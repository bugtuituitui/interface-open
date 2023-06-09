package com.wind.common.common.aspect;

import com.wind.common.common.annotation.Auth;
import com.wind.common.common.utils.AspectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 接口权限切面
 *
 * @author kfg
 * @date 2023/6/5 0:31
 */
@Aspect
@Component
public class AuthAspect {

    @Pointcut("@annotation(com.wind.common.common.annotation.Auth)")
    void pointCut() {
    }

    @Around("pointCut()")
    public Object deal(ProceedingJoinPoint joinPoint) throws Throwable {

        Auth auth = AspectUtils.getRealMethod(joinPoint).getAnnotation(Auth.class);

        switch (auth.type()) {
            case OnlyRole:
                String role = auth.onlyRole();
//                if (!role.equals(RequestHolder.get().getRole())) {
//                    return Result.fail("MustRole 无权限");
//                }
            case HasRole:
                String[] roles = auth.hasRole();
//                if (!Arrays.asList(roles).contains(RequestHolder.get().getRole())) {
//                    return Result.fail("HashRole 无权限");
//                }
            default:
                break;
        }

        return joinPoint.proceed(joinPoint.getArgs());
    }
}
