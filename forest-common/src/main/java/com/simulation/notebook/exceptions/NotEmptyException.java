package com.simulation.notebook.exceptions;


/**
 * 通用不能为空异常
 * @author Administrator
 */
public class NotEmptyException extends RuntimeException {

    /**
     * 枚举构造方法
     * @param msg 不能为空消息
     */
    public NotEmptyException(String msg) {
        super(msg);
    }
}
