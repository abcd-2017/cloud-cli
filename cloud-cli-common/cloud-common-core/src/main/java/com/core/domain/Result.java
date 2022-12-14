package com.core.domain;

import com.core.constant.Constants;

import java.io.Serializable;

/**
 * @author lk
 * @date 2022-12-09
 * 公共返回对象
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 成功
     */
    public static final int SUCCESS = Constants.SUCCESS;
    /**
     * 失败
     */
    public static final int FAIL = Constants.FAIL;
    private int code;

    private String msg;

    private T data;

    public static <T> Result<T> ok() {
        return restResultesult(null, SUCCESS, null);
    }

    public static <T> Result<T> ok(T data) {
        return restResultesult(data, SUCCESS, null);
    }

    public static <T> Result<T> ok(T data, String msg) {
        return restResultesult(data, SUCCESS, msg);
    }

    public static <T> Result<T> fail() {
        return restResultesult(null, FAIL, null);
    }

    public static <T> Result<T> fail(String msg) {
        return restResultesult(null, FAIL, msg);
    }

    public static <T> Result<T> fail(T data) {
        return restResultesult(data, FAIL, null);
    }

    public static <T> Result<T> fail(T data, String msg) {
        return restResultesult(data, FAIL, msg);
    }

    public static <T> Result<T> fail(int code, String msg) {
        return restResultesult(null, code, msg);
    }

    private static <T> Result<T> restResultesult(T data, int code, String msg) {
        Result<T> apiResultesult = new Result<>();
        apiResultesult.setCode(code);
        apiResultesult.setData(data);
        apiResultesult.setMsg(msg);
        return apiResultesult;
    }

    public static <T> Boolean isError(Result<T> ret) {
        return !isSuccess(ret);
    }

    public static <T> Boolean isSuccess(Result<T> ret) {
        return Result.SUCCESS == ret.getCode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
