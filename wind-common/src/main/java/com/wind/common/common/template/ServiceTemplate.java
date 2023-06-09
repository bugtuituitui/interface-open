package com.wind.common.common.template;

import com.wind.common.common.lang.PageData;
import com.wind.common.common.template.query.BaseQuery;

/**
 * service 模板
 *
 * @author kfg
 * @date 2023/6/8 8:55
 */
public interface ServiceTemplate<T> {

    /**
     * 添加
     *
     * @param entity
     * @return
     */
    boolean insertEntity(T entity);

    /**
     * 修改
     *
     * @param entity
     * @return
     */
    boolean updateEntity(T entity);

    /**
     * 删除
     *
     * @param entity
     * @return
     */
    boolean deleteEntity(T entity);

    /**
     * 条件查询
     *
     * @param query
     * @return
     */
    PageData<T> listEntity(BaseQuery query);
}
