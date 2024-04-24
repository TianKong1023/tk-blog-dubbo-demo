package com.kk.blog.enums;

public enum TokenStatus {
    VALID("0", "有效"),
    INVALID("1", "无效");

    public String getCode() {
        return code;
    }

    public String getDisc() {
        return disc;
    }

    TokenStatus(String code, String disc) {
        this.code = code;
        this.disc = disc;
    }

    private String code;
    private String disc;
}
