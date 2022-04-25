package com.simulation.notebook.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simulation.notebook.api.PlantCategoryServerApi;
import com.simulation.notebook.domain.entity.ForestPlant;
import com.simulation.notebook.domain.param.ForestPlantParam;
import com.simulation.notebook.domain.vo.ForestPlantVO;
import com.simulation.notebook.exceptions.BusinessException;
import com.simulation.notebook.mapper.ForestPlantMapper;
import com.simulation.notebook.result.Result;
import com.simulation.notebook.service.ForestPlantService;
import com.simulation.notebook.base.service.impl.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 森林植物表 服务实现类
 * </p>
 * @author ChenChen
 * @since 2022-03-11
 */
@Service
public class ForestPlantServiceImpl extends BaseServiceImpl<ForestPlantMapper, ForestPlant> implements ForestPlantService, PlantCategoryServerApi {

    @Resource
    private ForestPlantMapper forestPlantMapper;


    @Override
    public Result<List<ForestPlantVO>> listForestPlant(ForestPlantParam param) {
        List<ForestPlant> forestPlants = forestPlantMapper.listForestPlant(param);
        List<ForestPlantVO> forestAnimalVOList = getForestAnimalVOList(forestPlants);
        return Result.success(forestAnimalVOList);
    }

    /**
     * 页获取森林植物列表
     * @param param
     * @return
     */
    @Override
    public Result<Page<ForestPlantVO>> listForestPlantPage(ForestPlantParam param) {

        Page<ForestPlantVO> resultPage = new Page<>(param.getCurrent(), param.getSize());

        //分页请求数据
        Page<ForestPlant> forestPlantPage = forestPlantMapper.listForestPlantPage(resultPage, param);

        BeanUtils.copyProperties(forestPlantPage, resultPage);
        if (CollectionUtils.isNotEmpty(forestPlantPage.getRecords())) {
            List<ForestPlantVO> forestAnimalVOList = getForestAnimalVOList(forestPlantPage.getRecords());
            resultPage.setRecords(forestAnimalVOList);
        }
        return Result.success(resultPage);
    }

    /**
     * 组装返回结果集
     * @param forestPlantList
     * @return
     */
    private List<ForestPlantVO> getForestAnimalVOList(List<ForestPlant> forestPlantList) {
        return forestPlantList.stream()
                .map(e -> {
                    ForestPlantVO forestPlantVO = new ForestPlantVO();
                    BeanUtils.copyProperties(e, forestPlantVO);
                    return forestPlantVO;
                })
                .collect(Collectors.toList());
    }
}
