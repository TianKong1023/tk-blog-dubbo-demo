package com.kk.blog.utils;

import com.kk.blog.common.AppConstants;
import com.kk.blog.entity.TokenInfo;

import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {
    // 邮箱正则表达式
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    // 用户名正则表达式
    private static final String USER_NAME_REGEX = "^[a-zA-Z][a-zA-Z0-9]{2,15}$";

    // 邮箱校验
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // 用户名校验
    public static boolean isValidUserName(String name) {
        Pattern pattern = Pattern.compile(USER_NAME_REGEX);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

}
