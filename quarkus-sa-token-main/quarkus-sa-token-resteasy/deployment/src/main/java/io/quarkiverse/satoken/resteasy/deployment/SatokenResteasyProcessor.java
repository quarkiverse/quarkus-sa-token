package io.quarkiverse.satoken.resteasy.deployment;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import cn.dev33.satoken.annotation.SaCheckBasic;
import cn.dev33.satoken.annotation.SaCheckDisable;
import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaCheckSafe;
import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.TokenSign;
import io.quarkiverse.satoken.core.config.SaInterceptConfigForQuarkus;
import io.quarkiverse.satoken.core.config.SaRouteConfigForQuarkus;
import io.quarkiverse.satoken.core.exception.SaTokenExceptionMapper;
import io.quarkiverse.satoken.core.filter.SaRouteFilter;
import io.quarkiverse.satoken.core.interceptor.SaCheckBasicInterceptor;
import io.quarkiverse.satoken.core.interceptor.SaCheckDisableInterceptor;
import io.quarkiverse.satoken.core.interceptor.SaCheckLoginInterceptor;
import io.quarkiverse.satoken.core.interceptor.SaCheckPermissionInterceptor;
import io.quarkiverse.satoken.core.interceptor.SaCheckRoleInterceptor;
import io.quarkiverse.satoken.core.interceptor.SaCheckSafeInterceptor;
import io.quarkiverse.satoken.runtime.SaTokenProducer;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.arc.deployment.InterceptorBindingRegistrarBuildItem;
import io.quarkus.arc.processor.InterceptorBindingRegistrar;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;
import io.quarkus.resteasy.reactive.spi.CustomContainerRequestFilterBuildItem;
import io.quarkus.resteasy.reactive.spi.ExceptionMapperBuildItem;
import jakarta.ws.rs.Priorities;

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
    void addFilter(final SaRouteConfigForQuarkus routeConfig,
            BuildProducer<CustomContainerRequestFilterBuildItem> resteasyProducer) {
        if (routeConfig.interceptor) {
            resteasyProducer.produce(new CustomContainerRequestFilterBuildItem(SaRouteFilter.class.getName()));
        }
    }

    @BuildStep
    void addAnnotationInterceptor(final SaInterceptConfigForQuarkus satokenConfig,
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
                            return List.of(saCheckLogin, saCheckRole, saCheckPermission, checkSafe, checkDisable, checkBasic);
                        }
                    }));
            additionalBeans.produce(AdditionalBeanBuildItem.unremovableOf(SaCheckLoginInterceptor.class));
            additionalBeans.produce(AdditionalBeanBuildItem.unremovableOf(SaCheckRoleInterceptor.class));
            additionalBeans.produce(AdditionalBeanBuildItem.unremovableOf(SaCheckPermissionInterceptor.class));
            additionalBeans.produce(AdditionalBeanBuildItem.unremovableOf(SaCheckSafeInterceptor.class));
            additionalBeans.produce(AdditionalBeanBuildItem.unremovableOf(SaCheckDisableInterceptor.class));
            additionalBeans.produce(AdditionalBeanBuildItem.unremovableOf(SaCheckBasicInterceptor.class));
        }
    }

    @BuildStep
    void registerHttpExceptionMapper(final SaInterceptConfigForQuarkus satokenConfig,
            BuildProducer<ExceptionMapperBuildItem> providers) {
        if (satokenConfig.exceptionMapperEnabled) {
            providers.produce(new ExceptionMapperBuildItem(
                    SaTokenExceptionMapper.class.getName(),
                    SaTokenException.class.getName(),
                    Priorities.AUTHENTICATION,
                    false));
        }
    }

}
