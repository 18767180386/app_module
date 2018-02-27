package com.aiju.zyb.bean.base;

/**
 * Created by AIJU on 2017-04-14.
 */

public class Result<T> {
    public static final String RESULT_OK = "000";
    public static  final int Http_Success=200;
    private String code;
    private String msg;
    private T data;
    public int status;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

