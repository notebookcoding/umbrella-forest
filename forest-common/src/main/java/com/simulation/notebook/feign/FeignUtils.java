package com.simulation.notebook.feign;


import com.simulation.notebook.exceptions.RemoteCallException;
import com.simulation.notebook.result.Result;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

import java.util.Objects;

/**
 * FeiUtils
 * @author XueRi
 * @since 2021/10/28
 */
@UtilityClass
public class FeignUtils {

    /**
     * 远程调用解析
     * @param result 远程调用返回结果集
     * @return 结果集内Data数据
     */
    public static <T> T parse(Result<T> result) {
        return parse(result, false);
    }

    /**
     * 远程调用解析
     * @param result 远程调用返回结果集
     * @return 结果集内Data数据
     */
    public static <T> T parse(Result<T> result, Boolean skipHttpStatus) {
        // 判断状态码
        if (skipHttpStatus && Objects.equals(HttpStatus.OK.value(), result.getCode())) {
            throw new RemoteCallException();
        }
        return result.getData();
    }
}
