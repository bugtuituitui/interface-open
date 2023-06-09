package com.wind.web.controller.api;

import cn.hutool.core.util.RandomUtil;
import com.wind.common.common.lang.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 *
 * @author kfg
 * @date 2023/6/8 12:58
 */
@RestController
@RequestMapping("/apis")
public class APIController {

    /**
     * @param address
     * @return
     */
    @RequestMapping("/getWeather")
    public Result getWeather(String address) {
        return Result.success(address + "【天气：晴】");
    }

    /**
     * 获取随机位数字符串
     *
     * @param num
     * @return
     */
    @RequestMapping("/getRandomString")
    public Result getWeather(Integer num) {
        return Result.success(RandomUtil.randomString(num));
    }
}
