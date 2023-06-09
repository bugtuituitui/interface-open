package com.wind.portal.service;

import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author kfg
 * @date 2023/6/9 17:26
 */
@DubboService
public class DemoServiceImpl implements DemoService {

    @Override
    public void say() {
        System.out.println("hello");
    }
}
