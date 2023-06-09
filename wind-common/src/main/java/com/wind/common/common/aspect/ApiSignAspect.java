package com.wind.common.common.aspect;


import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wind.common.common.exception.BusinessException;
import com.wind.common.common.lang.Result;
import com.wind.common.common.utils.AssertUtils;
import com.wind.common.common.utils.RedisUtils;
import com.wind.common.common.utils.RequestUtils;
import com.wind.common.common.utils.SignatureGenerator;
import com.wind.common.web.entity.Cert;
import com.wind.common.web.entity.Invoke;
import com.wind.common.web.mapper.CertMapper;
import com.wind.common.web.service.impl.InvokeServiceImpl;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 接口签名切面
 *
 * @author kfg
 * @date 2023/4/4 17:11
 */
@Aspect
@Component
public class ApiSignAspect {

    // api key
    private static final String API_KEY = "apiKey";

    // api secret
    private static final String API_SECRET = "apiSecret";

    // 时间戳
    private static final String TIMESTAMP = "timestamp";

    // 流水号
    private static final String NONCE = "nonce";

    // 签名
    private static final String SIGNATURE = "signature";

    private static final String SIGN_ERROR_MSG = "签名错误";

    private static final String HTTP_EXPIRATION_MSG = "请求过期";

    private static final String HTTP_RESEND_MSG = "重发请求无效";

    @Autowired
    private CertMapper certMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private InvokeServiceImpl invokeService;

    /**
     * 定义切点
     */
    //@Pointcut("execution(* com.autumnwind.open.controller.api.*.*(..))")
    @Pointcut("@annotation(com.wind.common.common.annotation.Sign)")
    public void logPointCut() {
    }

    /**
     * 切面 配置通知
     *
     * @param joinPoint
     */
    @Around("logPointCut()")
    public Object checkSign(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = RequestUtils.getRequest();

        AssertUtils.isEmpty(request.getHeader(TIMESTAMP), SIGN_ERROR_MSG);
        AssertUtils.isEmpty(request.getHeader(NONCE), SIGN_ERROR_MSG);
        AssertUtils.isEmpty(request.getHeader(API_KEY), SIGN_ERROR_MSG);
        AssertUtils.isEmpty(request.getHeader(API_SECRET), SIGN_ERROR_MSG);
        AssertUtils.isEmpty(request.getHeader(SIGNATURE), SIGN_ERROR_MSG);


        String apiKey = request.getHeader(API_KEY);
        String apiSecret = request.getHeader(API_SECRET);

        // 校验key和secret
        Cert cert = certMapper.getByKeyAndSecret(apiKey, apiSecret);

        AssertUtils.isEmpty(cert, "签名不存在");

        Long timestamp = Long.valueOf(request.getHeader(TIMESTAMP));
        Integer nonce = Integer.valueOf(request.getHeader(NONCE));
        String sign = request.getHeader(SIGNATURE);

        // 检验请求是否过期
        long now = System.currentTimeMillis();
        if (timestamp + 60 * 1000 < now) {
            throw new BusinessException(-1, HTTP_EXPIRATION_MSG);
        }

        // 检验是否是重发请求
        if (redisUtils.get(apiKey + nonce) != null) {
            throw new BusinessException(-1, HTTP_RESEND_MSG);
        } else {
            redisUtils.set(apiKey + nonce, 0, 60);
        }

        // 获取参数
        Object[] args = joinPoint.getArgs();

        // proceedingJoinPoint.getArgs()返回的数组中携带有Request或者Response对象，导致序列化异常
        // 过滤request 或 reponse 对象
        //获取传参信息
        //过滤无法序列化
        Stream<?> stream = ArrayUtils.isEmpty(args) ? Stream.empty() : Arrays.stream(args);
        List<Object> argList = stream
                .filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)))
                .collect(Collectors.toList());

        Map<String, String> map = new HashMap<>();

        // RequestParam 参数不为对象 ["123456"]
        // RequestBody 参数不为对象 ["name=123456"]
        // RequestParam 参数为对象 ["{'name': '123456'}"] （正常情况）
        // 将参数加入map
        JSONArray jsonArray = JSONObject.parseArray(JSON.toJSONString(argList.toArray()));
        JSONObject jsonObject = new JSONObject();
        if (!jsonArray.isEmpty()) {

            // 是否是正常情况
            if (JSONUtil.isJsonObj(JSON.toJSONString(jsonArray.get(0)))) {
                jsonObject = jsonArray.getJSONObject(0);
            } else {
                Enumeration<String> params = request.getParameterNames();
                while (params.hasMoreElements()) {
                    String paramName = params.nextElement();
                    map.put(paramName, request.getParameter(paramName));
                }
            }

        }

        // 参数为对象
        Set<String> keys = jsonObject.keySet();
        for (String key : keys) {
            map.put(key, jsonObject.getString(key));
        }

        // 拼接参数
        String params = SignatureGenerator.getKeyAndValueStr(map);
        params = StringUtils.isEmpty(params) ? "" : "&" + params + "";

        // 校验sign
        try {
            if (sign == null || !sign.equals(SignatureGenerator.generateSignature(apiKey, apiSecret, params, timestamp, nonce))) {
                throw new BusinessException(-1, SIGN_ERROR_MSG);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(-1, SIGN_ERROR_MSG);
        }

        Result result = (Result) joinPoint.proceed(args);

        // 只有调用成功，才减去调用次数
        if (result.getCode() == 200) {
            cert.setTotalCount(cert.getTotalCount() - 1);
            certMapper.updateById(cert);
        }

        // 保存接口调用记录
        threadPoolTaskExecutor.execute(() -> {
            Invoke invoke = new Invoke();
            invoke.setDesc(JSON.toJSONString(result));
            invoke.setApi(request.getRequestURI());
            invoke.setUserId(cert.getUserId());
            invoke.setUsername(cert.getUsername());
            if (result.getCode() == 200) {
                invoke.setStatus(0);
            } else {
                invoke.setStatus(1);
            }
            invokeService.save(invoke);
        });
        return result;
    }
}
