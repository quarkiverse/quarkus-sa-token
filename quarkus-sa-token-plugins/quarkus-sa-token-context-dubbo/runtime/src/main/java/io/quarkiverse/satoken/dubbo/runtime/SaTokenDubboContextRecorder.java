package io.quarkiverse.satoken.dubbo.runtime;

import io.quarkus.arc.Arc;
import io.quarkus.runtime.annotations.Recorder;

/**
 * SaTokenDubboContextRecorder
 *
 * @author nayan
 * @date 2022/4/14 6:28 PM
 */
@Recorder
public class SaTokenDubboContextRecorder {
    public void injectDubboContextCreator() {
        Arc.container().instance(SaTokenDubboContextProducer.class).get();
    }
}
