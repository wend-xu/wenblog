package com.wende.spring.boot.wenblog.controller.rest;

import com.wende.spring.boot.wenblog.domain.article.Article;
import com.wende.spring.boot.wenblog.domain.article.ArticleComment;
import com.wende.spring.boot.wenblog.domain.config.City;
import com.wende.spring.boot.wenblog.service.ArticleService;
import com.wende.spring.boot.wenblog.service.AuthenticationService;
import com.wende.spring.boot.wenblog.service.UserService;
import com.wende.spring.boot.wenblog.util.ParseTool;
import com.wende.spring.boot.wenblog.util.constant.ArticleConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class RestAPI {
    @Autowired
    UserService userService;
    @Autowired
    ArticleService articleService;
    @Autowired
    AuthenticationService authenticationService;

    @RequestMapping("/login")
    public String login(@RequestParam(value = "loginName") String loginName , @RequestParam(value = "pwd") String pwd){
        return userService.login(loginName,pwd);
    }

    @RequestMapping("/article/get/byUser")
    public List<Article> getArticleByUserId(@RequestParam(value = "page", defaultValue = ArticleConstant.INDEX_DEFAULT_PAGE)String page,
                                            @RequestParam(value = "size", defaultValue = ArticleConstant.INDEX_DEFAULT_SIZE)String size,
                                            @RequestParam(value = "sort", defaultValue = ArticleConstant.SORT_BY_ID_ASC )String sort,
                                            @RequestParam(value = "mode", defaultValue = ArticleConstant.ARTICLE_PUBLIC+"")String mode,
                                            @RequestParam(value = "token",defaultValue = "invalue")String token){
        String userId = ParseTool.verifyToken(token);
        if(userId == null){
            return null;
        }
        if(mode.equals(ArticleConstant.ARTICLE_DRAFT+"")){
            return articleService.findArticles(userId,ArticleConstant.ARTICLE_DRAFT,page,size,sort);
        }else{
            return articleService.findArticles(userId,ArticleConstant.ARTICLE_PUBLIC,page,size,mode);
        }
    }

    @RequestMapping("/article/get/all")
    public List<Article> getArticle(@RequestParam(value = "page" ,defaultValue = ArticleConstant.INDEX_DEFAULT_PAGE)String page,
                                    @RequestParam(value = "size",defaultValue = ArticleConstant.INDEX_DEFAULT_SIZE)String size,
                                    @RequestParam(value = "sort", defaultValue = ArticleConstant.SORT_BY_ID_ASC)String sort){
        return articleService.findAllPublicArticles(page,size,sort);
    }

    //返回所有public的文章
    @RequestMapping("/article/count/all")
    public String countArticle(){
        return articleService.getArticleCount()+"";
    }

    @RequestMapping("/article/count/byuser")
    public String countArticleByUser(@RequestParam(value = "userid") String userId,
                                     @RequestParam(value = "mode")String mode){
        return articleService.getArticleCountByUserId(userId,mode)+"";
    }

    //需已登录或者通过token才能获取某个用户的文章数目
    @RequestMapping("/article/count")
    public String countArticle(@RequestParam(value = "token",defaultValue = "0")String token,
                               @RequestParam(value = "mode",defaultValue = ArticleConstant.ARTICLE_PUBLIC+"")String mode){
        //若验证失败将尝试判断当前是否登录
        String userId = ParseTool.verifyToken(token);
        //只允许且分别获得public或draft两种状态的文章
        int modeI = Integer.valueOf(mode);
        if(modeI != ArticleConstant.ARTICLE_DRAFT){
            modeI = ArticleConstant.ARTICLE_PUBLIC;
        }

        if(userId == null){
            if(authenticationService.getAuthUserId() != 0){
                return articleService.getArticleCountByUserId(authenticationService.getAuthUserId(),modeI)+"";
            }else{
                return "0";
            }
        }else{
            return articleService.getArticleCountByUserId( Long.valueOf(userId),modeI)+"";
        }
    }

    @RequestMapping("/config/city")
    public List<City> getCityByProvincial(@RequestParam int pid){
        return userService.findCityByProvincial(pid);
    }

    @RequestMapping("/article/comment")
    public List<ArticleComment> getComment(@RequestParam(value = "articleId",defaultValue = "0")String articleId){
        return articleService.findArticleComment(articleId);
    }
}
