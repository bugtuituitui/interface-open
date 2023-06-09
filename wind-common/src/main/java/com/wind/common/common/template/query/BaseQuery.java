package com.wind.common.common.template.query;

import lombok.Data;

/**
 * @author kfg
 * @date 2023/6/7 21:25
 */
@Data
public abstract class BaseQuery {

    private Long id;

    private int current;

    private int size;
}
