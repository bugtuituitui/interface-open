package com.wind.windgateway;

import com.wind.common.common.utils.AssertUtils;
import com.wind.common.common.utils.RedisUtils;
import com.wind.common.common.utils.SignatureGenerator;
import com.wind.common.web.entity.Cert;
import com.wind.common.web.mapper.CertMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 全局过滤器配置
 *
 * @author kfg
 * @date 2023/6/9 10:29
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter {

    @Autowired
    private CertMapper certMapper;

    @Autowired
    private RedisUtils redisUtils;


    /**
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        HttpHeaders headers = request.getHeaders();

        String key = headers.getFirst("key");
        String secret = headers.getFirst("secret");
        String timestampStr = headers.getFirst("timestamp");
        String nonceStr = headers.getFirst("nonce");
        String sign = headers.getFirst("signature");

        List<Object> headerList = new ArrayList<>();
        headerList.add(key);
        headerList.add(secret);
        headerList.add(timestampStr);
        headerList.add(nonceStr);
        headerList.add(sign);

        if (headerList.contains(null) || headerList.contains("")) {
            return handleNoAuth(exchange.getResponse());
        }

        // 校验key和secret
        Cert cert = certMapper.getByKeyAndSecret(key, secret);

        AssertUtils.isEmpty(cert, "apiKey不存在");

        Long timestamp = Long.valueOf(timestampStr);
        Integer nonce = Integer.valueOf(nonceStr);

        // 检验请求是否过期
        long now = System.currentTimeMillis();
        if (timestamp + 60 * 1000 < now) {
            return handleNoAuth(exchange.getResponse());
        }

        // 检验是否是重发请求
        if (redisUtils.get(key + nonce) != null) {
            return handleNoAuth(exchange.getResponse());
        } else {
            redisUtils.set(key + nonce, 0, 60);
        }

        MultiValueMap<String, String> valueMap = request.getQueryParams();

        HashMap<String, String> map = new HashMap<>();

        valueMap.forEach((k, v) -> {
            map.put(k, v.get(0));
        });

        // 拼接参数
        String params = SignatureGenerator.getKeyAndValueStr(map);
        params = StringUtils.isEmpty(params) ? "" : "&" + params + "";

        // 校验sign
        try {
            if (sign == null || !sign.equals(SignatureGenerator.generateSignature(key, secret, params, timestamp, nonce))) {
                return handleNoAuth(exchange.getResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return handleNoAuth(exchange.getResponse());
        }

        // 校验接口调用次数
        if (!cert.getUnlimited() && cert.getTotalCount() < 1) {
            return handleNoAuth(exchange.getResponse());
        }

        return chain.filter(exchange);
    }

    /**
     * 拒绝访问
     *
     * @param response
     * @return
     */
    public Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }
}
