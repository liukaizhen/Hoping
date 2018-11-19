package com.hp.libcore.base;

import com.google.gson.annotations.SerializedName;

/**
 * 数据统一封装类
 * @param <T>
 */
public class BaseResponse<T>{
    @SerializedName("code")
    private int code = -1;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private T data;

    public BaseResponse() {
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
        return "BaseResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
