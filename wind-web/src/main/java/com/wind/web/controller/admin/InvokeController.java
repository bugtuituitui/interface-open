package com.wind.web.controller.admin;


import com.wind.common.common.lang.Result;
import com.wind.common.web.entity.Invoke;
import com.wind.common.web.entity.query.InvokeQuery;
import com.wind.common.web.service.impl.InvokeServiceImpl;
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
@RequestMapping("/admin/invoke")
public class InvokeController {

    @Autowired
    private InvokeServiceImpl invokeService;

    /**
     * 添加
     *
     * @param invoke
     * @return
     */
    @PostMapping("/insert")
    public Result insert(Invoke invoke) {
        return Result.success(invokeService.insertEntity(invoke));
    }

    /**
     * 删除
     *
     * @param invoke
     * @return
     */
    @PostMapping("/delete")
    public Result delete(Invoke invoke) {
        return Result.success(invokeService.deleteEntity(invoke));
    }

    /**
     * 修改
     *
     * @param invoke
     * @return
     */
    @PostMapping("/update")
    public Result update(Invoke invoke) {
        return Result.success(invokeService.updateEntity(invoke));
    }

    /**
     * 条件查询
     *
     * @param query
     * @return
     */
    @PostMapping("/list")
    public Result list(InvokeQuery query) {
        return Result.success(invokeService.listEntity(query));
    }
}

