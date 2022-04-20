package io.quarkiverse.satoken.core.config.adapter;

import cn.dev33.satoken.config.SaSsoConfig;
import io.quarkiverse.satoken.core.config.SaSsoConfigForQuarkus;

import java.util.Objects;

/**
 * SaSsoConfigAdapter
 *
 * @author nayan
 * @date 2022/4/6 6:27 PM
 */
public class SaSsoConfigAdapter extends SaSsoConfig {
    private final SaSsoConfigForQuarkus config;

    public SaSsoConfigAdapter(SaSsoConfigForQuarkus config) {
        this.config = config;
    }

    @Override
    public long getTicketTimeout() {
        return Objects.nonNull(config) ? config.ticketTimeout : super.getTicketTimeout();
    }

    @Override
    public String getAllowUrl() {
        return Objects.nonNull(config) ? config.allowUrl : super.getAllowUrl();
    }

    @Override
    public Boolean getIsSlo() {
        return Objects.nonNull(config) ? config.isSlo : super.getIsSlo();
    }

    @Override
    public Boolean getIsHttp() {
        return Objects.nonNull(config) ? config.isHttp : super.getIsHttp();
    }

    @Override
    public String getSecretkey() {
        return Objects.nonNull(config) ? config.secretkey.orElse(null) : super.getSecretkey();
    }

    @Override
    public String getAuthUrl() {
        return Objects.nonNull(config) ? config.authUrl.orElse(null) : super.getAuthUrl();
    }

    @Override
    public String getCheckTicketUrl() {
        return Objects.nonNull(config) ? config.checkTicketUrl.orElse(null) : super.getCheckTicketUrl();
    }

    @Override
    public String getUserinfoUrl() {
        return Objects.nonNull(config) ? config.userinfoUrl.orElse(null) : super.getUserinfoUrl();
    }

    @Override
    public String getSloUrl() {
        return Objects.nonNull(config) ? config.sloUrl.orElse(null) : super.getSloUrl();
    }

    @Override
    public String getSsoLogoutCall() {
        return Objects.nonNull(config) ? config.ssoLogoutCall.orElse(null) : super.getSsoLogoutCall();
    }
}
