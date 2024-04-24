package com.kk.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@TableName("blog_article")
@Data
@Builder
public class ArticleInfo {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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

    /**
     * 文章访问次数
     */
    private int visitCount;

    /**
     * 发布标识
     */
    private String publishFlag;

    /**
     * 置顶标识
     */
    private String topFlag;

    /**
     * 删除标识
     */
    private String delFlag;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 更新时间
     */
    private Timestamp updateTime;
}
