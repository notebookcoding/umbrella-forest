package com.simulation.notebook.domain.entity;

import com.simulation.notebook.base.entity.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 森林动物表
 * </p>
 * @author ChenChen
 * @since 2022-03-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ForestAnimal extends BaseBean {

    private static final long serialVersionUID = 1L;

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
     * 动物形态:1.脊椎动物、2.无脊椎动物
     */
    private Integer animalMorphology;

    /**
     * 动物形态名称:脊椎动物，无脊椎动物
     */
    private String animalMorphologyName;

    /**
     * 动物类别:1.哺乳类、2.鸟类、3.爬行类、4.两栖类、5.鱼类、6.原生动物、7.扁形动物、8.腔肠动物、9.线形动物、10.环节动物、11.软体动物、12.棘皮动物、13.节肢动物
     */
    private Integer animalCategory;

    /**
     * 动物类别名称:哺乳类、鸟类、爬行类、两栖类、鱼类、原生动物、扁形动物、腔肠动物、线形动物、环节动物、软体动物、棘皮动物、节肢动物
     */
    private String animalCategoryName;


}
