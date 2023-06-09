package com.wind.common.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wind.common.web.entity.Cert;
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
public interface CertMapper extends BaseMapper<Cert> {

    /**
     * 根据key和secret查询数据
     *
     * @param key
     * @param secret
     * @return
     */
    Cert getByKeyAndSecret(String key, String secret);
}
