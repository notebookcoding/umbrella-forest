package com.simulation.notebook.base.param;

import lombok.Data;

/**
 * 通用排序对象入参
 * @author Administrator
 */
@Data
public class OrderedParam {

    /**
     * 修改排序主键Id
     */
    private Long primaryId;

    /**
     * 排序方向(1下移;-1上移)
     */
    private Integer move;
}
