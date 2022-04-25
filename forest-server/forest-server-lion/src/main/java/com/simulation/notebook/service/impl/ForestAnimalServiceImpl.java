package com.simulation.notebook.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simulation.notebook.domain.entity.ForestAnimal;
import com.simulation.notebook.domain.param.ForestAnimalParam;
import com.simulation.notebook.domain.vo.ForestAnimalVO;
import com.simulation.notebook.mapper.ForestAnimalMapper;
import com.simulation.notebook.service.ForestAnimalService;
import com.simulation.notebook.base.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 森林动物表 服务实现类
 * </p>
 * @author ChenChen
 * @since 2022-03-10
 */
@Service
public class ForestAnimalServiceImpl extends BaseServiceImpl<ForestAnimalMapper, ForestAnimal> implements ForestAnimalService {

    @Resource
    private ForestAnimalMapper forestAnimalMapper;

    /**
     * 分页获取森林动物列表
     * @param param
     * @return
     */
    @Override
    public Page<ForestAnimalVO> listForestAnimalPage(ForestAnimalParam param) {
        Page<ForestAnimalVO> resultPage = new Page<>(param.getCurrent(), param.getSize());
        Page<ForestAnimal> forestAnimalPage = forestAnimalMapper.listForestAnimalPage(resultPage, param);
        BeanUtils.copyProperties(forestAnimalPage, resultPage);
        if (CollectionUtils.isNotEmpty(forestAnimalPage.getRecords())) {
            List<ForestAnimalVO> forestAnimalVOList = getForestAnimalVOList(forestAnimalPage.getRecords());
            resultPage.setRecords(forestAnimalVOList);
        }
        return resultPage;
    }

    private List<ForestAnimalVO> getForestAnimalVOList(List<ForestAnimal> forestAnimalList) {
        return forestAnimalList.stream()
                .map(e -> {
                    ForestAnimalVO forestAnimalVO = new ForestAnimalVO();
                    BeanUtils.copyProperties(e, forestAnimalVO);
                    return forestAnimalVO;
                })
                .collect(Collectors.toList());
    }
}
