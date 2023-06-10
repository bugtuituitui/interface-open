package com.wind.common.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wind.common.web.entity.Api;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author lemon
 * @since 2023-06-07
 */
@Mapper
public interface ApiMapper extends BaseMapper<Api> {

    /**
     * 根据地址查询接口
     *
     * @param path
     * @return
     */
    Api getByPath(String path);
}
