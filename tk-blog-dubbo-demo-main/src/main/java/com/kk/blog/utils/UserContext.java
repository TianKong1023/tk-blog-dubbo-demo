package com.kk.blog.utils;

import com.kk.blog.entity.UserInfo;

public class UserContext {
    private static final ThreadLocal<UserInfo> userContext = new ThreadLocal<>();

    public static void setUser(UserInfo user) {
        userContext.set(user);
    }

    public static UserInfo getUser() {
        return userContext.get();
    }

    public static void removeUser() {
        userContext.remove();
    }
}
