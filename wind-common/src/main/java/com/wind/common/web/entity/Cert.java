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
public class Cert implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 凭证id
     */
    @TableId(value = "cert_id", type = IdType.AUTO)
    private Long certId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * apiKey
     */
    private String apiKey;

    /**
     * apiSecret
     */
    private String apiSecret;

    /**
     * 可调用次数
     */
    private Integer totalCount;

    /**
     * 是否有次数限制
     */
    private Boolean unlimited;


    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;

    @TableLogic
    private Boolean isDeleted;


}
