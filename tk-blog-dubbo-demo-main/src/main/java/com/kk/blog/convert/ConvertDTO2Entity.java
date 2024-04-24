package com.kk.blog.convert;

import com.kk.blog.common.AppConstants;
import com.kk.blog.dto.request.CommentRequestParam;
import com.kk.blog.entity.ArticleInfo;
import com.kk.blog.entity.CommentInfo;
import com.kk.blog.entity.RegisterInfo;
import com.kk.blog.enums.DeleteStatus;
import com.kk.blog.enums.RegisterStatus;
import com.kk.blog.dto.request.PublishArticleRequestParam;
import com.kk.blog.dto.request.RegisteRequestParam;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ConvertDTO2Entity {
    public static ArticleInfo convertPublishArticleParam(PublishArticleRequestParam param) {
        return ArticleInfo.builder()
                .userId(param.getUserId())
                .content(param.getContent())
                .coverSrc(param.getCoverSrc())
                .delFlag(AppConstants.DEFAULT_DEL_FLAG)
                .href(param.getHref())
                .introduction(param.getIntroduction())
                .publishFlag(AppConstants.DEFAULT_PUBLISH_FLAG)
                .tags(param.getTags())
                .title(param.getTitle())
                .topFlag(AppConstants.DEFAULT_TOP_FLAG)
                .type(AppConstants.DEFAULT_ARTICLE_TYPE)
                .updateTime(Timestamp.valueOf(LocalDateTime.now()))
                .visitCount(AppConstants.DEFAULT_VIS_COUNT)
                .build();
    }

    public static RegisterInfo convertRegisterParam(RegisteRequestParam param) {
        return RegisterInfo.builder()
                .email(param.getEmail())
                .password(param.getPassword())
                .userName(param.getUserName())
                .status(RegisterStatus.PENDING.getCode())
                .build();
    }

    public static CommentInfo convertCommentParam(CommentRequestParam param) {
        return CommentInfo.builder()
                .commentatorId(param.getCommentatorId())
                .content(param.getContent())
                .createTime(LocalDateTime.now())
                .objectId(param.getObjectId())
                .parentId(param.getParentId())
                .delFlag(DeleteStatus.DISPLAY.getCode())
                .type(param.getType())
                .build();
    }
}
