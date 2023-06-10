package com.wind.windinterfaces.interceptor;

import cn.hutool.json.JSONUtil;
import com.wind.common.web.entity.Api;
import com.wind.common.web.entity.Cert;
import com.wind.common.web.entity.Invoke;
import com.wind.common.web.mapper.ApiMapper;
import com.wind.common.web.mapper.CertMapper;
import com.wind.common.web.mapper.InvokeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 接口调用过滤器
 *
 * @author kfg
 * @date 2023/6/9 21:15
 */
@Slf4j
@Component
public class SubCountInterceptor implements HandlerInterceptor {

    private final String HOST = "http://localhost:9003";

    @Autowired
    private ApiMapper apiMapper;

    @Autowired
    private CertMapper certMapper;

    @Autowired
    private InvokeMapper invokeMapper;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        String path = request.getRequestURI();
        String params = JSONUtil.toJsonStr(request.getParameterMap());

        threadPoolTaskExecutor.execute(() -> {

            Cert cert = certMapper.getByKeyAndSecret(request.getHeader("key"), request.getHeader("secret"));
            // 调用成功，减去次数
            if (response.getStatus() == HttpStatus.OK.value()) {
                if (!cert.getUnlimited()) {
                    cert.setTotalCount(cert.getTotalCount() - 1);
                }
            }

            Api api = apiMapper.getByPath(path);

            // 保存调用记录
            Invoke invoke = new Invoke();
            invoke.setApiId(api.getId());
            invoke.setApiName(api.getApiName());
            invoke.setParams(params);
            invoke.setUserId(cert.getUserId());
            invoke.setUsername(cert.getUsername());
            invoke.setStatus(response.getStatus());
            invokeMapper.insert(invoke);
        });
    }


}
