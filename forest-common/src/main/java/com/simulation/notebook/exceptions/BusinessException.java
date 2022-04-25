package com.simulation.notebook.exceptions;

import com.simulation.notebook.constant.Constants;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {


    private int code = Constants.ResultCode.ERROR;
    private String message;

    public BusinessException() {
        super();
    }

    public BusinessException(Exception e) {
        super(e);
    }

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(int code) {
        super("");
        this.code = code;
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public BusinessException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
}
