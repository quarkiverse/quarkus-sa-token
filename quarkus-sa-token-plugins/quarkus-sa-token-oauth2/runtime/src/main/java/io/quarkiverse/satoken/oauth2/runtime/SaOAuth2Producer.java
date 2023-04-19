package io.quarkiverse.satoken.oauth2.runtime;

import cn.dev33.satoken.oauth2.config.SaOAuth2Config;
import io.quarkiverse.satoken.oauth2.config.SaOAuth2ConfigForQuarkus;
import io.quarkiverse.satoken.oauth2.config.adapter.SaOAuth2ConfigAdapter;
import io.quarkus.arc.DefaultBean;
import io.quarkus.arc.Unremovable;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

/**
 * SaOAuth2Producer
 *
 * @author nayan
 * @date 2022/4/7 4:03 PM
 */
@ApplicationScoped
public class SaOAuth2Producer {

    @Produces
    @Startup
    @Unremovable
    @DefaultBean
    @Singleton
    public SaOAuth2Config config(SaOAuth2ConfigForQuarkus config) {
        return new SaOAuth2ConfigAdapter(config);
    }

}
