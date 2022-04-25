package com.simulation.notebook.base.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 含有排序字段的实体类bean
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderedBean extends BaseBean {

    /**
     * 排序
     */
    private Integer ordered;

}
