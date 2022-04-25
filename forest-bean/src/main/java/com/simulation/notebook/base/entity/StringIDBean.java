package com.simulation.notebook.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 随机唯一字符串主键的公共实体类bean
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StringIDBean extends CommonBean {

    /**
     * uuid字符串类型主键id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    protected String id;

}
