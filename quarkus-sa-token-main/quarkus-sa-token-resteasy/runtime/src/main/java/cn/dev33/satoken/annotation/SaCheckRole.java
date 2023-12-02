package cn.dev33.satoken.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.enterprise.util.Nonbinding;
import jakarta.interceptor.InterceptorBinding;

/**
 * 角色认证：必须具有指定角色标识才能进入该方法
 * <p>
 * 可标注在函数、类上（效果等同于标注在此类的所有方法上）
 *
 * @author kong
 *
 */
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface SaCheckRole {

    /**
     * 需要校验的角色标识
     *
     * @return 需要校验的角色标识
     */
    @Nonbinding
    String[] value() default {};

    /**
     * 验证模式：AND | OR，默认AND
     *
     * @return 验证模式
     */
    @Nonbinding
    SaMode mode() default SaMode.AND;

    /**
     * 账号类型
     * <p>
     * 建议使用常量，避免因错误拼写带来的bug
     *
     * @return see note
     */
    @Nonbinding
    String type() default "";

}
