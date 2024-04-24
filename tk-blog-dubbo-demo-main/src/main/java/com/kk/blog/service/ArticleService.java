package com.kk.blog.service;

import com.kk.blog.entity.ArticleInfo;
import com.kk.blog.dto.request.PublishArticleRequestParam;

import java.util.List;


public interface ArticleService {
    List<ArticleInfo> getArticleInfoPage(int currentPageNum, int size, String type);
    void insertArticle(PublishArticleRequestParam param);
    void updateArticle(PublishArticleRequestParam param);

    ArticleInfo getArticleInfoById(int id);

    void exportArticle();

    void deleteArticle(Integer articleId);
}
