package com.wende.spring.boot.wenblog.domain.article;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@Table(name = "blog_article_comment ")
public class ArticleComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    long commentArticleId;
    long parentId;
    @Column(columnDefinition = "MEDIUMTEXT not null")
    String commentContent;
    long commentUserId;
    long beCommentUserId;
    Timestamp commentTime;
    String commentUserName;
    @Column(nullable = true)
    String beCommentUserName;
    @Transient
    List<ArticleComment> childComments;
}
