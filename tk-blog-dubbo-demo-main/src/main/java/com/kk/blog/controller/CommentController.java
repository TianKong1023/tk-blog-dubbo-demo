package com.kk.blog.controller;

import com.kk.blog.dto.CommentInfoDTO;
import com.kk.blog.dto.request.CommentRequestParam;
import com.kk.blog.dto.response.BaseResponse;
import com.kk.blog.service.CommentService;
import com.kk.blog.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/blog/comment")
@Slf4j
public class CommentController {

    @Resource
    public CommentService commentService;

    @PostMapping("/publishComment")
    public BaseResponse<String> publishComment(@RequestBody CommentRequestParam param) {
        try {
            log.info("用户评论，用户id = {}", param.getCommentatorId());
            param.setCommentatorId(UserContext.getUser().getId());
            commentService.publishComment(param);
            return BaseResponse.<String>builder()
                    .code(0)
                    .data("评论成功！")
                    .build();
        } catch (Exception e) {
            log.error("用户评论异常", e);
            return BaseResponse.<String>builder()
                    .code(1)
                    .data("系统异常，请稍后再试！")
                    .build();
        }
    }

    @GetMapping("/getCommentsByArticleId")
    public BaseResponse<List<CommentInfoDTO>> getCommentsByArticleId(@RequestParam("objectId") Integer objectId) {
        try {
            log.info("查询文章评论");
            List<CommentInfoDTO> comments = commentService.getCommentsByArticleId(objectId);
            return BaseResponse.<List<CommentInfoDTO>>builder()
                    .code(0)
                    .data(comments)
                    .build();
        } catch (Exception e) {
            log.info("查询文章评论失败", e);
            return BaseResponse.<List<CommentInfoDTO>>builder()
                    .code(1)
                    .data(null)
                    .build();
        }
    }

    @PostMapping("/deleteCommentById")
    public BaseResponse<String> deleteCommentById(@RequestParam Integer commentId) {
        try {
            log.info("删除评论，id={}", commentId);
            commentService.deleteCommentsById(commentId);
            return BaseResponse.<String>builder()
                    .code(0)
                    .data("删除成功！")
                    .build();
        } catch (Exception e) {
            log.error("删除评论异常", e);
            return BaseResponse.<String>builder()
                    .code(1)
                    .data("系统异常，请稍后再试！")
                    .build();
        }
    }
}
