package com.simulation.notebook.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simulation.notebook.domain.dto.ForestAnimalDTO;
import com.simulation.notebook.domain.entity.ForestAnimal;
import com.simulation.notebook.domain.param.ForestAnimalParam;
import com.simulation.notebook.domain.vo.ForestAnimalVO;
import com.simulation.notebook.base.service.BaseService;

import java.util.List;

/**
 * <p>
 * 森林动物表 服务类
 * </p>
 * @author ChenChen
 * @since 2022-03-10
 */
public interface ForestAnimalService extends BaseService<ForestAnimal> {

    /**
     * 分页获取森林动物列表
     * @param param
     * @return
     */
    Page<ForestAnimalVO> listForestAnimalPage(ForestAnimalParam param);

}
