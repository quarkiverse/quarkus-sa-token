package io.quarkiverse.satoken.sso.runtime;

import java.util.function.Consumer;

import cn.dev33.satoken.config.SaSsoConfig;
import cn.dev33.satoken.sso.SaSsoManager;
import cn.dev33.satoken.sso.SaSsoTemplate;
import cn.dev33.satoken.sso.SaSsoUtil;
import io.quarkus.arc.Arc;
import io.quarkus.runtime.annotations.Recorder;
import jakarta.enterprise.inject.spi.CDI;

/**
 * SaSsoRecorder
 *
 * @author nayan
 * @date 2022/4/7 2:08 PM
 */
@Recorder
public class SaSsoRecorder {

    public void injectAllBean() {
        Arc.container().instance(SaSsoProducer.class).get();
        setSaSsoConfig();
        setSaSsoTemplate();
    }

    /**
     * 注入SSO配置Bean
     *
     * @param saSsoConfig 配置对象
     */
    public void setSaSsoConfig() {
        injectbean(SaSsoConfig.class, SaSsoManager::setConfig);
    }

    /**
     * 注入 Sa-Token-SSO 单点登录模块 Bean
     *
     * @param saSsoTemplate saSsoTemplate对象
     */
    public void setSaSsoTemplate() {
        injectbean(SaSsoTemplate.class, template -> SaSsoUtil.saSsoTemplate = template);
    }

    public <T> void injectbean(Class<T> clazz, Consumer<T> consumer) {
        CDI.current().select(clazz).stream().findFirst().ifPresent(consumer);
    }

}
