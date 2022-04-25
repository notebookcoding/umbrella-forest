package com.simulation.notebook.api;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simulation.notebook.advisor.annotation.ParamBarrier;
import com.simulation.notebook.domain.param.ForestPlantParam;
import com.simulation.notebook.domain.vo.ForestPlantVO;
import com.simulation.notebook.result.Result;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 森林植物模块对外微服务
 * 植物类别接口（植物微服务的其中一个接口）
 * @author Administrator
 */
public interface PlantCategoryServerApi {


    /**
     * 配置说明: @FeignClient(value = ForestPlantServiceName.FOREST_PLANT_SERVICE, contextId = PlantCategoryServerApi.CONTEXT_ID)
     */
    String CONTEXT_ID = "plantCategoryServerApi";

    /**
     * 分页获取森林植物列表
     * @param param
     * @return
     */
    @GetMapping("/forest/plant/listForestPlantPage")
    Result<Page<ForestPlantVO>> listForestPlantPage(@SpringQueryMap ForestPlantParam param);

    /**
     * 获取数据
     * @param param
     * @return
     */
    @GetMapping(value = "/forest/plant/forestPlant")
    Result<List<ForestPlantVO>>listForestPlant(@SpringQueryMap ForestPlantParam param);
}
