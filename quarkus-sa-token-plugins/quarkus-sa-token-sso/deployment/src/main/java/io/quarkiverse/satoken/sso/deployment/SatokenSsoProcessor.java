package io.quarkiverse.satoken.sso.deployment;

import io.quarkiverse.satoken.sso.runtime.SaSsoProducer;
import io.quarkiverse.satoken.sso.runtime.SaSsoRecorder;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;

class SatokenSsoProcessor {

    private static final String FEATURE = "satoken-sso";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    void additionalBeans(BuildProducer<AdditionalBeanBuildItem> additionalBeans) {

        additionalBeans.produce(AdditionalBeanBuildItem.unremovableOf(SaSsoProducer.class));
    }

    @BuildStep
    @Record(ExecutionTime.RUNTIME_INIT)
    public SaTokenSsoBuildItem configure(SaSsoRecorder recorder) {
        recorder.injectAllBean();
        return new SaTokenSsoBuildItem();
    }
}
