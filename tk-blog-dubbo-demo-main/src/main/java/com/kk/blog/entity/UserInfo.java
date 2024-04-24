package com.kk.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@TableName("user")
@Data
@Builder
public class UserInfo {
    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    Integer id;

    /**
     * 用户名
     */
    String userName;

    /**
     * 密码
     */
    String password;

    /**
     * 邮箱
     */
    String email;

    /**
     * 用户身份(0-正常，1-管理员，2-封禁)
     */
    String status;

}
