package io.quarkiverse.satoken.oauth2.config.adapter;

import java.util.Objects;

import cn.dev33.satoken.oauth2.config.SaOAuth2Config;
import io.quarkiverse.satoken.oauth2.config.SaOAuth2ConfigForQuarkus;

/**
 * SaAuthConfigAdapter
 *
 * @author nayan
 * @date 2022/4/7 3:58 PM
 */
public class SaOAuth2ConfigAdapter extends SaOAuth2Config {
    private final SaOAuth2ConfigForQuarkus config;

    public SaOAuth2ConfigAdapter(SaOAuth2ConfigForQuarkus config) {
        this.config = config;
    }

    @Override
    public Boolean getIsCode() {
        return Objects.nonNull(config) ? config.isCode : super.getIsCode();
    }

    @Override
    public Boolean getIsImplicit() {
        return Objects.nonNull(config) ? config.isImplicit : super.getIsImplicit();
    }

    @Override
    public Boolean getIsPassword() {
        return Objects.nonNull(config) ? config.isPassword : super.getIsPassword();
    }

    @Override
    public Boolean getIsClient() {
        return Objects.nonNull(config) ? config.isClient : super.getIsClient();
    }

    @Override
    public Boolean getIsNewRefresh() {
        return Objects.nonNull(config) ? config.isNewRefresh : super.getIsNewRefresh();
    }

    @Override
    public long getCodeTimeout() {
        return Objects.nonNull(config) ? config.codeTimeout : super.getCodeTimeout();
    }

    @Override
    public long getAccessTokenTimeout() {
        return Objects.nonNull(config) ? config.accessTokenTimeout : super.getAccessTokenTimeout();
    }

    @Override
    public long getRefreshTokenTimeout() {
        return Objects.nonNull(config) ? config.refreshTokenTimeout : super.getRefreshTokenTimeout();
    }

    @Override
    public long getClientTokenTimeout() {
        return Objects.nonNull(config) ? config.clientTokenTimeout : super.getClientTokenTimeout();
    }

    @Override
    public long getPastClientTokenTimeout() {
        return Objects.nonNull(config) ? config.pastClientTokenTimeout : super.getPastClientTokenTimeout();
    }

    @Override
    public void setIsCode(Boolean isCode) {
        config.isCode = isCode;
    }

    @Override
    public void setIsImplicit(Boolean isImplicit) {
        config.isImplicit = isImplicit;
    }

    @Override
    public void setIsPassword(Boolean isPassword) {
        config.isPassword = isPassword;
    }

    @Override
    public void setIsClient(Boolean isClient) {
        config.isClient = isClient;
    }

    @Override
    public void setIsNewRefresh(Boolean isNewRefresh) {
        config.isNewRefresh = isNewRefresh;
    }

    @Override
    public SaOAuth2Config setCodeTimeout(long codeTimeout) {
        config.codeTimeout = codeTimeout;
        return this;
    }

    @Override
    public SaOAuth2Config setAccessTokenTimeout(long accessTokenTimeout) {
        config.accessTokenTimeout = accessTokenTimeout;
        return this;
    }

    @Override
    public SaOAuth2Config setRefreshTokenTimeout(long refreshTokenTimeout) {
        config.refreshTokenTimeout = refreshTokenTimeout;
        return this;
    }

    @Override
    public SaOAuth2Config setClientTokenTimeout(long clientTokenTimeout) {
        config.clientTokenTimeout = clientTokenTimeout;
        return this;
    }

    @Override
    public SaOAuth2Config setPastClientTokenTimeout(long pastClientTokenTimeout) {
        config.pastClientTokenTimeout = pastClientTokenTimeout;
        return this;
    }

}
