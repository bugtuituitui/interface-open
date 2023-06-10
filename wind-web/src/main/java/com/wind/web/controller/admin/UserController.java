package com.wind.web.controller.admin;


import com.wind.common.common.lang.Result;
import com.wind.common.web.entity.User;
import com.wind.common.web.entity.query.UserQuery;
import com.wind.common.web.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户接口
 * </p>
 *
 * @author lemon
 * @since 2023-06-07
 */
@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @PostMapping("/login")
    Result login(@Validated User user) {
        return Result.success(userService.login(user));
    }


    /**
     * 注册
     *
     * @param user
     * @return
     */
    @PostMapping("/reg")
    Result reg(@Validated User user) {
        userService.reg(user);
        return Result.success();
    }


    /**
     * 退出登录
     *
     * @return
     */
    @PostMapping("/logout")
    Result logout() {
        userService.logout();
        return Result.success();
    }

    /**
     * 查询信息
     *
     * @return
     */
    @PostMapping("/getInfo")
    Result getInfo() {
        return Result.success(userService.getInfo());
    }


    /**
     * 条件查询
     *
     * @param query
     * @return
     */
    @PostMapping("/list")
    Result listUser(UserQuery query) {
        return Result.success(userService.listEntity(query));
    }


    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping("/insert")
    Result insertUser(User user) {
        return Result.success(userService.insertEntity(user));
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @PostMapping("/update")
    Result updateUser(User user) {
        return Result.success(userService.updateEntity(user));
    }
}

