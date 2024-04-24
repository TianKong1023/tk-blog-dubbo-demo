package com.kk.blog.enums;

public enum ConfirmRegisterOperation {
    ARGEE(1, "同意"),
    REJECT(0, "拒绝");

    private int code;
    private String disc;

    ConfirmRegisterOperation(int code, String disc) {
        this.code = code;
        this.disc = disc;
    }

    public int getCode() {
        return code;
    }

    public String getDisc() {
        return disc;
    }

}
