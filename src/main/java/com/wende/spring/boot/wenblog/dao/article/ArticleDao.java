package com.wende.spring.boot.wenblog.dao.article;

import com.wende.spring.boot.wenblog.domain.article.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDao extends JpaRepository<Article,Long> {
    Article findArticleByArticleUUID(String articleUUID);

    List<Article> findArticlesByUserId(long userId);

    List<Article> findArticlesByUserId(long userId, Pageable pageable);

    List<Article> findArticlesByUserIdAndArticleMode(long userId,int articleMode, Pageable pageable);

    long countArticleByUserIdAndArticleMode(long userId,int articleMode);

    long countArticleByArticleMode(int articleMode);

    List<Article> findArticlesByArticleContentContaining(String Content);

    List<Article> findArticlesByArticleMode(int mode,Pageable pageable);

    List<Article> findDistinctByArticleModeAndArticleTitleContainingOrArticleModeAndArticleSummaryContainingOrArticleModeAndArticleContentContaining(int mode,String titleKey,int mode1,String summaryKey,int mode2,String contentKey,Pageable pageable);

    long countDistinctByArticleModeAndArticleTitleContainingOrArticleModeAndArticleSummaryContainingOrArticleModeAndArticleContentContaining(int mode,String titleKey,int mode1,String summaryKey,int mode2,String contentKey);
}
