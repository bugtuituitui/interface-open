package com.wind.common.web.entity.query;

import com.wind.common.common.template.query.BaseQuery;
import lombok.Data;

/**
 * @author kfg
 * @date 2023/6/8 9:40
 */
@Data
public class InvokeQuery extends BaseQuery {

    private Integer uid;

    private Integer apiId;
}
