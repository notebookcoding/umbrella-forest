package com.simulation.notebook.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.simulation.notebook.base.entity.OrderedBean;
import com.simulation.notebook.base.mapper.IBaseMapper;
import com.simulation.notebook.base.param.OrderedParam;
import com.simulation.notebook.base.service.OrderedService;
import com.simulation.notebook.constant.SqlConstants;
import com.simulation.notebook.enums.OrderedEnum;
import com.simulation.notebook.exceptions.BeanExceptionEnum;
import com.simulation.notebook.exceptions.BusinessException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * 排序类公共父类
 * @author Administrator
 */
public class OrderedServiceImpl<M extends IBaseMapper<T>, T extends OrderedBean> extends BaseServiceImpl<M, T> implements OrderedService<T> {

    /**
     * 数据库 ordered 字段
     */
    private final String orderedColumn = SqlConstants.ORDERED;

    /**
     * 限制查询个数为一
     */
    private final String limitOne = SqlConstants.LIMIT_ONE;

    /**
     * 将指定param的数据上移/下移
     * @param param 通用排序入参
     */
    @Override
    public void ordered(OrderedParam param) {
        ordered(param.getPrimaryId(), param.getMove());
    }

    /**
     * 将指定{id}的数据上移/下移
     * @param id   数据主键id。非空
     * @param move 移动方向。非空
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void ordered(Serializable id, int move) {
        ordered(id, move, null);
    }

    /**
     * 指定where业务场景下的数据上移/下移
     * @param id       数据主键id。非空
     * @param move     移动方向。非空
     * @param whereSql 个性化业务指定查询参数。注：key必须是数据库字段。例如：{"parent_id": 1}，查询相同parentId下的数据排序
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void ordered(Serializable id, int move, Map<String, Object> whereSql) {
        // 根据主键查询出业务数据
        T orderedBean = getById(id);
        ordered(orderedBean, move, whereSql);
    }

    /**
     * 指定where业务场景下的数据上移/下移
     * @param param    将指定param的数据上移/下移
     * @param whereSql 个性化业务指定查询参数。注：key必须是数据库字段。例如：{"parent_id": 1}，查询相同parentId下的数据排序
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void ordered(OrderedParam param, Map<String, Object> whereSql) {
        ordered(
                param.getPrimaryId(),
                param.getMove(),
                whereSql
        );
    }

    /**
     * 指定where业务场景下的数据上移/下移
     * @param orderedBean 业务对象。非空
     * @param move        移动方向。非空
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void ordered(T orderedBean, int move) {
        ordered(orderedBean, move, null);
    }

    /**
     * 指定where业务场景下的数据上移/下移
     * @param orderedBean 业务对象。非空
     * @param move        移动方向。非空
     * @param whereSql    个性化业务指定查询参数。注：key必须是数据库字段。例如：{"parent_id": 1}，查询相同parentId下的数据排序
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void ordered(T orderedBean, int move, Map<String, Object> whereSql) {
        if (Objects.isNull(OrderedEnum.get(move))) {
            throw new BusinessException(BeanExceptionEnum.ILLEGAL_ORDERED_ENUM.getName());
        }
        // 排序字段
        if (Objects.nonNull(orderedBean)) {
            // 查询附近的数据
            QueryWrapper<T> query = Wrappers.query();
            // 只查询id和ordered字段
            query.select(SqlConstants.ID, orderedColumn);
            // 根据指定where条件查询附件的数据
            if (whereSql != null && whereSql.size() > 0) {
                whereSql.forEach(query::eq);
            }

            if (Objects.equals(move, OrderedEnum.UP.getMove())) {
                query.lt(orderedColumn, orderedBean.getOrdered());
                query.orderByDesc(orderedColumn);
            } else {
                query.gt(orderedColumn, orderedBean.getOrdered());
                query.orderByAsc(orderedColumn);
            }
            // 只查询临近的一条
            query.last(limitOne);
            T nearBean = getOne(query);
            swap(orderedBean, nearBean);
        }
    }

    /**
     * 指定where业务场景下的数据上移/下移
     * @param id       数据主键id。非空
     * @param move     移动方向。非空
     * @param whereSql 个性化业务指定查询参数。注：key必须是数据库字段。例如：{SysUser::getParentId: 1}，查询相同parentId下的数据排序
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void orderedLambda(Serializable id, int move, Map<SFunction<T, ?>, Object> whereSql) {
        // 根据主键查询出业务数据
        T orderedBean = getById(id);
        orderedLambda(orderedBean, move, whereSql);
    }

    /**
     * 指定where业务场景下的数据上移/下移
     * @param param    将指定param的数据上移/下移
     * @param whereSql 个性化业务指定查询参数。注：key必须是数据库字段。例如：{SysUser::getParentId: 1}，查询相同parentId下的数据排序
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void orderedLambda(OrderedParam param, Map<SFunction<T, ?>, Object> whereSql) {
        orderedLambda(
                param.getPrimaryId(),
                param.getMove(),
                whereSql
        );
    }

    /**
     * 指定where业务场景下的数据上移/下移
     * @param orderedBean 业务对象。非空
     * @param move        移动方向。非空
     * @param whereSql    个性化业务指定查询参数。注：key必须是数据库字段。例如：{SysUser::getParentId: 1}，查询相同parentId下的数据排序
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void orderedLambda(T orderedBean, int move, Map<SFunction<T, ?>, Object> whereSql) {
        if (Objects.isNull(OrderedEnum.get(move))) {
            throw new BusinessException(BeanExceptionEnum.ILLEGAL_ORDERED_ENUM.getName());
        }
        // 排序字段
        if (Objects.nonNull(orderedBean)) {
            // 查询附近的数据
            QueryWrapper<T> query = Wrappers.query();
            // 只查询id和ordered字段
            query.select(SqlConstants.ID, orderedColumn);

            // 判断上移下移 拼接sql
            if (Objects.equals(move, OrderedEnum.UP.getMove())) {
                query.lt(orderedColumn, orderedBean.getOrdered());
                query.orderByDesc(orderedColumn);
            } else {
                query.gt(orderedColumn, orderedBean.getOrdered());
                query.orderByAsc(orderedColumn);
            }

            // 结构变为lambda表达式
            // 根据指定where条件查询附件的数据
            if (ObjectUtils.isNotEmpty(whereSql)) {
                whereSql.forEach((k, v) -> query.lambda().eq(k, v));
            }

            // 只查询临近的一条
            query.last(limitOne);

            T nearBean = getOne(query);
            swap(orderedBean, nearBean);
        }
    }

    /**
     * 数据库插入操作 上下移动 更新当前节点与临近节点的ordered
     * @param currentBean 当前节点
     * @param nearBean    临近节点
     */
    private void swap(T currentBean, T nearBean) {
        if (Objects.nonNull(nearBean)) {
            int tempOrdered = nearBean.getOrdered();
            nearBean.setOrdered(currentBean.getOrdered());
            currentBean.setOrdered(tempOrdered);
            // 执行更新替换
            updateById(currentBean);
            updateById(nearBean);
        }
    }

    /**
     * 获取排序字段最大值
     * @return 排序字段最大值
     */
    @Override
    public Integer getMaxOrdered() {
        return getMaxOrdered(Collections.emptyMap());
    }

    /**
     * 指定where业务场景下的  排序字段最大值
     * @param whereSql 个性化业务指定查询参数。注：key为String。例如：{SysUser::getParentId: 1}，查询相同parentId下的数据排序
     * @return 排序字段最大值
     */
    @Override
    public Integer getMaxOrdered(Map<String, Object> whereSql) {

        QueryWrapper<T> wrapper = Wrappers.<T>query()
                .select(orderedColumn)
                .orderByDesc(orderedColumn);

        // 设置查询条件
        if (ObjectUtils.isNotEmpty(whereSql)) {
            whereSql.forEach(wrapper::eq);
        }
        wrapper.last(limitOne);

        T bean = getOne(wrapper);
        return Objects.nonNull(bean) ? bean.getOrdered() : 0;
    }

    /**
     * 指定where业务场景下的  排序字段最大值
     * @param whereSql 个性化业务指定查询参数。注：key为SFunction。例如：{SysUser::getParentId: 1}，查询相同parentId下的数据排序
     * @return 排序字段最大值
     */
    @Override
    public Integer getMaxOrderedLambda(Map<SFunction<T, ?>, Object> whereSql) {

        // T 泛型使用不熟练
        // 建议使用wrapper进行传输, 使其在eq的基础上多支持 in, lt...
        LambdaQueryWrapper<T> wrapper = Wrappers.<T>query()
                .select(orderedColumn)
                .orderByDesc(orderedColumn)
                .lambda();

        // 设置查询条件
        if (ObjectUtils.isNotEmpty(whereSql)) {
            whereSql.forEach(wrapper::eq);
        }
        wrapper.last(limitOne);

        T bean = getOne(wrapper);
        return Objects.nonNull(bean) ? bean.getOrdered() : 0;
    }

}
