package com.kk.blog.dto.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentRequestParam {

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
}
