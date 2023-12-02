package cn.dev33.satoken.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.enterprise.util.Nonbinding;
import jakarta.interceptor.InterceptorBinding;

/**
 * 登录认证：只有登录之后才能进入该方法
 * <p>
 * 可标注在函数、类上（效果等同于标注在此类的所有方法上）
 *
 * @author kong
 *
 */
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface SaCheckLogin {

    /**
     * 多账号体系下所属的账号体系标识
     *
     * @return see note
     */
    @Nonbinding
    String type() default "";

}
