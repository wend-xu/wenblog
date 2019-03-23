package com.wende.spring.boot.wenblog.dao.article;

import com.wende.spring.boot.wenblog.domain.article.esDomain.EsArticle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EsArticleDao extends ElasticsearchRepository<EsArticle,String> {
    EsArticle findEsArticleByArticleUUID(String UUID);

    List<EsArticle> findDistinctByArticleTitleContainingOrArticleKeywordContainingOrArticleSummaryContainingOrArticleContentContaining
            (String title, String keyword,String summary,String content);

    List<EsArticle> findDistinctByArticleTitleContaining(String title);
}
