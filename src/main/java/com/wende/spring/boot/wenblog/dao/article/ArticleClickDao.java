package com.wende.spring.boot.wenblog.dao.article;

import com.wende.spring.boot.wenblog.domain.article.ArticleClick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleClickDao extends JpaRepository<ArticleClick,Long> {
    //多个参数时根据方法名上参数的顺序
    ArticleClick findArticleClickByUserIdAndArticleId(long userId,long articleId);

    long countArticleClickByArticleId(long articleId);
}
