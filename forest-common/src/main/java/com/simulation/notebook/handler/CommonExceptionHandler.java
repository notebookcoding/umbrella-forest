package com.simulation.notebook.handler;


import com.simulation.notebook.constant.Constants;
import com.simulation.notebook.exceptions.NoSuchException;
import com.simulation.notebook.exceptions.NotEmptyException;
import com.simulation.notebook.exceptions.RepetitiveException;
import com.simulation.notebook.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 通用全局处理器
 * @author Administrator
 */
@ControllerAdvice
@Order(1)
@ResponseBody
@Slf4j
public class CommonExceptionHandler {

    /**
     * 重复异常处理器
     * @param e 重复异常
     * @return 异常信息
     */
    @ExceptionHandler(value = RepetitiveException.class)
    public Result<?> repetitiveExceptionHandler(RepetitiveException e) {
        return Result.error(Constants.ResultCode.ERROR, e.getMessage());
    }

    /**
     * 不存在异常处理器
     * @param e 不存在异常
     * @return 异常信息
     */
    @ExceptionHandler(value = NoSuchException.class)
    public Result<?> noSuchExceptionHandler(NoSuchException e) {
        return Result.error(Constants.ResultCode.ERROR, e.getMessage());
    }

    /**
     * 不能为空异常处理器
     * @param e 不能为空异常
     * @return 异常信息
     */
    @ExceptionHandler(value = NotEmptyException.class)
    public Result<?> notEmptyExceptionHandler(NotEmptyException e) {
        return Result.error(Constants.ResultCode.ERROR, e.getMessage());
    }
}
