package com.simulation.notebook.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 森林植物表 返回结果
 * @author Administrator
 */
@Data
public class ForestPlantVO  {

    private static final long serialVersionUID = -7297641567939372006L;
    /**
     * 中文名称
     */
    private String nameCn;

    /**
     * 别称
     */
    private String alias;

    /**
     * 英文名称
     */
    private String nameEn;

    /**
     * 简单介绍
     */
    private String introduction;

    /**
     * 图片地址
     */
    private String imagePath;

    /**
     * 植物类别:1.裸子植物、2.被子植物、3.藻类植物、4.菌类植物、5.地衣植物、6.苔藓植物、7.蕨类植物
     */
    private Integer plantCategory;

    /**
     * 植物类别名称:裸子植物、被子植物、藻类植物、菌类植物、地衣植物、苔藓植物、蕨类植物
     */
    private String plantCategoryName;
}
