package com.wende.spring.boot.wenblog.domain.article;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "blog_article_sort")
public class ArticleSort {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    long userId;
    String sortArticleName;
}
