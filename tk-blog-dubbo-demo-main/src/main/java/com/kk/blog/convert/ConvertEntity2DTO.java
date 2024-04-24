package com.kk.blog.convert;

import com.kk.blog.dto.ArticleInfoDTO;
import com.kk.blog.entity.ArticleInfo;

public class ConvertEntity2DTO {
    public static ArticleInfoDTO convertArticleEntity2DTO (ArticleInfo articleInfo) {
        return ArticleInfoDTO.builder()
                .content(articleInfo.getContent())
                .coverSrc(articleInfo.getCoverSrc())
                .createTime(articleInfo.getCreateTime())
                .delFlag(articleInfo.getDelFlag())
                .href(articleInfo.getHref())
                .id(articleInfo.getId())
                .introduction(articleInfo.getIntroduction())
                .publishFlag(articleInfo.getPublishFlag())
                .tags(articleInfo.getTags())
                .title(articleInfo.getTitle())
                .topFlag(articleInfo.getTopFlag())
                .type(articleInfo.getType())
                .updateTime(articleInfo.getUpdateTime())
                .userId(articleInfo.getUserId())
                .visitCount(articleInfo.getVisitCount())
                .build();
    }
}
