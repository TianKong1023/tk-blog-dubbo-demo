package com.kk.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kk.blog.convert.ConvertDTO2Entity;
import com.kk.blog.dto.CommentInfoDTO;
import com.kk.blog.dto.request.CommentRequestParam;
import com.kk.blog.entity.CommentInfo;
import com.kk.blog.enums.DeleteStatus;
import com.kk.blog.mapper.CommentMapper;
import com.kk.blog.mapper.UserMapper;
import com.kk.blog.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public void publishComment(CommentRequestParam param) {
        CommentInfo commentInfo = ConvertDTO2Entity.convertCommentParam(param);
        commentMapper.insert(commentInfo);
    }

    @Override
    public List<CommentInfoDTO> getCommentsByArticleId(Integer objectId) {
        /*
        LambdaQueryWrapper<CommentInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentInfo::getObjectId, objectId);
        List<CommentInfo> commentInfos = commentMapper.selectList(queryWrapper);
        */
        List<CommentInfoDTO> commentInfoDTOS = commentMapper.listAllByObjectId(objectId);
        return commentInfoDTOS;
    }

    @Override
    @Transactional
    public void deleteCommentsById(Integer commentId) {
        // 要删除所有id或parentId为commentId的评论，需要加事务，逻辑删除
        LambdaQueryWrapper<CommentInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentInfo::getParentId, commentId).or().eq(CommentInfo::getId, commentId);
        List<CommentInfo> commentInfos = commentMapper.selectList(queryWrapper);
        commentInfos.forEach(s -> {
                    s.setDelFlag(DeleteStatus.DELETE.getCode());
                    commentMapper.updateById(s);
                });
    }
}
