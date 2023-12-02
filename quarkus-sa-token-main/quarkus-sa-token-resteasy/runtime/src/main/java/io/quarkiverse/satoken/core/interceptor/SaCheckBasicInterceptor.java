package io.quarkiverse.satoken.core.interceptor;

import cn.dev33.satoken.annotation.SaCheckBasic;
import cn.dev33.satoken.util.SaTokenConsts;
import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

/**
 * Sa-Token @SaCheckBasic拦截器
 *
 * @author nayan
 * @date 2022/4/14 4:17 PM
 */
@SaCheckBasic
@Interceptor
@Priority(SaTokenConsts.ASSEMBLY_ORDER)
public class SaCheckBasicInterceptor extends AbstractSaInterceptor<SaCheckBasic> {

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {

        return interceptAnnotation(context);
    }
}
