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
public class Invoke implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 调用id
     */
    @TableId(value = "invoke_id", type = IdType.AUTO)
    private Long invokeId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 接口id
     */
    private Long apiId;

    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 接口地址
     */
    private String apiPath;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 调用结果 0：成功 1：失败
     */
    private Integer status;

    /**
     * 描述
     */
    private String desc;

    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;

    @TableLogic
    private Boolean isDeleted;


}
