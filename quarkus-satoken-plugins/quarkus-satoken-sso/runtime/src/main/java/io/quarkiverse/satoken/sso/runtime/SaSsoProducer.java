package io.quarkiverse.satoken.sso.runtime;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

import cn.dev33.satoken.config.SaSsoConfig;
import io.quarkiverse.satoken.sso.config.SaSsoConfigForQuarkus;
import io.quarkiverse.satoken.sso.config.adapter.SaSsoConfigAdapter;
import io.quarkus.arc.DefaultBean;
import io.quarkus.arc.Unremovable;
import io.quarkus.runtime.Startup;

/**
 * SaSsoProducer
 *
 * @author nayan
 * @date 2022/4/7 4:03 PM
 */
@ApplicationScoped
public class SaSsoProducer {

    @Produces
    @Startup
    @Unremovable
    @DefaultBean
    @Singleton
    public SaSsoConfig config(SaSsoConfigForQuarkus config) {
        return new SaSsoConfigAdapter(config);
    }

}
