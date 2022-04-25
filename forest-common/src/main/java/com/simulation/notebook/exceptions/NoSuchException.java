package com.simulation.notebook.exceptions;


/**
 * 通用不存在异常
 * @author Administrator
 */
public class NoSuchException extends RuntimeException {

    private static final long serialVersionUID = 1859383743103749082L;

    /**
     * 枚举构造方法
     * @param msg 不存在消息
     */
    public NoSuchException(String msg) {
        super(msg);
    }

}
