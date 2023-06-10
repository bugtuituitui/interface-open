package com.wind.common.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wind.common.common.exception.BusinessException;
import com.wind.common.common.lang.PageData;
import com.wind.common.common.template.ServiceImplTemplate;
import com.wind.common.common.template.ServiceTemplate;
import com.wind.common.common.template.query.BaseQuery;
import com.wind.common.common.user.RequestHolder;
import com.wind.common.common.utils.JwtUtils;
import com.wind.common.common.utils.RedisUtils;
import com.wind.common.web.entity.User;
import com.wind.common.web.entity.UserInfo;
import com.wind.common.web.entity.query.UserQuery;
import com.wind.common.web.mapper.UserMapper;
import com.wind.common.web.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lemon
 * @since 2023-06-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService, ServiceTemplate<User> {

    private ServiceImplTemplate<User> template = new ServiceImplTemplate<User>(this);

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 登录
     *
     * @param user
     */
    @Override
    public UserInfo login(User user) {

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getUsername, user.getUsername())
                .eq(User::getPassword, user.getPassword());

        User data = baseMapper.selectOne(queryWrapper);
        if (data == null) {
            throw new BusinessException(-1, "用户名或密码错误");
        }

        String token = null;
        try {
            token = JwtUtils.generate(data.getId().toString());
            // redis时间单位为秒
            redisUtils.set(data.getId().toString(), token, 60 * JwtUtils.MINUTES);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new BusinessException(-1, "token生成错误");
        }

        return new UserInfo(data.getId(), data.getRole(), token);
    }

    /**
     * 注册
     *
     * @param user
     */
    @Override
    public void reg(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getUsername, user.getUsername());
        if (baseMapper.selectOne(queryWrapper) != null) {
            throw new BusinessException(-1, "用户已存在");
        }
        save(user);
    }

    /**
     * 退出登录
     */
    @Override
    public void logout() {
        redisUtils.del(RequestHolder.get().getUid().toString());
        SecurityUtils.getSubject().logout();
    }

    /**
     * 查询信息
     *
     * @return
     */
    @Override
    public UserInfo getInfo() {
        User user = getById(RequestHolder.get().getUid());
        return new UserInfo(user.getId(), user.getRole(), RequestHolder.get().getToken());
    }

    /**
     * 添加用户
     *
     * @param entity
     * @return
     */
    @Override
    public boolean insertEntity(User entity) {
        return save(entity);
    }

    /**
     * 修改用户
     *
     * @param entity
     * @return
     */
    @Override
    public boolean updateEntity(User entity) {
        return updateById(entity);
    }

    /**
     * 删除用户
     *
     * @param entity
     * @return
     */
    @Override
    public boolean deleteEntity(User entity) {
        return false;
    }

    /**
     * 查询用户
     *
     * @param query
     * @return
     */
    @Override
    public PageData<User> listEntity(BaseQuery query) {
        UserQuery condition = (UserQuery) query;
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(StringUtils.isNotEmpty(condition.getUsername()), User::getUsername, condition.getUsername());

        return template.list(query, queryWrapper);
    }
}
