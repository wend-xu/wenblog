package com.wende.spring.boot.wenblog.dao.article;

import com.wende.spring.boot.wenblog.domain.article.ArticleComment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ArticleCommentDao extends JpaRepository<ArticleComment,Long> {
    List<ArticleComment> findArticleCommentsByCommentArticleIdAndParentId(long articleId,long parentId);

    ArticleComment findArticleCommentById(long id);

    void deleteById(long id);

    @Transactional
    void deleteByParentId(long parentId);

    List<ArticleComment> findArticleCommentsByCommentArticleIdAndParentId(long articleId, long parentId, Pageable pageable);

    long countArticleCommentsByCommentArticleIdAndParentId(long articleId, long parentId);

    List<ArticleComment> findArticleCommentsByCommentUserId(long id);

    List<ArticleComment> findArticleCommentsByBeCommentUserId(long id);
}
