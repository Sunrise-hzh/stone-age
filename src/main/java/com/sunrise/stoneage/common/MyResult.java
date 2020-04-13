package com.sunrise.stoneage.common;

public class MyResult<T> {
    private long code;
    private String msg;
    private T data;

    public MyResult(){}

    public MyResult(long code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public MyResult(ResultCodeEnum resultCodeEnum, T data){
        this(resultCodeEnum.getCode(), resultCodeEnum.getMsg(), data);
    }

    public static <T> MyResult<T> success(T data){
        return new MyResult<>(ResultCodeEnum.SUCCESS, data);
    }

    public static <T> MyResult<T> success(String msg, T data){
        return new MyResult<>(ResultCodeEnum.SUCCESS.getCode(), msg, data);
    }

    public static <T> MyResult<T> failure(){
        return new MyResult<>(ResultCodeEnum.FAILURE, null);
    }

    public static <T> MyResult<T> failure(String msg){
        return new MyResult<>(ResultCodeEnum.FAILURE.getCode(), msg, null);
    }

    public static <T> MyResult<T> failure(ResultCodeEnum resultCodeEnum, String msg){
        return new MyResult<>(resultCodeEnum.getCode(), msg, null);
    }

    public static <T> MyResult<T> forbidden(){
        return new MyResult<>(ResultCodeEnum.FORBIDDEN,null);
    }

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
