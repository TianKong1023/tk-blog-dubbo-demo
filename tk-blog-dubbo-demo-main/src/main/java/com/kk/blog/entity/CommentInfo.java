package com.kk.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("blog_comment")
@Data
@Builder
public class CommentInfo {
    /**
     * 评论id
     */
    @TableId(value = "id", type = IdType.AUTO)
    Integer id;

    /**
     * 父评论id
     */
    Integer parentId;

    /**
     * 评论者id
     */
    Integer commentatorId;

    /**
     * 评论内容
     */
    String content;

    /**
     * 博客文章类型
     */
    String type;

    /**
     * 博客文章Id
     */
    Integer objectId;

    /**
     * 评论时间
     */
    LocalDateTime createTime;

    /**
     * 逻辑删除
     */
    String delFlag;
}
