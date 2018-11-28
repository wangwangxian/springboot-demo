package com.jw.demo.enums;

public enum ResultEnum {

    OK(200, "成功"),

    INTERNAL_ERROR(500, "服务器内部错误"),

    UNKNOWN(-1, "未知错误");


    private Integer code;

    private String desc;// 描述

    ResultEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
