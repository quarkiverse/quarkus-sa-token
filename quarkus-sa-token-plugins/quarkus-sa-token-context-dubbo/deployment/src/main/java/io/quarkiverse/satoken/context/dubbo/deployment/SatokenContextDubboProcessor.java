package io.quarkiverse.satoken.context.dubbo.deployment;

import io.quarkiverse.satoken.dubbo.runtime.SaTokenDubboContextProducer;
import io.quarkiverse.satoken.dubbo.runtime.SaTokenDubboContextRecorder;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;

class SatokenContextDubboProcessor {

    private static final String FEATURE = "satoken-context-dubbo";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    void additionalBeans(BuildProducer<AdditionalBeanBuildItem> additionalBeans) {
        additionalBeans.produce(AdditionalBeanBuildItem.unremovableOf(SaTokenDubboContextProducer.class));
    }

    @BuildStep
    @Record(ExecutionTime.RUNTIME_INIT)
    public SaTokenDubboContextBuildItem configure(SaTokenDubboContextRecorder recorder) {
        recorder.injectDubboContextCreator();
        return new SaTokenDubboContextBuildItem();
    }
}
