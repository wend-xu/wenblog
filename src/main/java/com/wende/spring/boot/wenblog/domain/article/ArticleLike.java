package com.wende.spring.boot.wenblog.domain.article;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@DynamicUpdate
@Table(name = "blog_article_like")
public class ArticleLike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long likeArticleId;
    private long likeUserId;
    private Timestamp likeTime;
}
