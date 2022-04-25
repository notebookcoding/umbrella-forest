package com.simulation.notebook.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simulation.notebook.advisor.annotation.ParamBarrier;
import com.simulation.notebook.api.PlantCategoryServerApi;
import com.simulation.notebook.domain.param.ForestPlantParam;
import com.simulation.notebook.domain.vo.ForestPlantVO;
import com.simulation.notebook.result.Result;
import com.simulation.notebook.service.ForestPlantService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 森林植物表 前端控制器
 * </p>
 * @author ChenChen
 * @since 2022-03-11
 */
@RestController
@RequestMapping("/forest/plant")
public class ForestPlantController {

    @Resource
    private PlantCategoryServerApi plantCategoryServerApi;

    /**
     * 分页获取森林植物列表
     * @param param
     * @return
     */
    @GetMapping("/listForestPlantPage")
    public Result<Page<ForestPlantVO>> listForestPlantPage(ForestPlantParam param) {
        return  plantCategoryServerApi.listForestPlantPage(param);
    }


    /**
     * 根据名称查找植物名称
     * @param param
     * @return
     */
    @GetMapping("/forestPlant")
    @ParamBarrier(included = {"nameCn"})
    public Result<List<ForestPlantVO>> listForestPlant(ForestPlantParam param) {
        return plantCategoryServerApi.listForestPlant(param);
    }

}
