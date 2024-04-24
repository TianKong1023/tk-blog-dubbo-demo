package com.kk.blog.enums;

public enum UserStatus {

    ROOT("2", "超级管理员"),
    ADMIN("1", "管理员"),
    USER("0", "普通用户"),
    BAN("-1", "已封禁用户");

    private String code;
    private String disc;

    UserStatus(String code, String disc) {
        this.code = code;
        this.disc = disc;
    }

    public String getCode() {
        return code;
    }

    public String getDisc() {
        return disc;
    }
}
