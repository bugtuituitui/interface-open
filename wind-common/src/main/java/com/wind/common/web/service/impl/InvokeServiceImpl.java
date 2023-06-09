package com.wind.common.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wind.common.common.lang.PageData;
import com.wind.common.common.template.ServiceImplTemplate;
import com.wind.common.common.template.ServiceTemplate;
import com.wind.common.common.template.query.BaseQuery;
import com.wind.common.web.entity.Invoke;
import com.wind.common.web.entity.query.InvokeQuery;
import com.wind.common.web.mapper.InvokeMapper;
import com.wind.common.web.service.IInvokeService;
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
public class InvokeServiceImpl extends ServiceImpl<InvokeMapper, Invoke> implements IInvokeService, ServiceTemplate<Invoke> {

    private ServiceImplTemplate<Invoke> template = new ServiceImplTemplate<Invoke>(this);

    /**
     * 添加
     *
     * @param entity
     * @return
     */
    @Override
    public boolean insertEntity(Invoke entity) {
        return save(entity);
    }

    /**
     * 修改
     *
     * @param entity
     * @return
     */
    @Override
    public boolean updateEntity(Invoke entity) {
        return updateById(entity);
    }

    /**
     * 删除
     *
     * @param entity
     * @return
     */
    @Override
    public boolean deleteEntity(Invoke entity) {
        return removeById(entity);
    }

    /**
     * 条件查询
     *
     * @param query
     * @return
     */
    @Override
    public PageData<Invoke> listEntity(BaseQuery query) {
        InvokeQuery condition = (InvokeQuery) query;

        LambdaQueryWrapper<Invoke> queryWrapper = new LambdaQueryWrapper<Invoke>()
                .eq(condition.getUid() != null, Invoke::getUserId, condition.getUid());

        return template.list(query, queryWrapper);
    }
}
