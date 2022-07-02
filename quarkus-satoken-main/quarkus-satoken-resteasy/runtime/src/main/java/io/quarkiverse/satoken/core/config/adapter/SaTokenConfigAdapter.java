package io.quarkiverse.satoken.core.config.adapter;

import cn.dev33.satoken.config.SaTokenConfig;
import io.quarkiverse.satoken.core.config.SaTokenConfigForQuarkus;

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
    public Boolean getIsReadHead() {
        return config.isReadHead;
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
        return config.tokenPrefix.orElse("");
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
        return config.jwtSecretKey.orElse(null);
    }

    @Override
    public long getIdTokenTimeout() {
        return config.idTokenTimeout;
    }

    @Override
    public String getBasic() {
        return config.basic.orElse("");
    }

    @Override
    public String getCurrDomain() {
        return config.currDomain.orElse(null);
    }

    @Override
    public Boolean getCheckIdToken() {
        return config.checkIdToken;
    }

}
