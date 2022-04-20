package io.quarkiverse.satoken.core.config.adapter;

import cn.dev33.satoken.config.SaCookieConfig;
import io.quarkiverse.satoken.core.config.SaCookieConfigForQuarkus;

import java.util.Objects;

/**
 * SaCookieConfigAdapter
 *
 * @author nayan
 * @date 2022/4/6 6:47 PM
 */
public class SaCookieConfigAdapter extends SaCookieConfig {

    private final SaCookieConfigForQuarkus config;

    public SaCookieConfigAdapter(SaCookieConfigForQuarkus config) {
        this.config = config;
    }

    @Override
    public String getDomain() {
        return Objects.nonNull(config) ? config.domain.orElse(null) : super.getDomain();
    }

    @Override
    public String getPath() {
        return Objects.nonNull(config) ? config.path.orElse(null) : super.getPath();
    }

    @Override
    public Boolean getSecure() {
        return Objects.nonNull(config) ? config.secure : super.getSecure();
    }

    @Override
    public Boolean getHttpOnly() {
        return Objects.nonNull(config) ? config.httpOnly : super.getHttpOnly();
    }

    @Override
    public String getSameSite() {
        return Objects.nonNull(config) ? config.sameSite.orElse(null) : super.getSameSite();
    }

}
