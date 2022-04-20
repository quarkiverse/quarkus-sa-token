package io.quarkiverse.satoken.core.config;

import io.quarkus.runtime.annotations.ConfigGroup;
import io.quarkus.runtime.annotations.ConfigItem;

import java.util.List;
import java.util.Optional;

/**
 * SaRouteConfigForQuarkus
 *
 * @author nayan
 * @date 2022/4/14 5:52 PM
 */
@ConfigGroup
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
