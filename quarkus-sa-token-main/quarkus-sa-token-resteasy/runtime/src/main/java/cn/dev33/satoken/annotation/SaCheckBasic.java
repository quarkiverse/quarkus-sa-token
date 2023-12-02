package cn.dev33.satoken.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.dev33.satoken.basic.SaBasicTemplate;
import jakarta.enterprise.util.Nonbinding;
import jakarta.interceptor.InterceptorBinding;

/**
 * Http Basic 认证：只有通过 Basic 认证后才能进入该方法
 * <p>
 * 可标注在函数、类上（效果等同于标注在此类的所有方法上）
 *
 * @author kong
 *
 */

@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface SaCheckBasic {

    /**
     * 领域
     *
     * @return see note
     */
    @Nonbinding
    String realm() default SaBasicTemplate.DEFAULT_REALM;

    /**
     * 需要校验的账号密码，格式形如 sa:123456
     *
     * @return see note
     */
    @Nonbinding
    String account() default "";

}
