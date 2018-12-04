package com.jw.common.mybatis.exception;

/**
 * @author jony
 * @date 2018/09/25 15:07
 */
public class BaseServiceNotFoundException extends RuntimeException{

    public BaseServiceNotFoundException(String message) {
        super(message);
    }
}
