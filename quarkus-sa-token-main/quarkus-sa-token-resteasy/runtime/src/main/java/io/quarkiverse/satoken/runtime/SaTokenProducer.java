package io.quarkiverse.satoken.runtime;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.basic.SaBasicTemplate;
import cn.dev33.satoken.basic.SaBasicUtil;
import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.context.SaTokenContext;
import cn.dev33.satoken.context.second.SaTokenSecondContextCreator;
import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.id.SaIdTemplate;
import cn.dev33.satoken.id.SaIdUtil;
import cn.dev33.satoken.json.SaJsonTemplate;
import cn.dev33.satoken.listener.SaTokenEventCenter;
import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.sign.SaSignTemplate;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.temp.SaTempInterface;
import io.quarkiverse.satoken.core.config.SaCookieConfigForQuarkus;
import io.quarkiverse.satoken.core.config.SaTokenConfigForQuarkus;
import io.quarkiverse.satoken.core.config.adapter.SaTokenConfigAdapter;
import io.quarkiverse.satoken.core.context.SaPathMatcherHolder;
import io.quarkiverse.satoken.core.context.SaTokenContextForQuarkus;
import io.quarkiverse.satoken.core.json.SaJsonTemplateForJackson;
import io.quarkiverse.satoken.core.utils.AntPathMatcher;
import io.quarkus.arc.Arc;
import io.quarkus.arc.DefaultBean;
import io.quarkus.arc.Unremovable;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Singleton;

/**
 * SaTokenProvider
 *
 * @author nayan
 * @date 2022/4/6 3:47 PM
 */
@ApplicationScoped
public class SaTokenProducer {

    @Produces
    @DefaultBean
    @Unremovable
    @Singleton
    public SaTokenConfig config(SaTokenConfigForQuarkus config, SaCookieConfigForQuarkus cookieConfig) {
        return new SaTokenConfigAdapter(config, cookieConfig);
    }

    @Produces
    @DefaultBean
    @Unremovable
    @ApplicationScoped
    public SaTokenContextForQuarkus saTokenContextForQuarkus() {
        return new SaTokenContextForQuarkus();
    }

    @Produces
    @DefaultBean
    @Unremovable
    @ApplicationScoped
    public SaJsonTemplateForJackson saJsonTemplateForJackson(ObjectMapper objectMapper) {
        return new SaJsonTemplateForJackson(objectMapper);
    }

    public void injectAllBean(@Observes StartupEvent startupEvent) {
        Arc.container().instance(SaTokenProducer.class).get();
        setConfig();
        setSaTokenDao();
        setStpInterface();
        setSaTokenContext();
        setSaTokenSecondContext();
        setSaTokenListener();
        setSaTemp();
        setSaIdTemplate();
        setSaBasicTemplate();
        setSaJsonTemplate();
        setStpLogic();
        setSaSignTemplate();
        setPathMatcher();
    }

    /**
     * 注入配置Bean
     * <p>
     * saTokenConfig 配置对象
     */
    public void setConfig() {
        injectBean(SaTokenConfig.class, SaManager::setConfig);
    }

    /**
     * 注入持久化Bean
     * <p>
     * saTokenDao SaTokenDao对象
     */
    public void setSaTokenDao() {
        injectBean(SaTokenDao.class, SaManager::setSaTokenDao);
    }

    /**
     * 注入权限认证Bean
     * <p>
     * stpInterface StpInterface对象
     */
    public void setStpInterface() {
        injectBean(StpInterface.class, SaManager::setStpInterface);
    }

    /**
     * 注入上下文Bean
     * <p>
     * saTokenContext SaTokenContext对象
     */
    public void setSaTokenContext() {
        injectBean(SaTokenContext.class, SaManager::setSaTokenContext);
    }

    /**
     * 注入二级上下文Bean
     * <p>
     * saTokenSecondContextCreator 二级上下文创建器
     */
    public void setSaTokenSecondContext() {
        injectBean(SaTokenSecondContextCreator.class, context -> SaManager.setSaTokenSecondContext(context.create()));
    }

    /**
     * 注入侦听器Bean
     * <p>
     * saTokenListener saTokenListener对象
     */
    public void setSaTokenListener() {

        injectBeanList(SaTokenListener.class, SaTokenEventCenter::registerListenerList);
    }

    /**
     * 注入临时令牌验证模块 Bean
     * <p>
     * saTemp saTemp对象
     */
    public void setSaTemp() {
        injectBean(SaTempInterface.class, SaManager::setSaTemp);
    }

    /**
     * 注入 Sa-Id-Token 模块 Bean
     * <p>
     * saIdTemplate saIdTemplate对象
     */
    public void setSaIdTemplate() {
        injectBean(SaIdTemplate.class, template -> SaIdUtil.saIdTemplate = template);
    }

    /**
     * 注入 Sa-Token Http Basic 认证模块
     * <p>
     * saBasicTemplate saBasicTemplate对象
     */
    public void setSaBasicTemplate() {
        injectBean(SaBasicTemplate.class, template -> SaBasicUtil.saBasicTemplate = template);
    }

    /**
     * 注入自定义的 JSON 转换器 Bean
     * <p>
     * saJsonTemplate JSON 转换器
     */
    public void setSaJsonTemplate() {
        injectBean(SaJsonTemplate.class, SaManager::setSaJsonTemplate);
    }

    /**
     * 注入自定义的 参数签名 Bean
     * <p>
     * saSignTemplate 参数签名 Bean
     */
    public void setSaSignTemplate() {
        injectBean(SaSignTemplate.class, SaManager::setSaSignTemplate);
    }

    /**
     * 注入自定义的 StpLogic
     * <p>
     * stpLogic /
     */
    public void setStpLogic() {
        injectBean(StpLogic.class, StpUtil::setStpLogic);
    }

    /**
     * 路由匹配器
     */
    public void setPathMatcher() {
        SaPathMatcherHolder.setPathMatcher(new AntPathMatcher());
    }

    public <T> void injectBean(Class<T> clazz, Consumer<T> consumer) {
        CDI.current().select(clazz).stream().findFirst().ifPresent(consumer);
    }

    public <T> void injectBeanList(Class<T> clazz, Consumer<List<T>> consumer) {
        Optional.ofNullable(CDI.current().select(clazz))
                .map(i -> i.stream().collect(Collectors.toList()))
                .ifPresent(consumer);

    }

}
