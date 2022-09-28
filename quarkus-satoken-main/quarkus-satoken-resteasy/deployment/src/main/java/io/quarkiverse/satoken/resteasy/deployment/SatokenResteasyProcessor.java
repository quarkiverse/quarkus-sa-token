package io.quarkiverse.satoken.resteasy.deployment;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import cn.dev33.satoken.annotation.*;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.TokenSign;
import io.quarkiverse.satoken.core.config.SaTokenConfigForQuarkus;
import io.quarkiverse.satoken.core.filter.SaRouteFilter;
import io.quarkiverse.satoken.core.interceptor.SaInterceptor;
import io.quarkiverse.satoken.runtime.SaTokenProducer;
import io.quarkiverse.satoken.runtime.SaTokenRecorder;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.arc.deployment.InterceptorBindingRegistrarBuildItem;
import io.quarkus.arc.processor.InterceptorBindingRegistrar;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;
import io.quarkus.resteasy.reactive.spi.CustomContainerRequestFilterBuildItem;

class SatokenResteasyProcessor {

    private static final String FEATURE = "satoken-resteasy";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    void additionalBeans(BuildProducer<AdditionalBeanBuildItem> additionalBeans) {
        additionalBeans.produce(AdditionalBeanBuildItem.unremovableOf(SaTokenProducer.class));
    }

    @BuildStep
    void reflectiveClasses(BuildProducer<ReflectiveClassBuildItem> reflectiveClassBuildProducer) {
        reflectiveClassBuildProducer.produce(new ReflectiveClassBuildItem(true, true,
                SaSession.class,
                TokenSign.class,
                String.class,
                ConcurrentHashMap.class,
                Vector.class));
    }

    @BuildStep
    void addFilter(final SaTokenConfigForQuarkus satokenConfig,
            BuildProducer<CustomContainerRequestFilterBuildItem> resteasyProducer) {
        if (satokenConfig.route.interceptor) {
            resteasyProducer.produce(new CustomContainerRequestFilterBuildItem(SaRouteFilter.class.getName()));
        }
    }

    @BuildStep
    void addAnnotationInterceptor(final SaTokenConfigForQuarkus satokenConfig,
            BuildProducer<InterceptorBindingRegistrarBuildItem> interceptorRegister,
            BuildProducer<AdditionalBeanBuildItem> additionalBeans) {
        if (satokenConfig.annotationInterceptedEnabled) {
            interceptorRegister.produce(new InterceptorBindingRegistrarBuildItem(
                    new InterceptorBindingRegistrar() {
                        @Override
                        public List<InterceptorBinding> getAdditionalBindings() {
                            InterceptorBinding saCheckLogin = InterceptorBinding.of(SaCheckLogin.class);
                            InterceptorBinding saCheckRole = InterceptorBinding.of(SaCheckRole.class);
                            InterceptorBinding saCheckPermission = InterceptorBinding.of(SaCheckPermission.class);
                            InterceptorBinding checkSafe = InterceptorBinding.of(SaCheckSafe.class);
                            InterceptorBinding checkDisable = InterceptorBinding.of(SaCheckDisable.class);
                            InterceptorBinding checkBasic = InterceptorBinding.of(SaCheckBasic.class);
                            return List.of(saCheckLogin, saCheckRole, saCheckPermission, checkSafe,checkDisable, checkBasic);
                        }
                    }));
            additionalBeans.produce(AdditionalBeanBuildItem.unremovableOf(SaInterceptor.class));
        }
    }

    @BuildStep
    @Record(ExecutionTime.RUNTIME_INIT)
    public SaTokenResteasyBuildItem configure(SaTokenRecorder recorder) {
        recorder.injectAllBean();
        return new SaTokenResteasyBuildItem();
    }

}
