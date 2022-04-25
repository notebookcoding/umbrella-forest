package com.simulation.notebook.base.param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;

/**
 * 前端查询分页公共参数
 * @author Administrator
 */
@Data
public class PageParam implements Serializable {

    /**
     * 当前页
     */
    private long current = 1;

    /**
     * 每页数据量
     */
    private long size = 10;

    /**
     * 返回一个空结果的分页对象
     * @param <V> 指定返回类型的泛型
     * @return 返回空结果的分页对象
     */
    public <V> IPage<V> emptyResult() {
        IPage<V> result = new Page<>(getCurrent(), getSize());
        result.setTotal(0);
        result.setPages(0);
        result.setRecords(Collections.emptyList());
        return result;
    }


}
