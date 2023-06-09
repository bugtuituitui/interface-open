package com.wind.portal.controller.admin;


import com.wind.common.common.lang.Result;
import com.wind.common.common.user.RequestHolder;
import com.wind.common.web.entity.Cert;
import com.wind.common.web.entity.query.CertQuery;
import com.wind.common.web.service.impl.CertServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lemon
 * @since 2023-06-07
 */
@RestController
@RequestMapping("/cert")
public class CertController {


    @Autowired
    private CertServiceImpl certService;


    /**
     * 添加
     *
     * @param cert
     * @return
     */
    @PostMapping("/insert")
    public Result insert(Cert cert) {
        return Result.success(certService.insertEntity(cert));
    }

    /**
     * 删除
     *
     * @param cert
     * @return
     */
    @PostMapping("/delete")
    public Result delete(Cert cert) {
        return Result.success(certService.deleteEntity(cert));
    }

    /**
     * 修改
     *
     * @param cert
     * @return
     */
    @PostMapping("/update")
    public Result update(Cert cert) {
        return Result.success(certService.updateEntity(cert));
    }

    /**
     * 条件查询
     *
     * @param query
     * @return
     */
    @PostMapping("/list")
    public Result list(CertQuery query) {
        return Result.success(certService.listEntity(query));
    }


    /**
     * 生成凭证
     *
     * @return
     */
    @PostMapping("/generate")
    public Result generateCert() {
        certService.generateCert(RequestHolder.get().getUid());
        return Result.success();
    }


    /**
     * 查询凭证
     *
     * @return
     */
    @PostMapping("/get")
    public Result get() {
        return Result.success(certService.getCert(RequestHolder.get().getUid()));
    }
}

