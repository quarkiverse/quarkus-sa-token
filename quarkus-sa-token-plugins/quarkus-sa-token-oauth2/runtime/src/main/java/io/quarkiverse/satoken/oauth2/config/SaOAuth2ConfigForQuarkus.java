package io.quarkiverse.satoken.oauth2.config;

import java.io.Serializable;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

/**
 * Sa-Token SSO 单点登录模块 配置类 Model
 *
 * @author kong
 */
@ConfigRoot(prefix = "sa-token", name = "oauth2", phase = ConfigPhase.BUILD_AND_RUN_TIME_FIXED)
public class SaOAuth2ConfigForQuarkus implements Serializable {

    private static final long serialVersionUID = -6541180061782004705L;

    /**
     * 是否打开模式：授权码（Authorization Code）
     */
    @ConfigItem(defaultValue = "true")
    public Boolean isCode;

    /**
     * 是否打开模式：隐藏式（Implicit）
     */
    @ConfigItem(defaultValue = "false")
    public Boolean isImplicit;

    /**
     * 是否打开模式：密码式（Password）
     */
    @ConfigItem(defaultValue = "false")
    public Boolean isPassword;

    /**
     * 是否打开模式：凭证式（Client Credentials）
     */
    @ConfigItem(defaultValue = "false")
    public Boolean isClient;

    /**
     * 是否在每次 Refresh-Token 刷新 Access-Token 时，产生一个新的 Refresh-Token
     */
    @ConfigItem(defaultValue = "false")
    public Boolean isNewRefresh;

    /**
     * Code授权码 保存的时间(单位：秒) 默认五分钟
     */
    @ConfigItem(defaultValue = "300")
    public long codeTimeout;

    /**
     * Access-Token 保存的时间(单位：秒) 默认两个小时
     */
    @ConfigItem(defaultValue = "7200")
    public long accessTokenTimeout;

    /**
     * Refresh-Token 保存的时间(单位：秒) 默认30 天
     */
    @ConfigItem(defaultValue = "2592000")
    public long refreshTokenTimeout;

    /**
     * Client-Token 保存的时间(单位：秒) 默认两个小时
     */
    @ConfigItem(defaultValue = "7200")
    public long clientTokenTimeout;

    /**
     * Past-Client-Token 保存的时间(单位：秒) 默认为 -1，代表延续 Client-Token有效期
     */
    @ConfigItem(defaultValue = "-1")
    public long pastClientTokenTimeout;

}
