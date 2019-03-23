package com.wende.spring.boot.wenblog.domain.article;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "blog_article_comment ")
public class ArticleComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    long commentArticleId;
    String commentContent;
    long commentUserId;
    long beCommentUserId;
    Timestamp commentTime;
}
