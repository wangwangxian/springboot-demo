package com.jw.demo.domain;

import com.jw.demo.enums.ResultEnum;

import java.io.Serializable;

/**
 * @author jony
 * @date 2018-11-27
 * @param <T>
 */
public class ResultMap<T> implements Serializable {

    private int code;

    private String msg;

    private T data;

    public ResultMap() {
        super();
    }

    public ResultMap(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public ResultMap(int code, String msg, T data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultMap(ResultEnum resultEnum) {
        super();
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getDesc();
    }

    public ResultMap(ResultEnum resultEnum,T date) {
        super();
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getDesc();
        this.data = date;
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
