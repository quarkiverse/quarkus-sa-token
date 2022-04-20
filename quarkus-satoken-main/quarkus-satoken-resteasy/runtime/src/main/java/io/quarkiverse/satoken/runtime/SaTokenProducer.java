package io.quarkiverse.satoken.runtime;

import cn.dev33.satoken.config.SaTokenConfig;
import io.quarkiverse.satoken.core.config.SaTokenConfigForQuarkus;
import io.quarkiverse.satoken.core.config.adapter.SaTokenConfigAdapter;
import io.quarkiverse.satoken.core.context.SaTokenContextForQuarkus;
import io.quarkus.arc.DefaultBean;
import io.quarkus.arc.Unremovable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

/**
 * SaTokenProvider
 *
 * @author nayan
 * @date 2022/4/6 3:47 PM
 */
@ApplicationScoped
public class SaTokenProducer {

    @Produces
    @DefaultBean
    @Unremovable
    @Singleton
    public SaTokenConfig config(SaTokenConfigForQuarkus config) {
        return new SaTokenConfigAdapter(config);
    }

    @Produces
    @DefaultBean
    @Unremovable
    @ApplicationScoped
    public SaTokenContextForQuarkus saTokenContextForQuarkus() {
        return new SaTokenContextForQuarkus();
    }
}
