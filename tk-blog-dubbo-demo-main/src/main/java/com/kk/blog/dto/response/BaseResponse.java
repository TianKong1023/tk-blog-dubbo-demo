package com.kk.blog.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BaseResponse<T> {
    // 响应状态 0-成功 1-失败
    int code;
    T data;
}
