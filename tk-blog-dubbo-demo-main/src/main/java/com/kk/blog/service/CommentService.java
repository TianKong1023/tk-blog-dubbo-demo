package com.kk.blog.service;

import com.kk.blog.dto.CommentInfoDTO;
import com.kk.blog.dto.request.CommentRequestParam;

import java.util.List;

public interface CommentService {
    void publishComment(CommentRequestParam param);
    List<CommentInfoDTO> getCommentsByArticleId(Integer objectId);
    void deleteCommentsById(Integer commentId);
}
