package com.wende.spring.boot.wenblog.service.impl;

import com.wende.spring.boot.wenblog.dao.article.ArticleCommentDao;
import com.wende.spring.boot.wenblog.dao.article.ArticleDao;
import com.wende.spring.boot.wenblog.dao.config.CityDao;
import com.wende.spring.boot.wenblog.dao.config.ProvincialDao;
import com.wende.spring.boot.wenblog.dao.photo.PhotoDao;
import com.wende.spring.boot.wenblog.dao.user.UserDao;
import com.wende.spring.boot.wenblog.dao.user.UserDataDao;
import com.wende.spring.boot.wenblog.domain.article.Article;
import com.wende.spring.boot.wenblog.domain.article.ArticleComment;
import com.wende.spring.boot.wenblog.domain.config.City;
import com.wende.spring.boot.wenblog.domain.config.Provincial;
import com.wende.spring.boot.wenblog.domain.photo.Photo;
import com.wende.spring.boot.wenblog.domain.user.User;
import com.wende.spring.boot.wenblog.domain.user.UserData;
import com.wende.spring.boot.wenblog.service.UserService;
import com.wende.spring.boot.wenblog.util.ParseTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    UserDataDao userDataDao;
    @Autowired
    PhotoDao photoDao;
    @Autowired
    ProvincialDao provincialDao;
    @Autowired
    CityDao cityDao;
    @Autowired
    ArticleDao articleDao;
    @Autowired
    ArticleCommentDao articleCommentDao;

    @Value("${wenblog.user.default.headportrait}")
    String defaultHeadPortraitUrl;
    @Value("${wenblog.user.protocol.host.port}")
    String protocolHostPort;

    @Override
    public String login(String loginName, String password) {
        password = ParseTool.encodePassword(password);
        User user = userDao.findUserByUserEmailAndUserPwd(loginName,password);
        String token = null;
        if(user != null){
            token = ParseTool.createToken(user.getUserId());
        }
        return token;
    }

    @Transactional
    @Override
    public User register(User user) {
        if(user.getUserName() != null && user.getUserPwd() != null && user.getUserEmail() != null){
            //邮箱被占用
            if(checkEmail(user.getUserEmail()) || !ParseTool.verifyPassword(user.getUserPwd())){
                return null;
            }
            String encodePwd = ParseTool.encodePassword(user.getUserPwd());
            user.setUserPwd(encodePwd);
            user.setUserRole("active");
            user.setUserImageUrl(defaultHeadPortraitUrl);

            userDao.save(user);
        }
        user = userDao.findUserByUserEmailAndUserPwd(user.getUserEmail(),user.getUserPwd());
        return user;
    }

    /**u
     * 若邮箱存在，返回tre
     * 若不存在，返回false
     * **/
    @Override
    public boolean checkEmail(String email) {
        /*if(email == null || email.equals("")){
            return false;
        }
        User user = userDao.findUserByUserEmail(email);
        return user != null;*/
        return email != null && !email.equals("") && userDao.findUserByUserEmail(email)!= null;
    }

    @Override
    public User getUserById(String userId) {
        long id = Long.valueOf(userId);
        return getUserById(id);
    }

    @Override
    public UserData getUserDataByUserId(String userId) {
        long userIdL = Long.valueOf(userId);
        return userDataDao.findUserDataByUserId(userIdL);
    }

    @Override
    public UserData getUserDataByUserId(long userId) {
        return userDataDao.findUserDataByUserId(userId);
    }

    @Override
    public User getUserById(long userID){
        return userDao.findUserByUserId(userID);
    }

    @Transactional
    @Override
    public void createUserData(long userId) {
        UserData userData = new UserData();
        userData.setUserId(userId);
        userDataDao.save(userData);
    }

    @Override
    public UserData saveUserData(UserData userData) {
        //获取use data的主键
        userData.setId(userDataDao.findUserDataByUserId(userData.getUserId()).getId());
        userDataDao.save(userData);
        return userDataDao.findUserDataById(userData.getId());
    }

    @Transactional
    @Override
    public User updateUser(User user) {
        User originUser = userDao.findUserByUserId(user.getUserId());
        //如果修改了用户名，需要修改存在用户文章中的用户名
        if(user.getUserName().equals(originUser.getUserName())){
            List<Article>articles = articleDao.findArticlesByUserId(user.getUserId());
            String username = user.getUserName();
            for(Article article:articles){
                article.setUserName(username);
                articleDao.save(article);
            }
            //跟新comment中的username
            List<ArticleComment> comments = articleCommentDao.findArticleCommentsByCommentUserId(user.getUserId());
            for(ArticleComment comment:comments){
                comment.setCommentUserName(username);
                articleCommentDao.save(comment);
            }
            comments = articleCommentDao.findArticleCommentsByBeCommentUserId(user.getUserId());
            for(ArticleComment comment:comments){
                comment.setBeCommentUserName(username);
                articleCommentDao.save(comment);
            }
        }
        userDao.save(user);
        return userDao.findUserByUserId(user.getUserId());
    }

    @Transactional
    @Override
    public String uploadPhoto(MultipartFile file,String filePath,String fileDescription) {
        if(filePath.equals("relativePath")){
            filePath = null;
        }

        String photoUrl = null;
        if(file != null){
            Photo photo = new Photo();
            //保存原始文件名
            photo.setOriginName(file.getOriginalFilename());

            String fileMD5 = ParseTool.analysisFileMD5(file);
            photo.setPhotoMD5(fileMD5);
            List<Photo> photos = photoDao.findPhotosByPhotoMD5(fileMD5);
            //如果文件已经存在，直接指向该文件
            if(photos.size()>0){
                photo.setPhotoUrl(photos.get(0).getPhotoUrl());
                photoUrl=photos.get(0).getPhotoUrl();
            }else{
                String contentType = file.getContentType();
                String fileType = contentType.substring(contentType.indexOf("/")+1);
                String fileName = UUID.randomUUID().toString().replaceAll("-","")+"."+fileType;
                String tempFloder = new Date().getTime()+"";

                if(filePath == null){
                    photoUrl = "src/main/resources/static/upload/"+tempFloder+"/"+fileName;
                }else {
                    photoUrl = filePath+"upload/"+tempFloder+"/"+fileName;
                }

                File saveTarget = new File(photoUrl);
                if(!saveTarget.getParentFile().exists()){
                    saveTarget.getParentFile().mkdirs();
                }
                //判断是否为图片，否则返回空
                try{
                    Image image = ImageIO.read(file.getInputStream());
                    if(image == null) return null;
                    file.transferTo(saveTarget.getAbsoluteFile());
                }catch (IOException e){
                    e.printStackTrace();
                }

                if(protocolHostPort != null)
                    photoUrl = protocolHostPort+"/upload/"+tempFloder+"/"+fileName;
                photo.setPhotoUrl(photoUrl);
            }

            if(fileDescription != null){
                photo.setPhotoDescription(fileDescription);
            }else {
                photo.setPhotoDescription("未添加描述");
            }

            photo.setUploadTime(ParseTool.dateToTimestamp(new Date()));
            photoDao.save(photo);
        }
        return photoUrl;
    }

    @Override
    public List<Provincial> findProvincials() {
        return provincialDao.findAll();
    }

    @Override
    public Provincial findProvincial(int pid) {
        return provincialDao.findProvincialByPid(pid);
    }

    @Override
    public Provincial findProvincial(String provincial) {
        return provincialDao.findProvincialByProvincial(provincial);
    }

    @Override
    public List<City> findCityByProvincial(int pid) {
        return cityDao.findCitiesByPid(pid);
    }

    @Override
    public City findCity(int cid) {
        return cityDao.findCityByCid(cid);
    }

    @Override
    public List<City> findAllCity() {
        return cityDao.findAll();
    }

    @Transactional
    @Override
    public boolean changePassword(String originPassword, String newPassword,long userId) {
        if(!originPassword.equals(newPassword) && ParseTool.verifyPassword(newPassword)){
            String password = ParseTool.encodePassword(newPassword);
            User user = userDao.findUserByUserId(userId);
            if(user != null){
                user.setUserPwd(password);
                userDao.save(user);
                return true;
            }
        }
        return false;
    }
}
