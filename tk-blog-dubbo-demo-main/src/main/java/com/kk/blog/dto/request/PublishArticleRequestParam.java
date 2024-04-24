package com.kk.blog.dto.request;

import lombok.Data;

@Data
public class PublishArticleRequestParam {

    /**
     * id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章类型（1：博文，2：美文）
     */
    private String type;

    /**
     * 文章标签
     */
    private String tags;

    /**
     * 链接（目前不清楚）
     */
    private String href;

    /**
     * 封面图片链接
     */
    private String coverSrc;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章介绍
     */
    private String introduction;
}
