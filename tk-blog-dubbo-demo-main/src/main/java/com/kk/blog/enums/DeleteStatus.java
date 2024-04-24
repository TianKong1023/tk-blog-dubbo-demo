package com.kk.blog.enums;

public enum DeleteStatus {
    DISPLAY("0", "显示"),
    DELETE("1", "逻辑删除");
    private String code;
    private String disc;

    DeleteStatus(String code, String disc) {
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
