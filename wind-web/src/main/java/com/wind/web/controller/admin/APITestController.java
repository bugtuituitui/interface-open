package com.wind.web.controller.admin;

import com.wind.client.client.WindAPIClient;
import com.wind.common.common.lang.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

/**
 * @author kfg
 * @date 2023/6/8 13:56
 */
@RestController
@RequestMapping("/admin/apiTest")
public class APITestController {

    @Autowired
    private WindAPIClient windAPIClient;

    @RequestMapping("/execute")
    public Result executeApi(String api, String body) throws NoSuchAlgorithmException {
        return Result.success(windAPIClient.sendRequest(body, api));
    }
}
