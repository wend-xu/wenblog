package com.wende.spring.boot.wenblog.dao.article;

import com.wende.spring.boot.wenblog.domain.article.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleCommentDao extends JpaRepository<ArticleComment,Long> {
    List<ArticleComment> findArticleCommentsByCommentArticleIdAndParentId(long articleId,long parentId);
}
