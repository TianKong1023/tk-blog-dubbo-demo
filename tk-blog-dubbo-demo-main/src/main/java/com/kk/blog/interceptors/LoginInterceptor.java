package com.kk.blog.interceptors;

import com.kk.blog.entity.UserInfo;
import com.kk.blog.service.UserService;
import com.kk.blog.utils.UserContext;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 检查用户是否已经登录
        String token = getTokenFromCookie(request);
        Boolean isValid = userService.validToken(token);
        if(!isValid) {
            response.setStatus(302);
            response.setHeader("Location", "/blog/login");
            return false;
        }
        UserInfo user = userService.getUserByToken(token);
        // 刷新该用户token
        userService.refreshTokenValidTime(user);
        UserContext.setUser(user);
        // 用户已经登录，放行请求
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        UserContext.removeUser();
    }

    private String getTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("token")) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
