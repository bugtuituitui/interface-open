package com.wind.common.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wind.common.common.lang.PageData;
import com.wind.common.common.template.ServiceImplTemplate;
import com.wind.common.common.template.ServiceTemplate;
import com.wind.common.common.template.query.BaseQuery;
import com.wind.common.web.entity.Api;
import com.wind.common.web.entity.query.ApiQuery;
import com.wind.common.web.mapper.ApiMapper;
import com.wind.common.web.service.IApiService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lemon
 * @since 2023-06-07
 */
@Service
public class ApiServiceImpl extends ServiceImpl<ApiMapper, Api> implements IApiService, ServiceTemplate<Api> {

    private ServiceImplTemplate<Api> template = new ServiceImplTemplate<Api>(this);

    /**
     * 添加api
     *
     * @param api
     * @return
     */
    @Override
    public boolean insertEntity(Api api) {
        return save(api);
    }

    /**
     * 删除api
     *
     * @param api
     * @return
     */
    @Override
    public boolean deleteEntity(Api api) {
        return removeById(api);
    }


    /**
     * 修改api
     *
     * @param api
     * @return
     */
    @Override
    public boolean updateEntity(Api api) {

        LambdaQueryWrapper<Api> queryWrapper = new LambdaQueryWrapper<Api>()
                .eq(Api::getApiPath, api.getApiPath());

        return updateById(api);
    }

    /**
     * 条件查询api
     *
     * @param query
     * @return
     */
    @Override
    public PageData<Api> listEntity(BaseQuery query) {

        ApiQuery condition = (ApiQuery) query;

        LambdaQueryWrapper<Api> queryWrapper = new LambdaQueryWrapper<Api>()
                .eq(StringUtils.isNotEmpty(condition.getApiName()), Api::getApiName, condition.getApiName());

        return template.list(query, queryWrapper);

    }
}
