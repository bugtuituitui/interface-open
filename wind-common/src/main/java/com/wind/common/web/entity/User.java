package com.wind.common.web.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
@Validated
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名为空")
    @Pattern(regexp = "^[a-zA-Z]{6,9}$", message = "6-8字母")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码为空")
    @Pattern(regexp = "^[a-zA-Z]{6,9}$", message = "6-8字母")
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 角色: 0：普通用户 1：管理员
     */
    private Integer role;

    /**
     * 状态 0：正常 1：禁用
     */
    private Integer status;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    @TableLogic
    private Boolean isDeleted;


}
