package com.wind.web.controller.admin;


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
@RequestMapping("/admin/api")
public class ApiController {

    @Autowired
    private ApiServiceImpl apiService;

    /**
     * 添加
     *
     * @param api
     * @return
     */
    @PostMapping("/insert")
    public Result insert(Api api) {
        return Result.success(apiService.insertEntity(api));
    }

    /**
     * 删除
     *
     * @param api
     * @return
     */
    @PostMapping("/delete")
    public Result delete(Api api) {
        return Result.success(apiService.deleteEntity(api));
    }

    /**
     * 修改
     *
     * @param api
     * @return
     */
    @PostMapping("/update")
    public Result update(Api api) {
        return Result.success(apiService.updateEntity(api));
    }

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

