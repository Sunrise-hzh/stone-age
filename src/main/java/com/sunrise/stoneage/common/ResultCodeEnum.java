package com.sunrise.stoneage.common;

public enum ResultCodeEnum {
    SUCCESS(200, "请求成功"),


    FAILURE(400, "请求失败"),   /* 通用客户端错误状态，在没有其他4xx错误代码适用时使用。错误可能类似于格式错误的请求语法，无效的请求消息参数或欺骗性请求路由等。
                                                客户端不应该在没有修改的情况下重复请求。*/

    UNAUTHORIZED(401, "暂未登录或token已过期"),/* 请求要求用户的身份认证，如登录过期或请求没有带上token。*/

    FORBIDDEN(403, "没有权限"),     /* 服务器理解请求客户端的请求，但是拒绝执行此请求。*/

    NOT_FOUND(404, "找不到相关资源"),   /* 服务器根据客户端请求的URL无法找到任何相关资源。404是任何用于掩盖403或401的一个谎言。
                                                          有可能资源是存在的，但是服务器并不想让客户端知道这一真相。 */



    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),  // 服务器内部错误，无法完成请求。


    ;

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
