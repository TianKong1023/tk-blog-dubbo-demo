package com.kk.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@TableName("registration_request")
@Data
@Builder
public class RegisterInfo {
    /**
     * 注册申请id
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
     * 用户状态(0-待审核 1-拒绝注册)
     */
    String status;
}
