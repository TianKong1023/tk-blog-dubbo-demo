package com.kk.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kk.blog.common.AppConstants;
import com.kk.blog.convert.ConvertDTO2Entity;
import com.kk.blog.dto.request.LoginRequestParam;
import com.kk.blog.dto.request.RegisteRequestParam;
import com.kk.blog.entity.RegisterInfo;
import com.kk.blog.entity.TokenInfo;
import com.kk.blog.entity.UserInfo;
import com.kk.blog.enums.*;
import com.kk.blog.mapper.BlogTokenMapper;
import com.kk.blog.mapper.RegisterMapper;
import com.kk.blog.mapper.UserMapper;
import com.kk.blog.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    public UserMapper userMapper;

    @Resource
    public RegisterMapper registerMapper;

    @Resource
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    public BlogTokenMapper blogTokenMapper;

    @Override
    public void userRegister(RegisteRequestParam param) {
        RegisterInfo registerInfo = ConvertDTO2Entity.convertRegisterParam(param);
        registerMapper.insert(registerInfo);
    }

    @Override
    @Transactional
    public void confirmRegister(Integer id, Integer operation) {
        RegisterInfo registerInfo = registerMapper.selectById(id);
        if(operation.equals(ConfirmRegisterOperation.ARGEE.getCode())) {
            UserInfo userInfo = convertRegister2User(registerInfo);
            registerMapper.deleteById(id);
            userMapper.insert(userInfo);
        } else {
            // 如果不同意注册，应该添加拒绝理由，并通过email通知用户
            registerInfo.setStatus(RegisterStatus.REJECTED.getCode());
            registerMapper.updateById(registerInfo);
        }
    }

    @Override
    public Boolean isValidAcc(String userName, String email) {
        Integer registerId = registerMapper.selectToValidAcc(userName, email);
        Integer userId = userMapper.selectToValidAcc(userName, email);
        return registerId == null && userId == null;
    }

    @Override
    public LoginStatus login(LoginRequestParam param) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", param.getUserName());
        UserInfo userInfo = userMapper.selectOne(queryWrapper);
        if(userInfo == null || !bCryptPasswordEncoder.matches(param.getPassword(), userInfo.getPassword())) {
            return LoginStatus.ERROR;
        }
        if(Objects.equals(userInfo.getStatus(), UserStatus.BAN.getCode())) {
            return LoginStatus.EXCEPTION;
        }
        TokenInfo tokenInfo = generateToken(userInfo.getUserName(), userInfo.getId());
        blogTokenMapper.insert(tokenInfo);
        return LoginStatus.SUCCESS;
    }

    @Override
    public TokenInfo getTokenByUserName(String userName) {
        QueryWrapper<UserInfo> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name", userName);
        UserInfo userInfo = userMapper.selectOne(userQueryWrapper);
        QueryWrapper<TokenInfo> tokenQueryWrapper = new QueryWrapper<>();
        tokenQueryWrapper.eq("user_id", userInfo.getId());
        return blogTokenMapper.selectOne(tokenQueryWrapper);
    }

    @Override
    public Boolean validToken(String token) {
        LocalDateTime validTime = getValidTime();
        TokenInfo tokenInfo = blogTokenMapper.selectOne(new QueryWrapper<TokenInfo>().eq("token", token).gt("last_active_time", validTime));
        return tokenInfo != null;
    }

    @Override
    public List<TokenInfo> findExpiredTokens() {
        LocalDateTime validTime = getValidTime();
        LambdaQueryWrapper<TokenInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.lt(TokenInfo::getLastActiveTime, validTime);
        List<TokenInfo> tokenInfos = blogTokenMapper.selectList(queryWrapper);
        return tokenInfos;
    }

    private LocalDateTime getValidTime() {
        LocalDateTime now = LocalDateTime.now();
        long tokenExpireTime = AppConstants.TOKEN_EXPIRE_TIME;
        LocalDateTime validTime = now.minus(tokenExpireTime, ChronoUnit.MILLIS);
        return validTime;
    }

    @Override
    @Transactional
    public void deleteExpiredTokens(List<TokenInfo> expiredTokens) {
        blogTokenMapper.deleteBatchIds(expiredTokens.stream()
                .map(TokenInfo::getId)
                .collect(Collectors.toList())
        );
    }

    @Override
    public UserInfo getUserByToken(String token) {
        LambdaQueryWrapper<TokenInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TokenInfo::getToken, token);
        TokenInfo tokenInfo = blogTokenMapper.selectOne(queryWrapper);
        return userMapper.selectById(tokenInfo.getUserId());
    }

    @Override
    @Transactional
    public void refreshTokenValidTime(UserInfo user) {
        TokenInfo tokenInfo = blogTokenMapper.selectOne(new LambdaQueryWrapper<TokenInfo>().eq(TokenInfo::getUserId, user.getId()));
        if(tokenInfo == null) {
            tokenInfo = generateToken(user.getUserName(), user.getId());
            blogTokenMapper.insert(tokenInfo);
            return ;
        }
        tokenInfo.setLastActiveTime(Timestamp.valueOf(LocalDateTime.now()));
        blogTokenMapper.updateById(tokenInfo);
    }

    private UserInfo convertRegister2User(RegisterInfo registerInfo) {
        return UserInfo.builder()
                .email(registerInfo.getEmail())
                .userName(registerInfo.getUserName())
                .password(bCryptPasswordEncoder.encode(registerInfo.getPassword()))
                .status(UserStatus.USER.getCode())
                .build();
    }

    private TokenInfo generateToken(String userName, int userId) {
        String token = UUID.randomUUID().toString();
        token = token + userName;
        return TokenInfo.builder()
                .token(token)
                .userId(userId)
                .loginTime(Timestamp.valueOf(LocalDateTime.now()))
                .lastActiveTime(Timestamp.valueOf(LocalDateTime.now()))
                .status(TokenStatus.INVALID.getCode())
                .build();
    }
}
