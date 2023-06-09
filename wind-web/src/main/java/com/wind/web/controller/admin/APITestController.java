package com.wind.web.controller.admin;

import com.wind.client.OpenApiClient;
import com.wind.common.common.lang.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.security.NoSuchAlgorithmException;

/**
 * @author kfg
 * @date 2023/6/8 13:56
 */
@RestController
@RequestMapping("/admin/apiTest")
public class APITestController {

    @Autowired
    private OpenApiClient openApiClient;

    @PostConstruct
    void init() {
        System.out.println("===============");
        System.out.println(openApiClient.getApiKey());
    }

    @RequestMapping("/execute")
    public Result executeApi(String api, String body) throws NoSuchAlgorithmException {

        return Result.success(openApiClient.sendRequest(body, api));
    }
}
