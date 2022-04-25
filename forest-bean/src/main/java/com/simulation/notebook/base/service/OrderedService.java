package com.simulation.notebook.base.service;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.simulation.notebook.base.entity.OrderedBean;
import com.simulation.notebook.base.param.OrderedParam;


import java.io.Serializable;
import java.util.Map;

/**
 * 排序类公共父类
 * @author Administrator
 */
public interface OrderedService<T extends OrderedBean> extends BaseService<T> {

    /**
     * 将指定param的数据上移/下移
     * @param param 通用排序入参
     */
    void ordered(OrderedParam param);

    /**
     * 将指定{id}的数据上移/下移
     * @param id   数据主键id。非空
     * @param move 移动方向。非空
     */
    void ordered(Serializable id, int move);

    /**
     * 指定where业务场景下的数据上移/下移
     * @param id       数据主键id。非空
     * @param move     移动方向。非空
     * @param whereSql 个性化业务指定查询参数。注：key必须是数据库字段。例如：{"parent_id": 1}，查询相同parentId下的数据排序
     */
    void ordered(Serializable id, int move, Map<String, Object> whereSql);

    /**
     * 指定where业务场景下的数据上移/下移
     * @param param    将指定param的数据上移/下移
     * @param whereSql 个性化业务指定查询参数。注：key必须是数据库字段。例如：{"parent_id": 1}，查询相同parentId下的数据排序
     */
    void ordered(OrderedParam param, Map<String, Object> whereSql);

    /**
     * 指定where业务场景下的数据上移/下移
     * @param orderedBean 业务对象。非空
     * @param move        移动方向。非空
     */
    void ordered(T orderedBean, int move);

    /**
     * 指定where业务场景下的数据上移/下移
     * @param orderedBean 业务对象。非空
     * @param move        移动方向。非空
     * @param whereSql    个性化业务指定查询参数。注：key必须是数据库字段。例如：{"parent_id": 1}，查询相同parentId下的数据排序
     */
    void ordered(T orderedBean, int move, Map<String, Object> whereSql);

    /**
     * 指定where业务场景下的数据上移/下移
     * @param id       数据主键id。非空
     * @param move     移动方向。非空
     * @param whereSql 个性化业务指定查询参数。注：key为SFunction。例如：{SysUser::getParentId: 1}，查询相同parentId下的数据排序
     */
    void orderedLambda(Serializable id, int move, Map<SFunction<T, ?>, Object> whereSql);

    /**
     * 指定where业务场景下的数据上移/下移
     * @param param    将指定param的数据上移/下移
     * @param whereSql 个性化业务指定查询参数。注：key为SFunction。例如：{SysUser::getParentId: 1}，查询相同parentId下的数据排序
     */
    void orderedLambda(OrderedParam param, Map<SFunction<T, ?>, Object> whereSql);

    /**
     * 指定where业务场景下的数据上移/下移
     * @param orderedBean 业务对象。非空
     * @param move        移动方向。非空
     * @param whereSql    个性化业务指定查询参数。注：key为SFunction。例如：{SysUser::getParentId: 1}，查询相同parentId下的数据排序
     */
    void orderedLambda(T orderedBean, int move, Map<SFunction<T, ?>, Object> whereSql);

    /**
     * 获取排序字段最大值
     * @return 排序字段最大值
     */
    Integer getMaxOrdered();

    /**
     * 指定where业务场景下的  排序字段最大值
     * @param whereSql 个性化业务指定查询参数。注：key为SFunction。例如：{"parent_id": 1}，查询相同parentId下的数据排序
     * @return 排序字段最大值
     */
    Integer getMaxOrdered(Map<String, Object> whereSql);

    /**
     * 指定where业务场景下的  排序字段最大值
     * @param whereSql 个性化业务指定查询参数。注：key为SFunction。例如：{SysUser::getParentId: 1}，查询相同parentId下的数据排序
     * @return 排序字段最大值
     */
    Integer getMaxOrderedLambda(Map<SFunction<T, ?>, Object> whereSql);

}
