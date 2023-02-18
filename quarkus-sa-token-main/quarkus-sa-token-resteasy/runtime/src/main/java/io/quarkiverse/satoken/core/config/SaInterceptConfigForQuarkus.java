package io.quarkiverse.satoken.core.config;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

/**
 * SaInterceptConfigForQuarkus
 *
 * @author nayan
 * @date 2023/2/18 17:46
 */

@ConfigRoot(prefix = "sa-token", name = "intercept", phase = ConfigPhase.BUILD_AND_RUN_TIME_FIXED)
public class SaInterceptConfigForQuarkus {
    /**
     * 开启注解拦截
     */
    @ConfigItem(defaultValue = "false")
    public Boolean annotationInterceptedEnabled;

    /**
     * 开启异常拦截
     */
    @ConfigItem(defaultValue = "false")
    public Boolean exceptionMapperEnabled;

}
