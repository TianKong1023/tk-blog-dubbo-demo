package com.kk.blog.enums;

public enum RegisterStatus {
    PENDING("0", "待审核"),
    REJECTED("1", "拒绝");

    private String code;
    private String description;

    RegisterStatus(String code, String description) {
        this.description = description;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
