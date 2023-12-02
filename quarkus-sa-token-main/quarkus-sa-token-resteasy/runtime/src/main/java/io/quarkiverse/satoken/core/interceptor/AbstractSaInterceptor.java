package io.quarkiverse.satoken.core.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.exception.BackResultException;
import cn.dev33.satoken.exception.StopMatchException;
import cn.dev33.satoken.strategy.SaStrategy;
import jakarta.interceptor.InvocationContext;

/**
 * Sa-Token 综合拦截器，提供注解鉴权和路由拦截鉴权能力
 *
 * @author nayan
 * @date 2022/4/14 4:17 PM
 */

public class AbstractSaInterceptor<T extends Annotation> {

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
