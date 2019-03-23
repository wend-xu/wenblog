package com.wende.spring.boot.wenblog.util.constant;

public class ArticleConstant {
    public static final int ARTICLE_DRAFT = 1;
    public static final int ARTICLE_PUBLIC = 2;
    public static final int ARTICLE_DELETE = 3;
    public static final int ARTICLE_ALL_MODE = 4;

    public static final String INDEX_DEFAULT_PAGE = "1";
    public static final String INDEX_DEFAULT_SIZE = "20";

    public static final String[] ARTICLE_SORT_ACCORDING =
            {"id","articleTitle","articleSummary","articleContent","articleTime","lastUpdateTime","articleClick","articleLike"};
    public static final String SORT_BY_ID_DESC = "id-desc";
    public static final String SORT_BY_ID_ASC = "id-asc";
    public static final String SORT_BY_TITLE_DESC = "articleTitle-desc";
    public static final String SORT_BY_TITLE_ASC = "articleTitle-asc";
    public static final String SORT_BY_SUMMARY_DESC = "articleSummary-desc";
    public static final String SORT_BY_SUMMARY_ASC = "articleSummary-asc";
    public static final String SORT_BY_CONTENT_DESC = "articleContent-desc";
    public static final String SORT_BY_CONTENT_ASC = "articleContent-asc";
    public static final String SORT_BY_PUBLIC_TIME_DESC = "articleTime-desc";
    public static final String SORT_BY_PUBLIC_TIME_ASC = "articleTime-asc";
    public static final String SORT_BY_UPDATE_TIME_DESC = "lastUpdateTime-desc";
    public static final String SORT_BY_UPDATE_TIME_ASC = "lastUpdateTime-asc";
    public static final String SORT_BY_CLICK_DESC = "articleClick-desc";
    public static final String SORT_BY_CLICK_ASC = "articleClick-asc";
    public static final String SORT_BY_LIKE_DESC = "articleLike-desc";
    public static final String SORT_BY_LIKE_ASC = "articleLike-asc";
}
