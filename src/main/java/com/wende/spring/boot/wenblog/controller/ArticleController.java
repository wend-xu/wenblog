package com.wende.spring.boot.wenblog.controller;

import com.wende.spring.boot.wenblog.domain.article.Article;
import com.wende.spring.boot.wenblog.domain.article.ArticleComment;
import com.wende.spring.boot.wenblog.domain.user.User;
import com.wende.spring.boot.wenblog.service.ArticleService;
import com.wende.spring.boot.wenblog.service.AuthenticationService;
import com.wende.spring.boot.wenblog.service.UserService;
import com.wende.spring.boot.wenblog.util.ParseTool;
import com.wende.spring.boot.wenblog.util.constant.ArticleConstant;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    UserService userService;

    @GetMapping("/editor")
    public String getEditor(Model model){
        model.addAttribute("mode","new");
        model.addAttribute("article",new Article());
        return  "editor";
    }

    @RequestMapping("/send")
    @ResponseBody
    public String publicArticle(@RequestBody Article article){
        if(!article.getArticleTitle().equals("") && article.getArticleContent() != "" && article.getArticleMode() != 0){
            article.setUserId(authenticationService.getAuthUserId());
            if(article.getArticleMode() == 0){
                article.setArticleMode(ArticleConstant.ARTICLE_DRAFT);
            }
            article = articleService.createArticle(article);
            if(article != null){
                return "success";
            }
        }
        return "fail";
    }

    /*
     * 修改这里的逻辑，每收到一次请求且当前用户未点击过对应的uuid的阅读量+1
     * 需要判断当前用户是否曾经点击或点赞过这篇文章做高亮提示
     * 需要判断当前用户是否为文章的拥有者
     * */
    @RequestMapping("/byUUID")
    public String getByUUID(@RequestParam(value = "uuid") String uuid,Model model){
        Article article = articleService.findArticleByArticleUUID(uuid);
        if(article != null){
            long click = articleService.articleBeClicked(article.getArticleUUID(),authenticationService.getAuthUserId());
            article.setArticleClick(click);
            articleService.hasArticleLikeOrClick(article,authenticationService.getAuthUserId());
            if(article.getUserId() == authenticationService.getAuthUserId()){
                model.addAttribute("editable","editable");
            }
        }else{
            article = new Article();
            model.addAttribute("notfound","notfound");
        }
        model.addAttribute("article",article);
        return "details";
    }

    @RequestMapping("/getall")
    public String getArticle(@RequestParam(value = "page" ,defaultValue = ArticleConstant.INDEX_DEFAULT_PAGE)String page,
                             @RequestParam(value = "size",defaultValue = ArticleConstant.INDEX_DEFAULT_SIZE)String size,
                             @RequestParam(value = "sort", defaultValue = ArticleConstant.SORT_BY_UPDATE_TIME_DESC)String sort,
                             Model model){
        List<Article> articles = articleService.findAllPublicArticles(page,size,sort);
        long userId = authenticationService.getAuthUserId();
        articleService.isNewArticles(articles,userId);
        if(userId != 0){
            articleService.hasArticlesLikeOrClick(articles,userId);
        }
        model.addAttribute("articles",articles);
        model.addAttribute("curr",page);
        model.addAttribute("size",size);
        model.addAttribute("targetApi","/article/getall?");
        model.addAttribute("targetCountApi","/rest/article/count/all");
        model.addAttribute("title","所有博客");
        return "index";
    }

    @RequestMapping("/get/byuser")
    public String getArticleByUser(@RequestParam(value = "page", defaultValue = ArticleConstant.INDEX_DEFAULT_PAGE)String page,
                                   @RequestParam(value = "size", defaultValue = ArticleConstant.INDEX_DEFAULT_SIZE)String size,
                                   @RequestParam(value = "sort", defaultValue = ArticleConstant.SORT_BY_UPDATE_TIME_DESC)String sort,
                                   @RequestParam(value = "mode", defaultValue = ArticleConstant.ARTICLE_PUBLIC+"")String mode,
                                   Model model){
        String userId = authenticationService.getAuthUserId()+"";
        if(!userId.equals("0")){
            //允许用户访问的仅有发表和草稿，已删除文章将作为后续添加的管理员模块的权限
            List<Article> articles;
            if(mode.equals(ArticleConstant.ARTICLE_DRAFT+"")){
                articles =articleService.findArticles(userId,ArticleConstant.ARTICLE_DRAFT,page,size,sort);
                model.addAttribute("targetCountApi"
                        ,"/rest/article/count?mode=1");
                model.addAttribute("targetApi","/article/get/byuser?mode=1&");
            }else{
                articles =articleService.findArticles(userId,ArticleConstant.ARTICLE_PUBLIC,page,size,sort);
                model.addAttribute("targetCountApi","/rest/article/count");
                model.addAttribute("targetApi","/article/get/byuser?");
            }
            articleService.hasArticlesLikeOrClick(articles,authenticationService.getAuthUserId());
            articleService.isNewArticles(articles,authenticationService.getAuthUserId());
            model.addAttribute("articles",articles);
        }
        String userName = userService.getUserById(authenticationService.getAuthUserId()).getUserName();
        model.addAttribute("curr",page);
        model.addAttribute("size",size);
        model.addAttribute("title",userName+" 的博客");
        return "index";
    }

    @RequestMapping("/search")
    public String searchArticle(@RequestParam(value = "keyword")String keyword,
                                @RequestParam(value = "page", defaultValue = ArticleConstant.INDEX_DEFAULT_PAGE)String page,
                                @RequestParam(value = "size", defaultValue = ArticleConstant.INDEX_DEFAULT_SIZE)String size,
                                @RequestParam(value = "sort", defaultValue = ArticleConstant.SORT_BY_UPDATE_TIME_DESC)String sort,
                                Model model){
        List<Article> articles = articleService.search(keyword,ArticleConstant.ARTICLE_PUBLIC,page,size,sort);
        model.addAttribute("articles",articles);
        model.addAttribute("curr",page);
        model.addAttribute("size",size);
        model.addAttribute("keyword",keyword);
        model.addAttribute("targetCountApi","/article/search/count?keyword="+keyword);
        model.addAttribute("targetApi","/article/search?keyword="+keyword+"&");
        model.addAttribute("title","key:“"+keyword+"”的搜索结果");
        return "index";
    }

    @RequestMapping("/search/count")
    @ResponseBody
    public String needSearchCount(@RequestParam(value = "keyword")String keyword){
        if(!keyword.equals(""))
            return articleService.searchResultCount(keyword,ArticleConstant.ARTICLE_PUBLIC)+"";
        else
            return "0";
    }

    @RequestMapping("/edit")
    public String editArticle(@RequestParam(value = "uuid") String uuid,Model model){
        System.out.println(uuid);
        Article article = articleService.findArticleByArticleUUID(uuid);
        model.addAttribute("article",article);
        model.addAttribute("mode","edit");
        System.out.println(article.toString());
        return "editor";
    }

    @PostMapping("/update")
    @ResponseBody
    public String updateArticle(@RequestBody Article article,Model model){
        if(article.getArticleMode() == 0){
            article.setArticleMode(ArticleConstant.ARTICLE_DRAFT);
        }
        article = articleService.updateArticle(article);
        if(article == null){ return "fail"; }
        model.addAttribute(article);
        return "success";
    }

    @PostMapping("/like")
    @ResponseBody
    public String likeArticle(@RequestBody Map<String,Object> requestMap){
        String uuid = (String)requestMap.get("uuid");
        if(authenticationService.getAuthUserId() == 0){
            return "未登录";
        }
        return articleService.articleLike(uuid,authenticationService.getAuthUserId())+"";
    }

    @RequestMapping("/comment/public")
    @ResponseBody
    public String publicComment(@RequestBody String request){
        JSONObject requestJSON = JSONObject.fromObject(request);
        if(requestJSON.getString("commentContent").equals("") || requestJSON.getLong("commentArticleId")==0){
            return "fail";
        }
        ArticleComment articleComment = new ArticleComment();
        articleComment.setCommentContent(requestJSON.getString("commentContent"));
        articleComment.setCommentArticleId(requestJSON.getLong("commentArticleId"));

        User authUser = userService.getUserById(authenticationService.getAuthUserId());
        if(authUser != null){
            articleComment.setCommentUserId(authUser.getUserId());
            articleComment.setCommentUserName(authUser.getUserName());
            articleComment.setCommentTime(ParseTool.dateToTimestamp(new Date()));
        }else {
            return "fail";
        }

        long parentId = 0;
        if(requestJSON.has("parentId")){ parentId = requestJSON.getLong("parentId"); }

        List<ArticleComment> comments;
        if(parentId == 0){//不要在public之后直接拿新数据，通过支持分页的新接口拿数据
            comments = articleService.publicComment(articleComment);
        }else{
            articleComment.setParentId(parentId);
            //设置父属性
            ArticleComment parentComment = articleService.findArticleCommentByCommentId(parentId);
            if(parentComment != null){
                articleComment.setBeCommentUserId(parentComment.getCommentUserId());
                articleComment.setBeCommentUserName(parentComment.getCommentUserName());
                if(parentComment.getParentId() != 0){
                    articleComment.setParentId(parentComment.getParentId());
                }
            }
            comments = articleService.publicComment(articleComment);
        }
        //由于是发表文章，发表成功不存在拿到null
        if(comments != null)
            return "success";
        else
            return "fail";
    }

    /*@RequestMapping("/comment/public/child")
    @ResponseBody
    public List<ArticleComment> publicChildComment(@RequestBody String request){
        JSONObject requestJSON = JSONObject.fromObject(request);
        ArticleComment articleComment = new ArticleComment();
        articleComment.setCommentContent(requestJSON.getString("commentContent"));

        articleComment.setCommentArticleId(requestJSON.getLong("commentArticleId"));
        User authUser = userService.getUserById(authenticationService.getAuthUserId());
        List<ArticleComment> comments = null;
        if(authUser != null && articleComment.getParentId() != 0
                && !articleComment.getCommentContent().equals("") && articleComment.getCommentArticleId() != 0){
            articleComment.setCommentUserId(authUser.getUserId());
            articleComment.setCommentUserName(authUser.getUserName());
            articleComment.setCommentTime(ParseTool.dateToTimestamp(new Date()));
            //设置父属性
            ArticleComment parentComment = articleService.findArticleCommentByCommentId(articleComment.getParentId());
            if(parentComment != null && parentComment.getParentId() != 0){
                articleComment.setParentId(parentComment.getParentId());
            }
            articleComment.setBeCommentUserId(parentComment.getCommentUserId());
            articleComment.setBeCommentUserName(parentComment.getCommentUserName());
            comments = articleService.publicComment(articleComment);
        }
        return comments;
    }*/

    @RequestMapping("/comment/delete")
    @ResponseBody
    public String dropComment(@RequestParam(value = "commentId") String commentId){
       if(articleService.deleteComment(commentId)){
           return  "success";
       }else{
           return "删除失败，无法找到该评论";
       }
    }
}
