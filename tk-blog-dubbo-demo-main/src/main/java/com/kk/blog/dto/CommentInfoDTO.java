package com.kk.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentInfoDTO {

    /**
     * 评论id
     */
    Integer id;

    /**
     * 父评论id
     */
    Integer parentId;

    /**
     * 用户id
     */
    Integer commentatorId;

    /**
     * 用户名
     */
    String commentator;

    /**
     * 评论内容
     */
    String content;

    /**
     * 评论时间
     */
    LocalDateTime createTime;

    /**
     * 被评论文章id
     */
    Integer ObjectId;
}
