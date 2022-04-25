package com.simulation.notebook.domain.param;

import com.simulation.notebook.base.param.PageParam;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 森林动物入参
 * @author Administrator
 */
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
public class ForestAnimalParam extends PageParam {

    /**
     * 森林动物表主键id
     */
    private Long forestAnimalId;

    /**
     * 中文名称
     */
    private String nameCn;

    /**
     * 动物类别:1.哺乳类、2.鸟类、3.爬行类、4.两栖类、5.鱼类、6.原生动物、7.扁形动物、8.腔肠动物、9.线形动物、10.环节动物、11.软体动物、12.棘皮动物、13.节肢动物
     */
    private Integer animalCategory;
}
