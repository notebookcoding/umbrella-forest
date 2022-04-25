package com.simulation.notebook.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自增主键的公共实体类bean
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseBean extends CommonBean {

    /**
     * 长整型自增主键id
     */
    @TableId(type = IdType.AUTO)
    protected Long id;
}
