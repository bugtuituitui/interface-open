package com.wind.common.web.entity.query;

import com.wind.common.common.template.query.BaseQuery;
import lombok.Data;

/**
 * @author kfg
 * @date 2023/6/8 11:16
 */
@Data
public class UserQuery extends BaseQuery {

    private String username;
}
