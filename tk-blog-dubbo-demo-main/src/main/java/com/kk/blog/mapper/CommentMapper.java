package com.kk.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kk.blog.dto.CommentInfoDTO;
import com.kk.blog.entity.CommentInfo;

import java.util.List;

public interface CommentMapper extends BaseMapper<CommentInfo> {
    List<CommentInfoDTO> listAllByObjectId(Integer objectId);
}
