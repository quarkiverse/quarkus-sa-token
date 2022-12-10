package io.quarkiverse.satoken.runtime;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dev33.satoken.config.SaTokenConfig;
import io.quarkiverse.satoken.core.config.SaTokenConfigForQuarkus;
import io.quarkiverse.satoken.core.config.adapter.SaTokenConfigAdapter;
import io.quarkiverse.satoken.core.context.SaTokenContextForQuarkus;
import io.quarkiverse.satoken.core.json.SaJsonTemplateForJackson;
import io.quarkus.arc.DefaultBean;
import io.quarkus.arc.Unremovable;

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

    @Produces
    @DefaultBean
    @Unremovable
    @ApplicationScoped
    public SaJsonTemplateForJackson saJsonTemplateForJackson(ObjectMapper objectMapper) {
        return new SaJsonTemplateForJackson(objectMapper);
    }
}
