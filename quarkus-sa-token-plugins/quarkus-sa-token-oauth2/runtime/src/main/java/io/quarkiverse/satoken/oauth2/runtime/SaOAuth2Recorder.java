package io.quarkiverse.satoken.oauth2.runtime;

import java.util.function.Consumer;

import cn.dev33.satoken.oauth2.SaOAuth2Manager;
import cn.dev33.satoken.oauth2.config.SaOAuth2Config;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Template;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Util;
import io.quarkus.arc.Arc;
import io.quarkus.runtime.annotations.Recorder;
import jakarta.enterprise.inject.spi.CDI;

/**
 * SaTokenRecorder
 *
 * @author nayan
 * @date 2022/4/7 2:08 PM
 */
@Recorder
public class SaOAuth2Recorder {

    public void injectAllBean() {
        Arc.container().instance(SaOAuth2Producer.class).get();
        setSaOAuth2Config();
        setSaOAuth2Template();
    }

    /**
     * 注入OAuth2配置Bean
     *
     * @param saOAuth2Config 配置对象
     */
    public void setSaOAuth2Config() {
        injectbean(SaOAuth2Config.class, SaOAuth2Manager::setConfig);
    }

    /**
     * 注入代码模板Bean
     *
     * @param saOAuth2Template 代码模板Bean
     */
    public void setSaOAuth2Template() {
        injectbean(SaOAuth2Template.class, template -> SaOAuth2Util.saOAuth2Template = template);
    }

    public <T> void injectbean(Class<T> clazz, Consumer<T> consumer) {
        CDI.current().select(clazz).stream().findFirst().ifPresent(consumer);
    }

}
