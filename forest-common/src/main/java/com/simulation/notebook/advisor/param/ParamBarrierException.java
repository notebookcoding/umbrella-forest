package com.simulation.notebook.advisor.param;

/**
 * 参数屏障异常
 * @author Administrator
 */
public class ParamBarrierException extends RuntimeException {

    public ParamBarrierException(String fieldName) {
        super("The parameter [" + fieldName + "] is empty");
    }
}
