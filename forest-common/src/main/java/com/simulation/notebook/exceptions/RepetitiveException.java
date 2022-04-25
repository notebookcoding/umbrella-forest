package com.simulation.notebook.exceptions;


/**
 * 通用重复异常
 * @author Administrator
 */
public class RepetitiveException extends RuntimeException {

    /**
     * 枚举构造方法
     * @param msg 重复消息
     */
    public RepetitiveException(String msg) {
        super(msg);
    }
}
