package com.simulation.notebook.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simulation.notebook.domain.dto.ForestAnimalDTO;
import com.simulation.notebook.domain.param.ForestAnimalParam;
import com.simulation.notebook.domain.param.ForestPlantParam;
import com.simulation.notebook.domain.vo.ForestAnimalVO;
import com.simulation.notebook.domain.vo.ForestPlantVO;
import com.simulation.notebook.fegin.PlantCategoryServerApiFeign;
import com.simulation.notebook.feign.FeignUtils;
import com.simulation.notebook.result.Result;
import com.simulation.notebook.service.ForestAnimalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 森林动物表 前端控制器
 * </p>
 * @author ChenChen
 * @since 2022-03-10
 */
@RestController
@RequestMapping("/forest/animal")
public class ForestAnimalController {

    @Resource
    private ForestAnimalService forestAnimalService;

    @Resource
    private PlantCategoryServerApiFeign plantCategoryServerApiFeign;

    /**
     * 分页获取森林动物列表
     * @param param
     * @return
     */
    @GetMapping("/listForestAnimalPage")
    public Result<Page<ForestAnimalVO>> listForestAnimalPage(ForestAnimalParam param) {
        return Result.success(forestAnimalService.listForestAnimalPage(param));
    }

    @GetMapping("/listForestPlantPage")
    public Result<Page<ForestPlantVO>> listForestPlantPage(ForestPlantParam param) {
        Result<Page<ForestPlantVO>> iPageResult = plantCategoryServerApiFeign.listForestPlantPage(param);
        return iPageResult;
    }

    @GetMapping("/listForestPlant")
    public Result<List<ForestPlantVO>> listForestPlant(ForestPlantParam param) {
        Result<List<ForestPlantVO>> listResult = plantCategoryServerApiFeign.listForestPlant(param);
        return listResult;
    }


}
