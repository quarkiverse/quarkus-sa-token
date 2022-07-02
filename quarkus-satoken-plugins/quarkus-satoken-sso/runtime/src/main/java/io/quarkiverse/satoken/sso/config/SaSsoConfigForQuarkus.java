package io.quarkiverse.satoken.sso.config;

import java.io.Serializable;
import java.util.Optional;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

/**
 * Sa-Token SSO 单点登录模块 配置类 Model
 *
 * @author nayan
 * @date 2022/4/6 6:27 PM
 */
@ConfigRoot(prefix = "sa-token", name = "sso", phase = ConfigPhase.BUILD_AND_RUN_TIME_FIXED)
public class SaSsoConfigForQuarkus implements Serializable {

    private static final long serialVersionUID = -6541180061782004705L;

    // ----------------- Server端相关配置 

    /**
     * Ticket有效期 (单位: 秒) ,默认5分钟
     */
    @ConfigItem(defaultValue = "300")
    public long ticketTimeout;

    /**
     * 所有允许的授权回调地址，多个用逗号隔开 (不在此列表中的URL将禁止下放ticket)
     */
    @ConfigItem(defaultValue = "*")
    public String allowUrl;

    /**
     * 是否打开单点注销功能
     */
    @ConfigItem(defaultValue = "true")
    public Boolean isSlo;

    /**
     * 是否打开模式三（此值为 true 时将使用 http 请求：校验ticket值、单点注销、获取userinfo）
     */
    @ConfigItem(defaultValue = "false")
    public Boolean isHttp;

    /**
     * 接口调用秘钥 (用于SSO模式三单点注销的接口通信身份校验)
     */
    public Optional<String> secretkey;

    // ----------------- Client端相关配置 

    /**
     * 配置 Server 端单点登录授权地址
     */
    public Optional<String> authUrl;

    /**
     * 配置 Server 端的 ticket 校验地址
     */
    public Optional<String> checkTicketUrl;

    /**
     * 配置 Server 端查询 userinfo 地址
     */
    public Optional<String> userinfoUrl;

    /**
     * 配置 Server 端单点注销地址
     */
    public Optional<String> sloUrl;

    /**
     * 配置当前 Client 端的单点注销回调URL （为空时自动获取）
     */
    public Optional<String> ssoLogoutCall;

    // -------------------- 其他 --------------------

    /**
     * 接口调用时的时间戳允许的差距（单位：ms），-1代表不校验差距
     */
    @ConfigItem(defaultValue = "600000")
    public long timestampDisparity;
}
