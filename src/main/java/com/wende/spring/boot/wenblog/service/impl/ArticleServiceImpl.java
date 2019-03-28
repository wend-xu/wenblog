package com.wende.spring.boot.wenblog.service.impl;

import com.wende.spring.boot.wenblog.dao.article.*;
import com.wende.spring.boot.wenblog.dao.user.UserDao;
import com.wende.spring.boot.wenblog.domain.article.Article;
import com.wende.spring.boot.wenblog.domain.article.ArticleClick;
import com.wende.spring.boot.wenblog.domain.article.ArticleComment;
import com.wende.spring.boot.wenblog.domain.article.ArticleLike;
import com.wende.spring.boot.wenblog.service.ArticleService;
import com.wende.spring.boot.wenblog.util.ParseTool;
import com.wende.spring.boot.wenblog.util.constant.ArticleConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleDao articleDao;
    @Autowired
    UserDao userDao;
    @Autowired
    ArticleCommentDao articleCommentDao;
    @Autowired
    ArticleClickDao articleClickDao;
    @Autowired
    ArticleLikeDao articleLikeDao;
    @Autowired
    EsArticleDao esArticleDao;

    @Transactional
    @Override
    public Article createArticle(Article article){
        if(article.getUserId()!=0){
            if(article.getArticleSummary() == null || article.getArticleSummary().equals("")){
                String summary ;
                if(article.getArticleContent().length()>140){
                    summary = article.getArticleContent().substring(0,140);
                }else {
                    summary = article.getArticleContent();
                }
                article.setArticleSummary(summary);
            }
            String articleUUId = UUID.randomUUID().toString().replace("-","");
            article.setArticleUUID(articleUUId);
            article.setArticleTime(new Timestamp(new Date().getTime()));
            article.setLastUpdateTime(new Timestamp(new Date().getTime()));
            article.setUserName(userDao.findUserByUserId(article.getUserId()).getUserName());
            articleDao.save(article);
            article = articleDao.findArticleByArticleUUID(articleUUId);
            if(article != null){
                /*EsArticle esArticle = EsArticle.createEsBlog(article);
                esArticleDao.save(esArticle);*/
                return article;
            }
        }
        return null;
    }

    @Override
    public Article findArticleByArticleUUID(String articleUUID) {
        return articleDao.findArticleByArticleUUID(articleUUID);
    }

    @Override
    public List<Article> findArticles(long userId, int articleMode ,int page, int size, String sort){
        Sort resultSort;
        //如果传入的sort标记错误，设置为默认
        if(!verifyArticleSort(sort)){
            sort = ArticleConstant.SORT_BY_PUBLIC_TIME_DESC;
        }
        //将sort标记转换为Sort用于分页
        if(sort.contains("desc")){
            resultSort= new Sort(Sort.Direction.DESC,sort.replace("-desc",""));
        }else{
            resultSort= new Sort(Sort.Direction.ASC,sort.replace("-asc",""));
        }

        Pageable pageable = PageRequest.of(page-1,size,resultSort);

        //根据需求返回文章
        if(userId == 0 && articleMode == 0){
            return articleDao.findArticlesByArticleMode(ArticleConstant.ARTICLE_PUBLIC,pageable);
        }else if(userId == 0 && articleMode != 0){
            return articleDao.findArticlesByArticleMode(articleMode,pageable);
        }else if(userId != 0 && articleMode == 0){
            return articleDao.findArticlesByUserId(userId,pageable);
        }else{//userId ！= 0 && mode ！= 0
            return articleDao.findArticlesByUserIdAndArticleMode(userId,articleMode,pageable);
        }
    }

    @Override
    public List<Article> findArticles(String userId, int articleMode ,String page, String size, String sort){
        long userIdL = Long.valueOf(userId);
        int pageI = Integer.valueOf(page);
        int sizeI = Integer.valueOf(size);
        return findArticles(userIdL,articleMode,pageI,sizeI,sort);
    }

    @Override
    public List<Article> findAllPublicArticles(String page, String size, String sort) {
        return findArticles("0",0,page,size,sort);
    }

    @Override
    public long getArticleCount() {
        return articleDao.countArticleByArticleMode(ArticleConstant.ARTICLE_PUBLIC);
    }

    @Override
    public long getArticleCountByUserId(long userId,int articleMode){
        return articleDao.countArticleByUserIdAndArticleMode(userId,articleMode);
    }

    @Override
    public long getArticleCountByUserId(String userId,String articleMode) {
        long userIdL = Long.valueOf(userId);
        int articleModeI = Integer.valueOf(articleMode);
        return getArticleCountByUserId(userIdL,articleModeI);
    }

    @Override
    @Transactional
    public Article updateArticle(Article article) {
        //获取数据库中的文章
        Article articleNeedUpdate = articleDao.findArticleByArticleUUID(article.getArticleUUID());
        //将可更新的内容替换
        articleNeedUpdate.setArticleTitle(article.getArticleTitle());
        articleNeedUpdate.setArticleKeyword(article.getArticleKeyword());
        articleNeedUpdate.setArticleSummary(article.getArticleSummary());
        articleNeedUpdate.setArticleContent(article.getArticleContent());
        articleNeedUpdate.setArticleMode(article.getArticleMode());
        articleNeedUpdate.setLastUpdateTime(ParseTool.dateToTimestamp(new Date()));
        //保存
        articleDao.save(articleNeedUpdate);
        article = articleDao.findArticleByArticleUUID(article.getArticleUUID());
        if(article != null){
            /*EsArticle esArticle = EsArticle.createEsBlog(article);
            esArticleDao.save(esArticle);*/
            return article;
        }
        return null;
    }

    @Override
    @Transactional
    public long articleBeClicked(String uuid,long userId) {
        Article article = articleDao.findArticleByArticleUUID(uuid);
        if(article!=null){
            ArticleClick articleClick = articleClickDao.findArticleClickByUserIdAndArticleId(userId,article.getId());
            //若article为空说明当前文章未被当前用户点击过
            if(articleClick != null || userId == 0){//当未点击或者未登录的情况
                return article.getArticleClick();
            }else {
                //创建一条点击记录
                articleClick = new ArticleClick();
                articleClick.setArticleId(article.getId());
                articleClick.setUserId(userId);
                articleClick.setClickTime(new Timestamp(new Date().getTime()));
                articleClickDao.save(articleClick);

                //将article的点击数更新为该文章点击记录的数目
                article.setArticleClick(articleClickDao.countArticleClickByArticleId(article.getId()));
                articleDao.save(article);
                //页显点击数的准确与否不是特别的重要
                return article.getArticleClick();
            }
        }
        return 0;
    }

    //点赞功能
    @Override
    @Transactional
    public long articleLike(String uuid,long userId) {
        Article article = articleDao.findArticleByArticleUUID(uuid);
        if(article!=null){
            ArticleLike articleLike = articleLikeDao.findArticleLikeByLikeUserIdAndLikeArticleId(userId,article.getId());
            //存在记录，删除记录取消点赞
            if(articleLike != null){
                articleLikeDao.delete(articleLike);
                article.setArticleLike(articleLikeDao.countArticleLikeByLikeArticleId(article.getId()));
                articleDao.save(article);
                return article.getArticleLike();
            //点赞
            }else{
                articleLike = new ArticleLike();
                articleLike.setLikeArticleId(article.getId());
                articleLike.setLikeUserId(userId);
                articleLike.setLikeTime(new Timestamp(new Date().getTime()));
                articleLikeDao.save(articleLike);
                article.setArticleLike(articleLikeDao.countArticleLikeByLikeArticleId(article.getId()));
                articleDao.save(article);
                return  article.getArticleLike();
            }
        }
        return 0;
    }

    @Override
    public void hasArticlesLikeOrClick(List<Article> articles,long userId){
        for(Article article:articles){
            hasArticleLikeOrClick(article,userId);
        }
    }

    @Override
    public void hasArticleLikeOrClick(Article article,long userId){
        if(articleLikeDao.findArticleLikeByLikeUserIdAndLikeArticleId(userId,article.getId())!=null){
            article.setHasLike(true);
        }
        if(articleClickDao.findArticleClickByUserIdAndArticleId(userId,article.getId())!= null){
            article.setHasClick(true);
        }
    }

    @Override
    public void isNewArticle(Article article, long userId) {
        //未点击过的文章并且发表时间一周以内为new
        if(article.getUserId() == userId){ return; }
        if(articleClickDao.findArticleClickByUserIdAndArticleId(userId,article.getId()) == null
                && ParseTool.isDataInRange(ParseTool.timestampToDate(article.getLastUpdateTime()),-7)){
            article.setANew(true);
        }
    }

    @Override
    public void isNewArticles(List<Article> articles, long userId) {
        for(Article article:articles){
            isNewArticle(article,userId);
        }
    }

    @Override
    public List<ArticleComment> findArticleComment(long articleId,int page,int size,String sort) {
        Sort resultSort;
        if(sort != null && sort.contains("asc")){
            resultSort = new Sort(Sort.Direction.ASC,"commentTime");
        }else{
            resultSort = new Sort(Sort.Direction.DESC,"commentTime");
        }

        List<ArticleComment> comments;
        if(page > 0 && size > 0){
            Pageable pageable = PageRequest.of(page-1,size,resultSort);
            comments= articleCommentDao.findArticleCommentsByCommentArticleIdAndParentId(articleId,0,pageable);
        }else{
            comments = articleCommentDao.findArticleCommentsByCommentArticleIdAndParentId(articleId,0);
        }

        for(ArticleComment comment:comments){
            List<ArticleComment> childComment = findChildComment(comment.getCommentArticleId(),comment.getId());
            comment.setChildComments(childComment);
        }
        return comments;
    }

    @Override
    public List<ArticleComment> findArticleComment(String articleId, String page, String size, String sort) {
        long articleIdL = Long.valueOf(articleId);
        int pageI = Integer.valueOf(page);
        int sizeI = Integer.valueOf(size);
        return findArticleComment(articleIdL,pageI,sizeI,sort);
    }

    @Override
    public List<ArticleComment> findArticleComment(long articleId) {
        return findArticleComment(articleId,0,0,"");
    }

    @Override
    public List<ArticleComment> findArticleComment(String articleId) {
        long articleIdL = Long.valueOf(articleId);
        return findArticleComment(articleIdL);
    }

    @Override
    public List<ArticleComment> findChildComment(long articleId, long commentId) {
        return articleCommentDao.findArticleCommentsByCommentArticleIdAndParentId(articleId,commentId);
    }

    @Override
    public ArticleComment findArticleCommentByCommentId(String id) {
        long idL = Long.valueOf(id);
        return findArticleCommentByCommentId(idL);
    }

    @Override
    public ArticleComment findArticleCommentByCommentId(long id) {
        return articleCommentDao.findArticleCommentById(id);
    }

    @Override
    public boolean deleteComment(String id) {
        long idl = Long.valueOf(id);
        return deleteComment(idl);
    }

    @Override
    @Transactional
    public boolean deleteComment(long id) {
        ArticleComment comment = articleCommentDao.findArticleCommentById(id);
        if(comment != null){
            articleCommentDao.deleteById(comment.getId());
            articleCommentDao.deleteByParentId(comment.getId());
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public List<ArticleComment> publicComment(ArticleComment comment) {
        articleCommentDao.save(comment);
        return findArticleComment(comment.getCommentArticleId());
    }

    @Override
    public long getCommentCount(long articleId) {
        return articleCommentDao.countArticleCommentsByCommentArticleIdAndParentId(articleId,0);
    }

    @Override
    public long getChildCommentCount(long articleId, long parentId) {
        return articleCommentDao.countArticleCommentsByCommentArticleIdAndParentId(articleId,parentId);
    }

    private boolean verifyArticleSort(String sort){
        String[] sortBys = ArticleConstant.ARTICLE_SORT_ACCORDING;
        for(String sortBy:sortBys){
            if(sort.contains(sortBy)){
                return true;
            }
        }
        return false;
    }
}
