package com.wind.common.common.entity.enums;

/**
 * 角色枚举
 *
 * @author kfg
 * @date 2023/6/7 18:49
 */
public enum RoleEnum {

    USER(0, "普通用户"),
    ADMIN(1, "管理员");

    private int code;

    private String message;

    private RoleEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

}
