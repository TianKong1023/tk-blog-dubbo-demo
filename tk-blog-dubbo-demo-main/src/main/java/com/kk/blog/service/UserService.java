package com.kk.blog.service;

import com.kk.blog.entity.TokenInfo;
import com.kk.blog.entity.UserInfo;
import com.kk.blog.enums.LoginStatus;
import com.kk.blog.dto.request.LoginRequestParam;
import com.kk.blog.dto.request.RegisteRequestParam;

import java.util.List;

public interface UserService {
    void userRegister(RegisteRequestParam param);

    void confirmRegister(Integer id, Integer operation);

    Boolean isValidAcc(String userName, String email);

    LoginStatus login(LoginRequestParam param);

    TokenInfo getTokenByUserName(String userName);

    Boolean validToken(String token);

    List<TokenInfo> findExpiredTokens();

    void deleteExpiredTokens(List<TokenInfo> expiredTokens);

    UserInfo getUserByToken(String token);

    void refreshTokenValidTime(UserInfo user);
}
