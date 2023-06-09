package com.wind.common.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wind.common.web.entity.User;
import com.wind.common.web.entity.UserInfo;

/**
 * <p>
 * 用户服务类
 * </p>
 *
 * @author lemon
 * @since 2023-06-07
 */
public interface IUserService extends IService<User> {

    /**
     * 登录
     *
     * @param user
     */
    UserInfo login(User user);

    /**
     * 注册
     *
     * @param user
     */
    void reg(User user);

    /**
     * 退出
     */
    void logout();

    /**
     * 查询信息
     *
     * @return
     */
    UserInfo getInfo();

}
