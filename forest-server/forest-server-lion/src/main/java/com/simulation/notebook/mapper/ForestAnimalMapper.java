package com.simulation.notebook.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simulation.notebook.base.mapper.IBaseMapper;
import com.simulation.notebook.domain.entity.ForestAnimal;
import com.simulation.notebook.domain.param.ForestAnimalParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 森林动物表 Mapper 接口
 * </p>
 * @author ChenChen
 * @since 2022-03-10
 */
public interface ForestAnimalMapper extends IBaseMapper<ForestAnimal> {


    /**
     * 分页获取森林动物列表
     * @param page
     * @param param
     * @return
     */
    Page<ForestAnimal> listForestAnimalPage(Page page, @Param("param") ForestAnimalParam param);
}
