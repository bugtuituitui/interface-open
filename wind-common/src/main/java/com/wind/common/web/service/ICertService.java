package com.wind.common.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wind.common.web.entity.Cert;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lemon
 * @since 2023-06-07
 */
public interface ICertService extends IService<Cert> {

    /**
     * 生成凭证
     *
     * @return
     */
    void generateCert(Long uid);

    /**
     * 查询凭证
     *
     * @param uid
     * @return
     */
    Cert getCert(Long uid);
}
