package com.kk.blog.controller;

import com.kk.blog.entity.TokenInfo;
import com.kk.blog.entity.UserInfo;
import com.kk.blog.enums.LoginStatus;
import com.kk.blog.enums.UserStatus;
import com.kk.blog.dto.request.LoginRequestParam;
import com.kk.blog.dto.request.RegisteRequestParam;
import com.kk.blog.dto.response.BaseResponse;
import com.kk.blog.service.UserService;
import com.kk.blog.utils.UserContext;
import com.kk.blog.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/blog/user")
@RestController
@Slf4j
public class UserController {

    @Resource
    public UserService userService;

    /**
     * 用户注册
     * @param param
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<String> userRegister(@RequestBody RegisteRequestParam param) {
        try {
            log.info("用户注册{}", param);
            if(!ValidationUtils.isValidEmail(param.getEmail()) || !ValidationUtils.isValidUserName(param.getUserName())) {
                return BaseResponse.<String>builder()
                        .data("用户名或邮箱不符合规范！")
                        .code(1)
                        .build();
            }
            if(!userService.isValidAcc(param.getUserName(), param.getEmail())) {
                return BaseResponse.<String>builder()
                        .data("用户名或邮箱已存在！")
                        .code(1)
                        .build();
            }
            userService.userRegister(param);
            return BaseResponse.<String>builder()
                    .data("注册成功，请等待管理员确认")
                    .code(0)
                    .build();
        } catch (Exception e) {
            log.error("注册失败！", e);
            return BaseResponse.<String>builder()
                    .data("注册失败，请稍后再试！")
                    .code(1)
                    .build();
        }
    }

    /**
     * 管理员确认用户注册
     * @param id
     * @param operation
     * @return
     */
    @PostMapping("/confirmRegister")
    public BaseResponse<String> confirmRegister(@RequestParam(value = "id") Integer id, @RequestParam(value = "operation") Integer operation) {
        try {
            log.info("确认用户注册，id = {}", id);
            UserInfo userInfo = UserContext.getUser();
            if(!(userInfo.getStatus().equals(UserStatus.ADMIN.getCode()) || userInfo.getStatus().equals(UserStatus.ROOT.getCode()))) {
                return BaseResponse.<String>builder()
                        .data("用户权限不足")
                        .code(1)
                        .build();
            }
            userService.confirmRegister(id, operation);
            return BaseResponse.<String>builder()
                    .data("操作成功")
                    .code(0)
                    .build();
        } catch (Exception e) {
            log.error("操作失败！", e);
            return BaseResponse.<String>builder()
                    .data("操作失败，请稍后再试！")
                    .code(1)
                    .build();
        }
    }

    /**
     * 用户登录
     * @param param
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<String> login(@RequestBody LoginRequestParam param) {
        log.info("用户登录，userName = {}", param.getUserName());
        LoginStatus loginStatus = userService.login(param);
        if(loginStatus.getCode() == LoginStatus.EXCEPTION.getCode()) {
            return BaseResponse.<String>builder()
                    .data("账号异常！")
                    .code(1)
                    .build();
        } else if(loginStatus.getCode() == LoginStatus.ERROR.getCode()) {
            return BaseResponse.<String>builder()
                    .data("用户名或密码错误！")
                    .code(1)
                    .build();
        } else {
            TokenInfo token = userService.getTokenByUserName(param.getUserName());
            return BaseResponse.<String>builder()
                    .data(token.getToken())
                    .code(1)
                    .build();
        }
    }
}
