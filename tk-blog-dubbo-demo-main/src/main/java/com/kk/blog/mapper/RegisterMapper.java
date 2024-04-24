package com.kk.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kk.blog.entity.RegisterInfo;

public interface RegisterMapper extends BaseMapper<RegisterInfo> {
    Integer selectToValidAcc(String userName, String email);
}
