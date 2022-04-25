package com.simulation.notebook.handler;

import com.simulation.notebook.advisor.param.ParamBarrierException;
import com.simulation.notebook.constant.Constants;
import com.simulation.notebook.exceptions.BusinessException;
import com.simulation.notebook.exceptions.RemoteCallException;
import com.simulation.notebook.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 整个项目的统一监控
 */
@Slf4j
@ControllerAdvice
@ResponseBody
@Order()
public class GlobalHandler {

    /**
     * 业务上抛异常捕获
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public Result<?> handlerBusinessException(HttpServletRequest request, BusinessException e) {
        int code = e.getCode();
        String msg = e.getMessage();
        String obj = null;
        if (Constants.ResultCode.ERROR_PWD == code) {
            // 剩余的次数
            obj = msg.substring(msg.length() - 1);
            msg = msg.substring(0, msg.length() - 1);
        }
        log.error("[" + code + "] " + msg, e);
        return Result.error(code, msg, obj);
    }

    /**
     * 方法请求方式错误异常捕获
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public Result<?> handlerHttpRequestMethodNotSupportedException(HttpServletRequest request, BusinessException e) {
        log.error("[" + e.getCode() + "] " + e.getMessage(), e);
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * ParamBarrier 注解异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = ParamBarrierException.class)
    public Result<?> paramBarrierExceptionHandler(ParamBarrierException e) {
        return Result.error(Constants.ResultCode.ERROR, e.getMessage());
    }

    /**
     * Feign远程调用异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = RemoteCallException.class)
    public Result<?> remoteCallExceptionExceptionHandler(RemoteCallException e) {
        log.error(e.getMessage(), e);
        return Result.error(Constants.ResultCode.EXCEPTION, e.getMessage());
    }

    /**
     * 全局拦截参数未传异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public Result<?> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        return Result.error(Constants.ResultCode.EXCEPTION, "参数不能为空！");
    }

    /**
     * 系统功能异常捕获
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Result<?> exceptionHandler(HttpServletRequest request, Exception e) {
        log.error(e.getMessage(), e);
        return Result.error(Constants.ResultCode.EXCEPTION, "请稍候再试或联系管理员！");
    }
}
