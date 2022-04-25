package com.simulation.notebook.result;

import com.simulation.notebook.constant.Constants;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 所有请求的返回结果封装，所有的方法统一返回Map集合
 * @author Administrator
 */
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -7067776095314563853L;

    private Integer code;

    private String msg;

    private T data;

    private Result() {
        super();
    }

    private Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<T>(Constants.ResultCode.OK, "操作成功！");
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(Constants.ResultCode.OK, null, data);
    }

    public static <T> Result<T> error() {
        return new Result<T>(Constants.ResultCode.ERROR, "operation fail!");
    }

    public static <T> Result<T> error(String msg) {
        return new Result<T>(Constants.ResultCode.ERROR, msg);
    }

    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<T>(code, msg);
    }

    public static <T> Result<T> error(Integer code, String msg, T data) {
        return new Result<T>(code, msg, data);
    }

    public static <T> Result<T> noLogin() {
        return new Result<T>(Constants.ResultCode.ERROR_NO_LOGIN, "用户未登录！");
    }

    public static <T> Result<T> forceLogout() {
        return new Result<T>(Constants.ResultCode.ERROR_FORCE_LOGOUT, "您的账号已在其他地方登录！");
    }

    public static <T> Result<T> mustModPwd() {
        return new Result<T>(Constants.ResultCode.ERROR_MUST_MODIFY_PWD, "请重新修改密码！");
    }

    public static <T> Result<T> illegalRequest() {
        return new Result<T>(Constants.ResultCode.ERROR_ILLEGAL_REQUEST, "非法请求！");
    }

    public static <T> Result<T> illegalUri() {
        return new Result<T>(Constants.ResultCode.ERROR_ILLEGAL_URI, "非法请求！");
    }
}
