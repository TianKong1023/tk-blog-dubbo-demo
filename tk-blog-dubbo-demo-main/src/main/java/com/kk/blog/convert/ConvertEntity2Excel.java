package com.kk.blog.convert;

import com.kk.blog.entity.ArticleInfo;
import com.kk.blog.entity.excel.ArticleExcelInfo;
import com.kk.blog.enums.ArticleStatus;

public class ConvertEntity2Excel {
    public static ArticleExcelInfo convertArticleInfo2Excel(ArticleInfo articleInfo) {
        return ArticleExcelInfo.builder()
                .createTime(articleInfo.getCreateTime().toString())
                .updateTime(articleInfo.getUpdateTime().toString())
                .id(articleInfo.getId().toString())
                .userId(articleInfo.getUserId().toString())
                .introduction(articleInfo.getIntroduction())
                .tags(articleInfo.getTags())
                .type(ArticleStatus.getByCode(articleInfo.getType()).getArticleType())
                .visitCount(String.valueOf(articleInfo.getVisitCount()))
                .title(articleInfo.getTitle())
                .build();
    }
}
