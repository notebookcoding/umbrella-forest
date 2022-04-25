package com.simulation.notebook.domain.entity;

import com.simulation.notebook.base.entity.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 森林植物表
 * </p>
 * @author ChenChen
 * @since 2022-03-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ForestPlant extends BaseBean {

    private static final long serialVersionUID = 6087721667279847452L;

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
     * 植物分类:1.种子植物、2.孢子植物
     */
    private Integer plantTaxonomy;

    /**
     * 植物分类名称:种子植物、孢子植物
     */
    private String plantTaxonomyName;

    /**
     * 植物类别:1.裸子植物、2.被子植物、3.藻类植物、4.菌类植物、5.地衣植物、6.苔藓植物、7.蕨类植物
     */
    private Integer plantCategory;

    /**
     * 植物类别名称:裸子植物、被子植物、藻类植物、菌类植物、地衣植物、苔藓植物、蕨类植物
     */
    private String plantCategoryName;


}
