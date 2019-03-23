package com.wende.spring.boot.wenblog.dao.article;

import com.wende.spring.boot.wenblog.domain.article.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleLikeDao extends JpaRepository<ArticleLike,Long> {
    ArticleLike findArticleLikeByLikeUserIdAndLikeArticleId(long userId,long articleId);

    long countArticleLikeByLikeArticleId(long articleId);
}
