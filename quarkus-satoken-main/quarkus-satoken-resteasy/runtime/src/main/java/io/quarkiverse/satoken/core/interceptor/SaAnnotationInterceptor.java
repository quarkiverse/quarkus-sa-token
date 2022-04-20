package io.quarkiverse.satoken.core.interceptor;

import cn.dev33.satoken.annotation.*;
import cn.dev33.satoken.strategy.SaStrategy;
import cn.dev33.satoken.util.SaTokenConsts;
import io.quarkus.arc.Priority;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;

/**
 * 注解式鉴权 - 拦截器
 *
 * @author nayan
 * @date 2022/4/14 4:17 PM
 */
@SaCheckLogin
@SaCheckRole
@SaCheckPermission
@SaCheckSafe
@SaCheckBasic
@Interceptor
@Priority(SaTokenConsts.ASSEMBLY_ORDER)
public class SaAnnotationInterceptor {

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {

        // 获取处理method

        Method method = context.getMethod();

        // 进行验证
        SaStrategy.me.checkMethodAnnotation.accept(method);

        // 通过验证
        return context.proceed();
    }
}
