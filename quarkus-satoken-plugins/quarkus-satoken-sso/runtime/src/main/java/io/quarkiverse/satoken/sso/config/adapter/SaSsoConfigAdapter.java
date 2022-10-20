package io.quarkiverse.satoken.sso.config.adapter;

import cn.dev33.satoken.config.SaSsoConfig;
import cn.dev33.satoken.util.SaFoxUtil;
import io.quarkiverse.satoken.sso.config.SaSsoConfigForQuarkus;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

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
        return config.ticketTimeout;
    }

    @Override
    public String getAllowUrl() {
        return config.allowUrl;
    }

    @Override
    public Boolean getIsSlo() {
        return config.isSlo;
    }

    @Override
    public Boolean getIsHttp() {
        return config.isHttp;
    }

    @Override
    public String getSecretkey() {
        return config.secretkey.orElse(super.secretkey);
    }

    @Override
    public String getAuthUrl() {
        return config.authUrl.orElse(super.authUrl);
    }

    @Override
    public String getCheckTicketUrl() {
        return config.checkTicketUrl.orElse(super.checkTicketUrl);
    }

    @Override
    public String getUserinfoUrl() {
        return config.userinfoUrl.orElse(super.userinfoUrl);
    }

    @Override
    public String getSloUrl() {
        return config.sloUrl.orElse(super.sloUrl);
    }

    @Override
    public String getSsoLogoutCall() {
        return config.ssoLogoutCall.orElse(super.ssoLogoutCall);
    }

    @Override
    public String getServerUrl() {
        return config.serverUrl.orElse(super.serverUrl);
    }

    @Override
    public long getTimestampDisparity() {
        return config.timestampDisparity;
    }

    @Override
    public SaSsoConfig setTicketTimeout(long ticketTimeout) {
        config.ticketTimeout = ticketTimeout;
        return this;
    }

    @Override
    public SaSsoConfig setAllowUrl(String allowUrl) {
        config.allowUrl = allowUrl;
        return this;
    }

    @Override
    public SaSsoConfig setIsSlo(Boolean isSlo) {
        config.isSlo = isSlo;
        return this;
    }

    @Override
    public SaSsoConfig setIsHttp(Boolean isHttp) {
        config.isHttp = isHttp;
        return this;
    }

    @Override
    public SaSsoConfig setSecretkey(String secretkey) {
        config.secretkey = Optional.ofNullable(secretkey);
        return this;
    }

    @Override
    public SaSsoConfig setAuthUrl(String authUrl) {
        config.authUrl = Optional.ofNullable(authUrl);
        return this;
    }

    @Override
    public SaSsoConfig setCheckTicketUrl(String checkTicketUrl) {
        config.checkTicketUrl = Optional.ofNullable(checkTicketUrl);
        return this;
    }

    @Override
    public SaSsoConfig setUserinfoUrl(String userinfoUrl) {
        config.userinfoUrl = Optional.ofNullable(userinfoUrl);
        return this;
    }

    @Override
    public SaSsoConfig setSloUrl(String sloUrl) {
        config.sloUrl = Optional.ofNullable(sloUrl);
        return this;
    }

    @Override
    public SaSsoConfig setSsoLogoutCall(String ssoLogoutCall) {
        config.ssoLogoutCall = Optional.ofNullable(ssoLogoutCall);
        return this;
    }

    @Override
    public SaSsoConfig setServerUrl(String serverUrl) {
        config.serverUrl = Optional.ofNullable(serverUrl);
        return this;
    }

    @Override
    public SaSsoConfig setTimestampDisparity(long timestampDisparity) {
        config.timestampDisparity = timestampDisparity;
        return this;
    }

    @Override
    public SaSsoConfig setAllow(String... url) {
        config.allowUrl = SaFoxUtil.arrayJoin(url);
        return this;
    }

    @Override
    public SaSsoConfig setNotLoginView(Supplier<Object> notLoginView) {
        this.notLoginView = notLoginView;
        return this;
    }

    @Override
    public SaSsoConfig setDoLoginHandle(BiFunction<String, String, Object> doLoginHandle) {
        this.doLoginHandle = doLoginHandle;
        return this;
    }

    @Override
    public SaSsoConfig setTicketResultHandle(BiFunction<Object, String, Object> ticketResultHandle) {
        this.ticketResultHandle = ticketResultHandle;
        return this;
    }

    @Override
    public SaSsoConfig setSendHttp(Function<String, String> sendHttp) {
        this.sendHttp = sendHttp;
        return this;
    }
}
