package com.simulation.notebook.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 业务平台公共实体类bean属性
 * @author Administrator
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonBean implements Serializable {

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    protected LocalDateTime createTime;

    /**
     * 创建人
     */
    protected Long createBy;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    protected LocalDateTime updateTime;

    /**
     * 修改人
     */
    protected Long updateBy;

    /**
     * 数据删除状态
     */
    @TableLogic
    @TableField("is_deleted")
    protected Boolean deleted;

}
