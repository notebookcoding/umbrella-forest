package com.simulation.notebook.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simulation.notebook.base.entity.BaseBean;
import com.simulation.notebook.exceptions.BeanExceptionEnum;
import com.simulation.notebook.vo.CommonVO;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 类型转换相关的工具类
 * @author Administrator
 */
public class ConvertUtil {

    /**
     * 将业务实体类bean转换成返回前端的VO类对象
     * @param beans 分页业务实体类bean
     * @param clazz 返回前端的VO类定义
     * @param <V>   返回前端对象泛型。继承自CommonVO
     * @param <B>   实体类bean对象泛型。继承自BaseBean
     * @return 返回转换后的VO分页封装
     */
    public static <V extends CommonVO, B extends BaseBean> IPage<V> convertBeanToVO(IPage<B> beans, Class<V> clazz) {
        // 返回结果
        IPage<V> result = new Page<>(beans.getCurrent(), beans.getSize());

        List<B> records = beans.getRecords();
        if (CollectionUtils.isNotEmpty(records)) {
            result.setRecords(convertBeanToVO(records, clazz));
        } else {
            result.setRecords(Collections.emptyList());
        }
        result.setTotal(beans.getTotal());
        result.setPages(beans.getPages());
        return result;
    }

    /**
     * 将业务实体类bean转换成返回前端的VO类对象
     * @param beanList 分页业务实体类bean
     * @param clazz    返回前端的VO类定义
     * @param <V>      返回前端对象泛型。继承自CommonVO
     * @param <B>      实体类bean对象泛型。继承自BaseBean
     * @return 返回转换后的VO分页封装
     */
    public static <V extends CommonVO, B extends BaseBean> List<V> convertBeanToVO(List<B> beanList, Class<V> clazz) {
        if (CollectionUtils.isNotEmpty(beanList)) {
            return beanList.stream()
                    .map(e -> {
                        try {
                            V vo = clazz.getDeclaredConstructor().newInstance();
                            BeanUtils.copyProperties(e, vo);
                            return vo;
                        } catch (Exception ex) {
                            throw new RuntimeException(BeanExceptionEnum.NO_DECLARED_CONSTRUCTOR_ERROR.getName());
                        }
                    })
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

}
