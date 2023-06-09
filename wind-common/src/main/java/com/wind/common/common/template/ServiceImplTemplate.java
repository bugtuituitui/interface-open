package com.wind.common.common.template;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wind.common.common.lang.PageData;
import com.wind.common.common.template.query.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * ServiceImpl 模板
 *
 * @author kfg
 * @date 2023/6/7 21:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceImplTemplate<T> {

    private ServiceImpl service;

    /**
     * 动态获得主键
     *
     * @param entity
     * @param primaryKeyFieldName
     * @param <T>
     * @param <PK>
     * @return
     */
    private static <T, PK extends Serializable> PK getPrimaryKeyValue(T entity, String primaryKeyFieldName) {
        Field primaryKeyField = ReflectionUtils.findField(entity.getClass(), primaryKeyFieldName);
        ReflectionUtils.makeAccessible(primaryKeyField);
        return (PK) ReflectionUtils.getField(primaryKeyField, entity);
    }

    /**
     * 添加
     *
     * @param entity
     * @return
     */
    public boolean insert(T entity) {
        return service.save(entity);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    public boolean delete(Long id) {
        return service.removeById(id);
    }

    /**
     * 修改
     *
     * @param entity
     * @return
     */
    public boolean update(T entity) {
        return service.updateById(entity);
    }

    /**
     * 条件查询
     *
     * @param <T>
     * @return
     */
    public <T> PageData<T> list(BaseQuery query, LambdaQueryWrapper<T> queryWrapper) {

        Page<T> page = new Page<>(query.getCurrent(), query.getSize());

        // 根据id查询详情
        if (query.getId() != null) {
            List list = new ArrayList<>();
            list.add(service.getById(query.getId()));
            return PageData.get(list, list.size());
        }


        // 条件查询分页
        service.page(page, queryWrapper);
        return PageData.get(page.getRecords(), page.getTotal());
    }

}
