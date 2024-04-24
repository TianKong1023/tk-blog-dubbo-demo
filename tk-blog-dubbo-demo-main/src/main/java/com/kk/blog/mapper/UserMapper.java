package com.kk.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kk.blog.entity.UserInfo;

public interface UserMapper extends BaseMapper<UserInfo> {
    Integer selectToValidAcc(String userName, String email);
}
