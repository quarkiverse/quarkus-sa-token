package io.quarkiverse.satoken.dao.redis.jackson.deployment;

import io.quarkiverse.satoken.dao.redis.jackson.SaTokenDaoRedisJackson;
import io.quarkiverse.satoken.dao.redis.jackson.SaTokenJacksonModuleCustomizer;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;

class SatokenDaoRedisJacksonProcessor {

    private static final String FEATURE = "satoken-dao-redis-jackson";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    void additionalBeans(BuildProducer<AdditionalBeanBuildItem> additionalBeans) {
        additionalBeans.produce(AdditionalBeanBuildItem.unremovableOf(SaTokenJacksonModuleCustomizer.class));
        additionalBeans.produce(AdditionalBeanBuildItem.unremovableOf(SaTokenDaoRedisJackson.class));
    }
}
