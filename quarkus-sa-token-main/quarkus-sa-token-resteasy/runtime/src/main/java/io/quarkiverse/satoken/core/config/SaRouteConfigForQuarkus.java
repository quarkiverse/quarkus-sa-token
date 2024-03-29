package io.quarkiverse.satoken.core.config;

import java.util.List;
import java.util.Optional;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

/**
 * SaRouteConfigForQuarkus
 *
 * @author nayan
 * @date 2022/4/14 5:52 PM
 */
@ConfigRoot(prefix = "sa-token", name = "route", phase = ConfigPhase.BUILD_AND_RUN_TIME_FIXED)
public class SaRouteConfigForQuarkus {
    /**
     * enabled
     */
    @ConfigItem(defaultValue = "false")
    public boolean interceptor;

    /**
     * interceptor paths
     */
    @ConfigItem(defaultValue = "/**")
    public List<String> includePaths;

    public Optional<List<String>> excludePaths;

}
