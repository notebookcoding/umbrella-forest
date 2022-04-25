package com.simulation.notebook.mapper;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simulation.notebook.base.mapper.IBaseMapper;
import com.simulation.notebook.domain.entity.ForestPlant;
import com.simulation.notebook.domain.param.ForestPlantParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 森林植物表 Mapper 接口
 * </p>
 * @author ChenChen
 * @since 2022-03-11
 */
public interface ForestPlantMapper extends IBaseMapper<ForestPlant> {

    /**
     * 分页获取森林植物列表
     * @param page
     * @param param
     * @return
     */
    Page<ForestPlant> listForestPlantPage(IPage page, @Param("param") ForestPlantParam param);

    /**
     *  获取森林植物列表
     * @param param
     * @return
     */

    List<ForestPlant> listForestPlant(  @Param("param") ForestPlantParam param);
}
