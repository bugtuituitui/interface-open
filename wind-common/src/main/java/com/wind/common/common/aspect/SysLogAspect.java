package com.wind.common.common.aspect;

import com.wind.common.common.annotation.SysLog;
import com.wind.common.common.utils.AspectUtils;
import com.wind.common.common.utils.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * 系统日志切面
 *
 * @author kfg
 * @date 2023/6/4 23:16
 */
@Slf4j
@Aspect
@Component
public class SysLogAspect {

    // 定义切入点
    @Pointcut("@annotation(com.wind.common.common.annotation.SysLog)")
    //@Pointcut("execution(* com.example.controller.*.*(..))")
    void pointCut() {
    }

    // 配置通知
    @Around("pointCut()")
    Object deal(ProceedingJoinPoint point) throws Throwable {

        String IP = RequestUtils.getIP();

        String UID = RequestUtils.getUID();

        SysLog sysLog = (SysLog) AspectUtils.getAnnotation(point, SysLog.class);

        log.info("IP: '{}', Method: '{}', UID: '{}'", IP, sysLog.value(), UID);

        return point.proceed(point.getArgs());
    }
}
