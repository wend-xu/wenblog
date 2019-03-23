package com.wende.spring.boot.wenblog.dao.article;

import com.wende.spring.boot.wenblog.domain.article.ArticleSort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArricleSortDao extends JpaRepository<ArticleSort,Long> {
}
