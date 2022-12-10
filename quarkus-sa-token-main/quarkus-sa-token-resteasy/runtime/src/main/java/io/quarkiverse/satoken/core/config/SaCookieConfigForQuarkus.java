package io.quarkiverse.satoken.core.config;

import java.util.Optional;

import io.quarkus.runtime.annotations.ConfigGroup;
import io.quarkus.runtime.annotations.ConfigItem;

/**
 * Sa-Token Cookie写入 相关配置
 *
 * @author nayan
 * @date 2022/4/6 6:27 PM
 */
@ConfigGroup
public class SaCookieConfigForQuarkus {

    /**
     * 域（写入Cookie时显式指定的作用域, 常用于单点登录二级域名共享Cookie的场景）
     */
    public Optional<String> domain;

    /**
     * 路径
     */
    public Optional<String> path;

    /**
     * 是否只在 https 协议下有效
     */
    @ConfigItem(defaultValue = "false")
    public Boolean secure;

    /**
     * 是否禁止 js 操作 Cookie
     */
    @ConfigItem(defaultValue = "false")
    public Boolean httpOnly;

    /**
     * 第三方限制级别（Strict=完全禁止，Lax=部分允许，None=不限制）
     */
    public Optional<String> sameSite;

}
