package com.wende.spring.boot.wenblog.domain.article.esDomain;


import com.wende.spring.boot.wenblog.domain.article.Article;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.stereotype.Repository;


@Document(indexName = "article",type = "article")
@Data
public class EsArticle {
    @Id
    String id;
    private String articleUUID;
    private String articleTitle;
    private String articleKeyword;
    private String articleSummary;
    private String articleContent;

    public static EsArticle createEsBlog(Article article){
        EsArticle esArticle = new EsArticle();
        if(article.getArticleUUID()!= null){
            esArticle.setArticleUUID(article.getArticleUUID());
        }
        if (article.getArticleTitle() != null){
            esArticle.setArticleTitle(article.getArticleTitle());
        }
        if(article.getArticleKeyword() != null){
            esArticle.setArticleKeyword(article.getArticleKeyword());
        }
        if(article.getArticleSummary() != null){
            esArticle.setArticleSummary(article.getArticleSummary());
        }
        if(article.getArticleContent() != null){
            esArticle.setArticleContent(article.getArticleContent());
        }
        return esArticle;
    }
}
