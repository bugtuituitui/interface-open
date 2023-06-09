package com.wind.windinterfaces.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kfg
 * @date 2023/6/9 11:16
 */
@RestController
public class APIController {

    @RequestMapping("/getWeather")
    public String getWeather() {
        return "天气：晴";
    }
}
