package com.wind.common.web.entity.query;

import com.wind.common.common.template.query.BaseQuery;
import lombok.Data;

/**
 * @author kfg
 * @date 2023/6/8 9:31
 */
@Data
public class CertQuery extends BaseQuery {

    private Long uid;
    private String username;
}
