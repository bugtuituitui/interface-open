package com.wind.common.web.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wind.common.common.lang.PageData;
import com.wind.common.common.template.ServiceImplTemplate;
import com.wind.common.common.template.ServiceTemplate;
import com.wind.common.common.template.query.BaseQuery;
import com.wind.common.common.utils.AssertUtils;
import com.wind.common.web.entity.Api;
import com.wind.common.web.entity.Cert;
import com.wind.common.web.entity.query.CertQuery;
import com.wind.common.web.mapper.CertMapper;
import com.wind.common.web.mapper.UserMapper;
import com.wind.common.web.service.ICertService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CertServiceImpl extends ServiceImpl<CertMapper, Cert> implements ICertService, ServiceTemplate<Cert> {


    private ServiceImplTemplate<Api> template = new ServiceImplTemplate<Api>(this);

    @Autowired
    private UserMapper userMapper;

    /**
     * 添加
     *
     * @param entity
     * @return
     */
    @Override
    public boolean insertEntity(Cert entity) {
        return save(entity);
    }

    /**
     * 修改
     *
     * @param entity
     * @return
     */
    @Override
    public boolean updateEntity(Cert entity) {
        return updateById(entity);
    }

    /**
     * 删除
     *
     * @param entity
     * @return
     */
    @Override
    public boolean deleteEntity(Cert entity) {
        return removeById(entity);
    }

    /**
     * 条件查询
     *
     * @param query
     * @return
     */
    @Override
    public PageData<Cert> listEntity(BaseQuery query) {
        CertQuery condition = (CertQuery) query;

        LambdaQueryWrapper<Cert> queryWrapper = new LambdaQueryWrapper<Cert>()
                .eq(condition.getUid() != null, Cert::getCertId, condition.getUid())
                .eq(StringUtils.isNotEmpty(condition.getUsername()), Cert::getUsername, condition.getUsername());

        return template.list(query, queryWrapper);
    }


    /**
     * 生成凭证
     *
     * @param uid
     * @return
     */
    @Override
    public void generateCert(Long uid) {
        LambdaQueryWrapper<Cert> queryWrapper = new LambdaQueryWrapper<Cert>()
                .eq(Cert::getUserId, uid);

        AssertUtils.greaterThanZero(baseMapper.selectCount(queryWrapper), "凭证已生成");

        Cert cert = new Cert();
        cert.setUserId(uid);
        cert.setUsername(userMapper.selectById(uid).getUsername());
        cert.setApiKey(RandomUtil.randomString(10));
        cert.setApiSecret(RandomUtil.randomString(10));
        save(cert);
    }

    /**
     * 查询凭证
     *
     * @param uid
     * @return
     */
    @Override
    public Cert getCert(Long uid) {

        LambdaQueryWrapper<Cert> queryWrapper = new LambdaQueryWrapper<Cert>()
                .eq(Cert::getUserId, uid);

        Cert cert = baseMapper.selectOne(queryWrapper);
        AssertUtils.isEmpty(cert, "未生成凭证");

        return cert;
    }
}
