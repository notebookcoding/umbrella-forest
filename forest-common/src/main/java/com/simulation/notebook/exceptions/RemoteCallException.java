package com.simulation.notebook.exceptions;

/**
 * 远程调用错误
 * @author Administrator
 */
public class RemoteCallException extends RuntimeException {

    public RemoteCallException() {
        super("远程调用异常");
    }
}
