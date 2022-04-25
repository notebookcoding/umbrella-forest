package com.simulation.notebook.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 平台公共异常提示
 * @author Administrator
 */
@Getter
@AllArgsConstructor
public enum BeanExceptionEnum {

    /**
     * 类缺少无参构造方法
     */
    NO_DECLARED_CONSTRUCTOR_ERROR("类缺少无参构造方法！"),

    /**
     * 非法的排序类型值
     */
    ILLEGAL_ORDERED_ENUM("非法的排序类型值！");

    private final String name;

}
