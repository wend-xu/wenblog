package com.wende.spring.boot.wenblog.service;

import com.wende.spring.boot.wenblog.domain.article.Article;
import com.wende.spring.boot.wenblog.domain.article.ArticleComment;

import java.util.List;

public interface ArticleService {
    Article createArticle(Article article);

    Article findArticleByArticleUUID(String  articleUUID);

    /** userId==0&&articleMode==0 返回所有已发布的文章
    ** userId==0&&articleMode!=0 返回所有指定状态的文章
    ** userId!=0&&articleMode!=0 返回指定用户指定状态的文章
    ** userId!=0&&articleMode==0 返回指定用户的所有文章
     * */
    List<Article> findArticles(long userId, int articleMode ,int page, int size, String sort);

    //返回所有已发布的文章
    List<Article> findAllPublicArticles(String page, String size, String sort);

    //同findArticles(long userId, int articleMode ,int page, int size, String sort);
    List<Article> findArticles(String userId, int articleMode ,String page, String size, String sort);

    long getArticleCount();

    long getArticleCountByUserId(long userId , int articleMode);

    long getArticleCountByUserId(String userId , String articleMode);

    Article updateArticle(Article article);

    long articleBeClicked(String uuid,long userId);

    long articleLike(String uuid,long userId);

    void hasArticlesLikeOrClick(List<Article> articles,long userId);

    void hasArticleLikeOrClick(Article article,long userId);

    void isNewArticle(Article article,long userId);

    void isNewArticles(List<Article> article,long userId);

    List<Article> search(String keyword, int articleMode,int page,int size,String sort);

    List<Article> search(String keyword, int articleMode,String page,String size,String sort);

    long searchResultCount(String keyword,int mode);

    List<ArticleComment> findArticleComment(long articleId,int page,int size,String sort);

    List<ArticleComment> findArticleComment(String articleId,String page,String size,String sort);

    List<ArticleComment> findArticleComment(long articleId);

    List<ArticleComment> findArticleComment(String articleId);

    List<ArticleComment> findChildComment(long articleId,long commentId);

    ArticleComment findArticleCommentByCommentId(String id);

    ArticleComment findArticleCommentByCommentId(long id);

    boolean deleteComment(String id);

    boolean deleteComment(long id);

    List<ArticleComment> publicComment(ArticleComment comment);

    long getCommentCount(long articleId);

    long getChildCommentCount(long articleId,long parentId);
}
