package io.quarkiverse.satoken.core.config.adapter;

import java.util.Optional;

import cn.dev33.satoken.config.SaCookieConfig;
import cn.dev33.satoken.config.SaTokenConfig;
import io.quarkiverse.satoken.core.config.SaCookieConfigForQuarkus;
import io.quarkiverse.satoken.core.config.SaTokenConfigForQuarkus;

/**
 * SaTokenConfigAdapter
 *
 * @author nayan
 * @date 2022/4/6 6:00 PM
 */
public class SaTokenConfigAdapter extends SaTokenConfig {

    private final SaTokenConfigForQuarkus config;

    public SaTokenConfigAdapter(SaTokenConfigForQuarkus config, SaCookieConfigForQuarkus cookieConfig) {
        this.config = config;
        this.cookie = new SaCookieConfigAdapter(cookieConfig);
    }

    @Override
    public String getTokenName() {
        return this.config.tokenName;
    }

    @Override
    public long getTimeout() {
        return this.config.timeout;
    }

    @Override
    public long getActivityTimeout() {
        return this.config.activityTimeout;
    }

    @Override
    public Boolean getIsConcurrent() {
        return this.config.isConcurrent;
    }

    @Override
    public Boolean getIsShare() {
        return this.config.isShare;
    }

    @Override
    public Boolean getIsReadBody() {
        return this.config.isReadBody;
    }

    @Override
    public Boolean getIsReadHeader() {
        return this.config.isReadHeader;
    }

    @Override
    public Boolean getIsReadCookie() {
        return this.config.isReadCookie;
    }

    @Override
    public String getTokenStyle() {
        return this.config.tokenStyle;
    }

    @Override
    public int getDataRefreshPeriod() {
        return this.config.dataRefreshPeriod;
    }

    @Override
    public Boolean getTokenSessionCheckLogin() {
        return this.config.tokenSessionCheckLogin;
    }

    @Override
    public Boolean getAutoRenew() {
        return this.config.autoRenew;
    }

    @Override
    public String getTokenPrefix() {
        return this.config.tokenPrefix.orElse(super.getTokenPrefix());
    }

    @Override
    public Boolean getIsPrint() {
        return this.config.isPrint;
    }

    @Override
    public Boolean getIsLog() {
        return this.config.isLog;
    }

    @Override
    public String getJwtSecretKey() {
        return this.config.jwtSecretKey.orElse(super.getJwtSecretKey());
    }

    @Override
    public long getIdTokenTimeout() {
        return this.config.idTokenTimeout;
    }

    @Override
    public String getBasic() {
        return this.config.basic.orElse(super.getBasic());
    }

    @Override
    public String getCurrDomain() {
        return this.config.currDomain.orElse(super.getCurrDomain());
    }

    @Override
    public Boolean getCheckIdToken() {
        return this.config.checkIdToken;
    }

    @Override
    public SaTokenConfig setTokenName(String tokenName) {
        this.config.tokenName = tokenName;
        return this;
    }

    @Override
    public SaTokenConfig setTimeout(long timeout) {
        this.config.timeout = timeout;
        return this;
    }

    @Override
    public SaTokenConfig setActivityTimeout(long activityTimeout) {
        this.config.activityTimeout = activityTimeout;
        return this;
    }

    @Override
    public SaTokenConfig setIsConcurrent(Boolean isConcurrent) {
        this.config.isConcurrent = isConcurrent;
        return this;
    }

    @Override
    public SaTokenConfig setIsShare(Boolean isShare) {
        this.config.isShare = isShare;
        return this;
    }

    @Override
    public int getMaxLoginCount() {
        return this.config.maxLoginCount;
    }

    @Override
    public SaTokenConfig setMaxLoginCount(int maxLoginCount) {
        this.config.maxLoginCount = maxLoginCount;
        return this;
    }

    @Override
    public SaTokenConfig setIsReadBody(Boolean isReadBody) {
        this.config.isReadBody = isReadBody;
        return this;
    }

    @Override
    public SaTokenConfig setIsReadHeader(Boolean isReadHeader) {
        this.config.isReadHeader = isReadHeader;
        return this;
    }

    @Override
    public SaTokenConfig setIsReadCookie(Boolean isReadCookie) {
        this.config.isReadCookie = isReadCookie;
        return this;
    }

    @Override
    public SaTokenConfig setTokenStyle(String tokenStyle) {
        this.config.tokenStyle = tokenStyle;
        return this;
    }

    @Override
    public SaTokenConfig setDataRefreshPeriod(int dataRefreshPeriod) {
        this.config.dataRefreshPeriod = dataRefreshPeriod;
        return this;
    }

    @Override
    public SaTokenConfig setTokenSessionCheckLogin(Boolean tokenSessionCheckLogin) {
        this.config.tokenSessionCheckLogin = tokenSessionCheckLogin;
        return this;
    }

    @Override
    public SaTokenConfig setAutoRenew(Boolean autoRenew) {
        this.config.autoRenew = autoRenew;
        return this;
    }

    @Override
    public SaTokenConfig setTokenPrefix(String tokenPrefix) {
        this.config.tokenPrefix = Optional.ofNullable(tokenPrefix);
        return this;
    }

    @Override
    public SaTokenConfig setIsPrint(Boolean isPrint) {
        this.config.isPrint = isPrint;
        return this;
    }

    @Override
    public SaTokenConfig setIsLog(Boolean isLog) {
        this.config.isLog = isLog;
        return this;
    }

    @Override
    public SaTokenConfig setJwtSecretKey(String jwtSecretKey) {
        this.config.jwtSecretKey = Optional.ofNullable(jwtSecretKey);
        return this;
    }

    @Override
    public SaTokenConfig setIdTokenTimeout(long idTokenTimeout) {
        this.config.idTokenTimeout = idTokenTimeout;
        return this;
    }

    @Override
    public SaTokenConfig setBasic(String basic) {
        this.config.basic = Optional.ofNullable(basic);
        return this;
    }

    @Override
    public SaTokenConfig setCurrDomain(String currDomain) {
        this.config.currDomain = Optional.ofNullable(currDomain);
        return this;
    }

    @Override
    public SaTokenConfig setCheckIdToken(Boolean checkIdToken) {
        this.config.checkIdToken = checkIdToken;
        return this;
    }

    @Override
    public SaCookieConfig getCookie() {
        return this.cookie;
    }

    @Override
    public SaTokenConfig setCookie(SaCookieConfig cookie) {
        this.cookie = cookie;
        return this;
    }

}
