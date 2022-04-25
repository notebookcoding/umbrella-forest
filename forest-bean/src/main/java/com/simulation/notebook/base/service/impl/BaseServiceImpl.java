package com.simulation.notebook.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simulation.notebook.base.entity.BaseBean;
import com.simulation.notebook.base.mapper.IBaseMapper;
import com.simulation.notebook.base.service.BaseService;

/**
 * 平台业务service实现的公共父类
 * @author Administrator
 */
public class BaseServiceImpl<M extends IBaseMapper<T>, T extends BaseBean> extends ServiceImpl<M, T> implements BaseService<T> {


}
