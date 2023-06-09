package com.wind.portal.controller.admin;

import com.wind.common.common.lang.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kfg
 * @date 2023/6/7 19:03
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/auth")
    public Result auth() {
        return Result.success();
    }
}
