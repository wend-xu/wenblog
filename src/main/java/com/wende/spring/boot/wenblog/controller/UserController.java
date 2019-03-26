package com.wende.spring.boot.wenblog.controller;

import com.wende.spring.boot.wenblog.domain.config.Provincial;
import com.wende.spring.boot.wenblog.domain.user.User;
import com.wende.spring.boot.wenblog.domain.user.UserData;
import com.wende.spring.boot.wenblog.service.AuthenticationService;
import com.wende.spring.boot.wenblog.service.UserService;
import com.wende.spring.boot.wenblog.util.ParseTool;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationService authenticationService;

    @Value("${wenblog.user.default.upload.dir}")
    String defaultUploadDir;


    @GetMapping("/index")
    public String index(){
        return "redirect:/article/getall";
    }

    @GetMapping("/loginpage")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/datapage")
    public String userData(Model model){
        UserData userData = userService.getUserDataByUserId(authenticationService.getAuthUserId());
        String[] address;
        if(userData.getUserAddress() != null){
            address = userData.getUserAddress().split("-");
        }else {
            address = new String[]{"请选择","请选择","请选择"};
        }

        model.addAttribute("selectedProvincial",address[0]);
        model.addAttribute("selectedCity",address[1]);

        if(address.length < 3){
            model.addAttribute("address","");
        }else {
            model.addAttribute("address",address[2]);
        }

        Provincial provincial = userService.findProvincial(address[0]);
        if(provincial!=null){
            int pid = provincial.getPid();
            model.addAttribute("cities",userService.findCityByProvincial(pid));
        }else{
            model.addAttribute("cities",userService.findAllCity());
        }
        model.addAttribute("provincials",userService.findProvincials());
        model.addAttribute("userData",userData);
        return "userdata";
    }

    @GetMapping("/register")
    public String registerPage() { return  "register"; }

    @GetMapping("/head")
    public String getHead(){ return "part/head"; }


    @GetMapping("/detail")
    public String getDetail(){return "details";}

    @GetMapping("/login")
    public String loginByToken(@RequestParam(value = "token") String token){
        return null;
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        SecurityContextHolder.getContext().setAuthentication(null);
        session.removeAttribute("user");
        session.removeAttribute("token");
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestParam(value = "loginname") String loginname,
                        @RequestParam( value = "pwd") String pwd,
                        HttpSession session){
       /* Authentication authentication = new UsernamePasswordAuthenticationToken(loginname,pwd);
        AuthenticationManager am =new AuthenticationManager(){
            final List<GrantedAuthority> AUTHORITIES = new ArrayList<>();
            @Override
            public Authentication authenticate(Authentication auth) throws AuthenticationException {
                AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_active"));
                return new UsernamePasswordAuthenticationToken(auth.getName(), auth.getCredentials(), AUTHORITIES);
            }
        };
        Authentication result = am.authenticate(authentication);
        result = SecurityContextHolder.getContext().getAuthentication();
        */

        Authentication authentication = authenticationService.login(loginname,pwd);
        if(authentication != null){
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userService.getUserById(authentication.getName());
            session.setAttribute("user",user);
            String token = ParseTool.createToken(user.getUserId());
            session.setAttribute("token",token);
            return "success";
        }else {
            if(!userService.checkEmail(loginname)){
                return "用户不存在";
            }
            return "密码错误";
        }
    }

    @PostMapping("/register")
    @ResponseBody
    public String register(@RequestParam(value = "useremail") String userEmail,
                           @RequestParam(value = "username") String userName,
                           @RequestParam(value = "userpwd") String userPwd,
                           HttpSession session){
        User user = new User();
        user.setUserEmail(userEmail);
        user.setUserName(userName);
        user.setUserPwd(userPwd);
        user = userService.register(user);
        if(user != null){
            userService.createUserData(user.getUserId());
            Authentication authentication = authenticationService.login(user.getUserEmail(),userPwd);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute("user",user);
            return "success";
        } else{
            return "fail";
        }
    }

    @PostMapping("/data")
    @ResponseBody
    public String saveData(@RequestBody String jsonParam,HttpSession session){
        JSONObject jsonObject = JSONObject.fromObject(jsonParam);
        UserData userData = new UserData();
        userData.setUserId(authenticationService.getAuthUserId());
        userData.setUserSex(jsonObject.getString("sex"));
        /*Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jsonObject.getString("birthday"));
        userData.setUserBirthday(date);*/
        userData.setUserBirthday(ParseTool.parseStringToDate("yyyy年MM月dd日",jsonObject.getString("birthday")));
        userData.setUserSchool(jsonObject.getString("school"));
        userData.setUserAddress(jsonObject.getString("address"));
        userData.setUserBloodType(jsonObject.getString("bloodtype"));
        userData.setUserSignature(jsonObject.getString("signature"));
        userData.setUserDescription(jsonObject.getString("desc"));
        userService.saveUserData(userData);

        User user = userService.getUserById(authenticationService.getAuthUserId());
        user.setUserName(jsonObject.getString("username"));
        user = userService.updateUser(user);
        //更新session中的use
        session.setAttribute("user",user);
        return "success";
    }

    @RequestMapping(value = "/upload/headPortrait" ,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String uploadHeadPortrait(
            @RequestParam(value = "file") MultipartFile file, HttpSession session){
        JSONObject result = new JSONObject();
        if(file != null && authenticationService.getAuthUserId() != 0){
            String fileUrl = userService.uploadPhoto
                    (file,defaultUploadDir,"头像");
            if(fileUrl != null){
                User user = userService.getUserById(authenticationService.getAuthUserId());
                user.setUserImageUrl(fileUrl);
                userService.updateUser(user);
                session.setAttribute("user",user);
                result.put("code",0);
                result.put("src",fileUrl);
            }else{
                result.put("code",1);
                result.put("src",userService.getUserById(authenticationService.getAuthUserId()).getUserImageUrl());
            }
        }else {
            result.put("code",1);
            result.put("src",userService.getUserById(authenticationService.getAuthUserId()).getUserImageUrl());
        }
        return result.toString();
    }

    @RequestMapping(value = "/upload/photo/editorMD" ,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String uploadPhotoByEditorMd(@RequestParam(value = "editormd-image-file")MultipartFile photo){
        String photoUrl = userService.uploadPhoto(photo,defaultUploadDir,null);
        JSONObject result = new JSONObject();
        if (photoUrl != null) {
            result.put("success",1);
            result.put("message","上传成功");
            result.put("url",photoUrl);
        }else {
            result.put("success",0);
            result.put("message","上传失败");
        }
        return result.toString();
    }

    @RequestMapping(value = "/upload/photo/layEdit" ,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String uploadPhotoByLayEdit(@RequestParam(value = "file")MultipartFile photo){
        String photoUrl = userService.uploadPhoto(photo,defaultUploadDir,null);
        JSONObject result = new JSONObject();
        if (photoUrl != null) {
            result.put("code",0);
            result.put("msg","上传成功");
            JSONObject data =new JSONObject();
            data.put("src",photoUrl);
            result.put("data",data);
        }else {
            result.put("code",1);
            result.put("msg","上传失败");
        }
        return result.toString();
    }

    @GetMapping("/changePwd")
    public String toChangePasswordPage(){
        return "changepwd";
    }

    @PostMapping("/changePwd")
    @ResponseBody
    public String changePassword(@RequestBody String jsonParam,HttpSession session){
        JSONObject jsonObject = JSONObject.fromObject(jsonParam);
        String originPassword = jsonObject.getString("originPwd");
        String newPassword = jsonObject.getString("newPwd");
        if(userService.changePassword(originPassword,newPassword,authenticationService.getAuthUserId())){
            logout(session);
            return "success";
        }else {
            return "fail";
        }
    }

    @RequestMapping("/authUserId")
    @ResponseBody
    public String getAuthUserId(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("authUserId",authenticationService.getAuthUserId());
        return jsonObject.toString();
    }
}