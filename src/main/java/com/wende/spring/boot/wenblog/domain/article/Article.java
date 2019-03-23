package com.wende.spring.boot.wenblog.domain.article;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "blog_article")
@DynamicUpdate
public class Article {
    //与其他表关联时请使用id，其余情况使用uuid
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String articleUUID;
    private String articleTitle;
    private String articleKeyword;
    @Column(columnDefinition = "varchar(512)")
    private String articleSummary;
    @Column(columnDefinition = "MEDIUMTEXT not null")
    private String articleContent;
    private Timestamp articleTime;
    private Timestamp lastUpdateTime;
    private long articleClick;
    private long articleLike;
    private int articleSortId;
    private long userId;
    private String userName;
    private int articleMode;
    @Transient
    private boolean hasLike;
    @Transient
    private boolean hasClick;
    @Transient
    private boolean aNew;
}
