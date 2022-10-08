package io.quarkiverse.satoken.core.interceptor;

import java.lang.reflect.Method;
import java.util.Objects;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import cn.dev33.satoken.annotation.SaCheckBasic;
import cn.dev33.satoken.annotation.SaCheckDisable;
import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaCheckSafe;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.exception.BackResultException;
import cn.dev33.satoken.exception.StopMatchException;
import cn.dev33.satoken.fun.SaParamFunction;
import cn.dev33.satoken.strategy.SaStrategy;
import cn.dev33.satoken.util.SaTokenConsts;
import io.quarkus.arc.Priority;

/**
 * Sa-Token 综合拦截器，提供注解鉴权和路由拦截鉴权能力
 *
 * @author nayan
 * @date 2022/4/14 4:17 PM
 */
@SaCheckLogin
@SaCheckRole
@SaCheckPermission
@SaCheckSafe
@SaCheckDisable
@SaCheckBasic
@Interceptor
@Priority(SaTokenConsts.ASSEMBLY_ORDER)
public class SaInterceptor {

    /**
     * 认证函数：每次请求执行
     * <p>
     * 参数：路由处理函数指针
     */
    public SaParamFunction<Object> auth = handler -> {
    };

    /**
     * 写入[认证函数]: 每次请求执行
     *
     * @param auth /
     * @return 对象自身
     */
    public SaInterceptor setAuth(SaParamFunction<Object> auth) {
        this.auth = auth;
        return this;
    }

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {

        try {
            // 获取此请求对应的 Method 处理函数
            Method method = context.getMethod();
            // 如果此 Method 或其所属 Class 标注了 @SaIgnore，则忽略掉鉴权
            if (SaStrategy.me.isAnnotationPresent.apply(method, SaIgnore.class)) {
                return context.proceed();
            }
            // 注解校验
            SaStrategy.me.checkMethodAnnotation.accept(method);
            if (Objects.nonNull(auth)) {
                // Auth 校验
                auth.run(context);
            }

        } catch (StopMatchException e) {
            // 停止匹配，进入Controller
        } catch (BackResultException e) {
            // 停止匹配，向前端输出结果
            throw e;
        }

        // 通过验证
        return context.proceed();
    }
}
