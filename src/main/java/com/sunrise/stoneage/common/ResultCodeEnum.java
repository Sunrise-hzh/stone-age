package com.sunrise.stoneage.common;

public enum ResultCodeEnum {
    SUCCESS(200, "操作成功"),
    FAILURE(500, "操作失败"),
    VALIDATE_FAILED(404, "参数校验失败"),
    UNAUTHORIZED(401, "暂未登录或token已过期"),
    FORBIDDEN(403, "没有权限");

    ResultCodeEnum(long code, String msg){
        this.code = code;
        this.msg = msg;
    }

    private long code;
    private String msg;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
