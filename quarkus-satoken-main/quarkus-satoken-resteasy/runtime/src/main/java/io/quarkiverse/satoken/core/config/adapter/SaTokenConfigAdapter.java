package io.quarkiverse.satoken.core.config.adapter;

import cn.dev33.satoken.config.SaCookieConfig;
import cn.dev33.satoken.config.SaTokenConfig;
import io.quarkiverse.satoken.core.config.SaTokenConfigForQuarkus;

import java.util.Optional;

/**
 * SaTokenConfigAdapter
 *
 * @author nayan
 * @date 2022/4/6 6:00 PM
 */
public class SaTokenConfigAdapter extends SaTokenConfig {

    private final SaTokenConfigForQuarkus config;

    public SaTokenConfigAdapter(SaTokenConfigForQuarkus config) {
        this.config = config;
        this.cookie = new SaCookieConfigAdapter(config.cookie);
    }

    @Override
    public String getTokenName() {
        return config.tokenName;
    }

    @Override
    public long getTimeout() {
        return config.timeout;
    }

    @Override
    public long getActivityTimeout() {
        return config.activityTimeout;
    }

    @Override
    public Boolean getIsConcurrent() {
        return config.isConcurrent;
    }

    @Override
    public Boolean getIsShare() {
        return config.isShare;
    }

    @Override
    public Boolean getIsReadBody() {
        return config.isReadBody;
    }

    @Override
    public Boolean getIsReadHeader() {
        return config.isReadHeader;
    }

    @Override
    public Boolean getIsReadCookie() {
        return config.isReadCookie;
    }

    @Override
    public String getTokenStyle() {
        return config.tokenStyle;
    }

    @Override
    public int getDataRefreshPeriod() {
        return config.dataRefreshPeriod;
    }

    @Override
    public Boolean getTokenSessionCheckLogin() {
        return config.tokenSessionCheckLogin;
    }

    @Override
    public Boolean getAutoRenew() {
        return config.autoRenew;
    }

    @Override
    public String getTokenPrefix() {
        return config.tokenPrefix.orElse(super.getTokenPrefix());
    }

    @Override
    public Boolean getIsPrint() {
        return config.isPrint;
    }

    @Override
    public Boolean getIsLog() {
        return config.isLog;
    }

    @Override
    public String getJwtSecretKey() {
        return config.jwtSecretKey.orElse(super.getJwtSecretKey());
    }

    @Override
    public long getIdTokenTimeout() {
        return config.idTokenTimeout;
    }

    @Override
    public String getBasic() {
        return config.basic.orElse(super.getBasic());
    }

    @Override
    public String getCurrDomain() {
        return config.currDomain.orElse(super.getCurrDomain());
    }

    @Override
    public Boolean getCheckIdToken() {
        return config.checkIdToken;
    }


    @Override
    public SaTokenConfig setTokenName(String tokenName) {
        config.tokenName = tokenName;
        return this;
    }

    @Override
    public SaTokenConfig setTimeout(long timeout) {
        config.timeout = timeout;
        return this;
    }

    @Override
    public SaTokenConfig setActivityTimeout(long activityTimeout) {
        config.activityTimeout=activityTimeout;
        return this;
    }

    @Override
    public SaTokenConfig setIsConcurrent(Boolean isConcurrent) {
        config.isConcurrent=isConcurrent;
        return this;
    }

    @Override
    public SaTokenConfig setIsShare(Boolean isShare) {
        config.isShare = isShare;
        return this;
    }

    @Override
    public int getMaxLoginCount() {
        return config.maxLoginCount;
    }

    @Override
    public SaTokenConfig setMaxLoginCount(int maxLoginCount) {
        config.maxLoginCount = maxLoginCount;
        return this;
    }

    @Override
    public SaTokenConfig setIsReadBody(Boolean isReadBody) {
        config.isReadBody = isReadBody;
        return this;
    }

    @Override
    public SaTokenConfig setIsReadHeader(Boolean isReadHeader) {
        config.isReadHeader = isReadHeader;
        return this;
    }

    @Override
    public SaTokenConfig setIsReadCookie(Boolean isReadCookie) {
        config.isReadCookie = isReadCookie;
        return this;
    }

    @Override
    public SaTokenConfig setTokenStyle(String tokenStyle) {
        config.tokenStyle = tokenStyle;
        return this;
    }

    @Override
    public SaTokenConfig setDataRefreshPeriod(int dataRefreshPeriod) {
        config.dataRefreshPeriod = dataRefreshPeriod;
        return this;
    }

    @Override
    public SaTokenConfig setTokenSessionCheckLogin(Boolean tokenSessionCheckLogin) {
        config.tokenSessionCheckLogin = tokenSessionCheckLogin;
        return this;
    }

    @Override
    public SaTokenConfig setAutoRenew(Boolean autoRenew) {
        config.autoRenew = autoRenew;
        return this;
    }

    @Override
    public SaTokenConfig setTokenPrefix(String tokenPrefix) {
        config.tokenPrefix = Optional.ofNullable(tokenPrefix);
        return this;
    }

    @Override
    public SaTokenConfig setIsPrint(Boolean isPrint) {
        config.isPrint = isPrint;
        return this;
    }

    @Override
    public SaTokenConfig setIsLog(Boolean isLog) {
        return super.setIsLog(isLog);
    }

    @Override
    public SaTokenConfig setJwtSecretKey(String jwtSecretKey) {
        return super.setJwtSecretKey(jwtSecretKey);
    }

    @Override
    public SaTokenConfig setIdTokenTimeout(long idTokenTimeout) {
        return super.setIdTokenTimeout(idTokenTimeout);
    }

    @Override
    public SaTokenConfig setBasic(String basic) {
        return super.setBasic(basic);
    }

    @Override
    public SaTokenConfig setCurrDomain(String currDomain) {
        return super.setCurrDomain(currDomain);
    }

    @Override
    public SaTokenConfig setCheckIdToken(Boolean checkIdToken) {
        return super.setCheckIdToken(checkIdToken);
    }

    @Override
    public SaCookieConfig getCookie() {
        return super.getCookie();
    }

    @Override
    public SaTokenConfig setCookie(SaCookieConfig cookie) {
        return super.setCookie(cookie);
    }
}
