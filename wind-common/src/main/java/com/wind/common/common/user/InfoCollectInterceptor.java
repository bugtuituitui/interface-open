package com.wind.common.common.user;

import com.wind.common.common.constant.Header;
import com.wind.common.common.utils.JwtUtils;
import com.wind.common.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 请求拦截器 用于获取用户信息
 *
 * @author kfg
 * @date 2023/6/4 22:25
 */
@Order(10)
@Component
public class InfoCollectInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService userService;

    /**
     * 收集用户信息
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader(Header.TOKEN);

        Long UID = Optional.ofNullable(JwtUtils.getCredential(token)).map(Object::toString).map(Long::valueOf).orElse(null);
        String IP = request.getRemoteHost();
        RequestHolder.put(new RequestInfo(IP, UID, token));

        System.out.println("collector execute");
        return true;
    }

    /**
     * 清除用户信息
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestHolder.clear();
        System.out.println("info clear");
    }
}
