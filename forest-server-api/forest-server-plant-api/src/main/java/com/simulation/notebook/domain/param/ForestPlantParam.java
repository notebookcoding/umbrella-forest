package com.simulation.notebook.domain.param;

import com.simulation.notebook.base.param.PageParam;
import lombok.Data;

/**
 *
 * @author Administrator
 */
@Data
public class ForestPlantParam extends PageParam {

    private static final long serialVersionUID = 2098328339242399044L;


    /**
     * 中文名称
     */
    private String nameCn;

    /**
     * 植物类别:1.裸子植物、2.被子植物、3.藻类植物、4.菌类植物、5.地衣植物、6.苔藓植物、7.蕨类植物',
     */
    private Integer plantCategory;
}
