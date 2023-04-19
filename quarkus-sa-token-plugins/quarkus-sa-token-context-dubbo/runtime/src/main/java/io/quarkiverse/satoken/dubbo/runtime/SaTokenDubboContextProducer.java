package io.quarkiverse.satoken.dubbo.runtime;

import cn.dev33.satoken.context.dubbo.SaTokenSecondContextCreatorForDubbo;
import cn.dev33.satoken.context.second.SaTokenSecondContextCreator;
import io.quarkus.arc.DefaultBean;
import io.quarkus.arc.Unremovable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

/**
 * SaTokenDubboContextProducer
 *
 * @author nayan
 * @date 2022/4/14 6:26 PM
 */
@ApplicationScoped
public class SaTokenDubboContextProducer {

    @Produces
    @DefaultBean
    @Unremovable
    @ApplicationScoped
    public SaTokenSecondContextCreator contextCreator() {
        return new SaTokenSecondContextCreatorForDubbo();
    }

}
