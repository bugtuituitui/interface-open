package com.wind.common.web.entity.query;

import com.wind.common.common.template.query.BaseQuery;
import lombok.Data;

/**
 * @author kfg
 * @date 2023/6/7 21:25
 */
@Data
public class ApiQuery extends BaseQuery {

    private Integer apiId;

    private String apiName;

}
