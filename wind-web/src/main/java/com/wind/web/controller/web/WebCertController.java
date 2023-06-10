package com.wind.web.controller.web;


import com.wind.common.common.lang.Result;
import com.wind.common.common.user.RequestHolder;
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
@RequestMapping("/web/cert")
public class WebCertController {

    @Autowired
    private CertServiceImpl certService;

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

