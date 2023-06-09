package com.wind.common.web.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author lemon
 * @since 2023-06-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Api implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 接口id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 接口地址
     */
    private String apiPath;

    /**
     * 接口参数
     */
    private String apiParams;

    /**
     * 接口描述
     */
    private String apiDesc;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createTime;


    @TableLogic
    private Boolean isDeleted;


}
