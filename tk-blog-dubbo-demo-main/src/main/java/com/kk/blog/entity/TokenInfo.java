package com.kk.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@TableName("blog_token")
@Data
@Builder
public class TokenInfo {
    @TableId(value = "id", type = IdType.AUTO)
    Integer id;
    Integer userId;
    String token;
    Timestamp loginTime;
    Timestamp lastActiveTime;
    String status;
}
