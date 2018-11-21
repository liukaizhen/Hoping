package com.hp.libcore.base;

import com.google.gson.annotations.SerializedName;

/**
 * POJO统一封装类
 * @param <T>
 */
public class BasePoJo<T>{
    @SerializedName(value = "code", alternate = {"error_code"})
    private int code = -1;
    @SerializedName(value = "msg", alternate = {"message", "reason"})
    private String msg;
    @SerializedName(value = "data", alternate = {"result"})
    private T data;

    public BasePoJo() {
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

    public boolean isSuccess() {
        return code == 0;
    }

    @Override
    public String toString() {
        return "BasePoJo{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
