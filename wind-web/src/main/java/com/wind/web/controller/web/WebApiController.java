package com.wind.web.controller.web;


import com.wind.common.common.lang.Result;
import com.wind.common.web.entity.Api;
import com.wind.common.web.entity.query.ApiQuery;
import com.wind.common.web.service.impl.ApiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author lemon
 * @since 2023-06-07
 */
@RestController
@RequestMapping("/web/api")
public class WebApiController {

    @Autowired
    private ApiServiceImpl apiService;


    /**
     * 条件查询
     *
     * @param query
     * @return
     */
    @PostMapping("/list")
    public Result list(ApiQuery query) {
        return Result.success(apiService.listEntity(query));
    }
}

