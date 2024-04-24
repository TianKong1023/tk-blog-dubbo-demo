package com.kk.blog.enums;

public enum LoginStatus {
    SUCCESS(0, "登录成功！"),
    ERROR(1, "用户名或密码错误！"),
    EXCEPTION(2, "账号状态异常！");



    private int code;
    private String disc;

    LoginStatus(int code, String disc) {
        this.code = code;
        this.disc = disc;
    }

    public int getCode() {
        return code;
    }

    public String getDisc() {
        return disc;
    }

    public static LoginStatus getStatusByCode(int target) {
        for(LoginStatus status : LoginStatus.values()) {
            if(status.code == target) {
                return status;
            }
        }
        return null;
    }
}
