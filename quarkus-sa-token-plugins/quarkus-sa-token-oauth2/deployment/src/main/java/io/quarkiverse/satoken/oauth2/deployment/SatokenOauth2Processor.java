package io.quarkiverse.satoken.oauth2.deployment;

import cn.dev33.satoken.oauth2.model.*;
import io.quarkiverse.satoken.oauth2.runtime.SaOAuth2Producer;
import io.quarkiverse.satoken.oauth2.runtime.SaOAuth2Recorder;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;

class SatokenOauth2Processor {

    private static final String FEATURE = "satoken-oauth2";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    void additionalBeans(BuildProducer<AdditionalBeanBuildItem> additionalBeans) {

        additionalBeans.produce(AdditionalBeanBuildItem.unremovableOf(SaOAuth2Producer.class));
    }

    @BuildStep
    @Record(ExecutionTime.RUNTIME_INIT)
    public SaTokenOAuth2BuildItem configure(SaOAuth2Recorder recorder) {
        recorder.injectAllBean();
        return new SaTokenOAuth2BuildItem();
    }

    @BuildStep
    void reflectiveClasses(BuildProducer<ReflectiveClassBuildItem> reflectiveClassBuildProducer) {
        reflectiveClassBuildProducer.produce(new ReflectiveClassBuildItem(true, true,
                AccessTokenModel.class,
                ClientTokenModel.class,
                CodeModel.class,
                RefreshTokenModel.class,
                RequestAuthModel.class,
                SaClientModel.class));
    }
}
