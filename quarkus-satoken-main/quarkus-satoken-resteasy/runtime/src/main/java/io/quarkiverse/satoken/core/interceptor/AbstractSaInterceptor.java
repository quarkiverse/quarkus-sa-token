package io.quarkiverse.satoken.core.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;

import javax.interceptor.InvocationContext;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.exception.BackResultException;
import cn.dev33.satoken.exception.StopMatchException;
import cn.dev33.satoken.fun.SaParamFunction;
import cn.dev33.satoken.strategy.SaStrategy;

/**
 * Sa-Token 综合拦截器，提供注解鉴权和路由拦截鉴权能力
 *
 * @author nayan
 * @date 2022/4/14 4:17 PM
 */

public class AbstractSaInterceptor<T extends Annotation> {

    /**
     * 认证函数：每次请求执行
     * <p>
     * 参数：路由处理函数指针
     */
    public static SaParamFunction<Object> auth = handler -> {
    };

    /**
     * 写入[认证函数]: 每次请求执行
     *
     * @param auth /
     * @return 对象自身
     */
    public static void setAuth(SaParamFunction<Object> auth) {
        AbstractSaInterceptor.auth = auth;
    }

    public Object interceptAnnotation(InvocationContext context) throws Exception {

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
