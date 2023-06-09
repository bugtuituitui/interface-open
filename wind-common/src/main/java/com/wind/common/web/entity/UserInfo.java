package com.wind.common.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author kfg
 * @date 2023/6/7 18:39
 */
@Data
@AllArgsConstructor
public class UserInfo {

    private Integer uid;

    private Integer role;

    private String token;
}
