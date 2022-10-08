package io.quarkiverse.satoken.runtime;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.enterprise.inject.spi.CDI;

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
import io.quarkus.arc.Arc;
import io.quarkus.runtime.annotations.Recorder;

/**
 * SaTokenRecorder
 *
 * @author nayan
 * @date 2022/4/7 2:08 PM
 */
@Recorder
public class SaTokenRecorder {

    public void injectAllBean() {
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
    }

    /**
     * 注入配置Bean
     *
     * saTokenConfig 配置对象
     */
    public void setConfig() {
        injectBean(SaTokenConfig.class, SaManager::setConfig);
    }

    /**
     * 注入持久化Bean
     *
     * saTokenDao SaTokenDao对象
     */
    public void setSaTokenDao() {
        injectBean(SaTokenDao.class, SaManager::setSaTokenDao);
    }

    /**
     * 注入权限认证Bean
     *
     * stpInterface StpInterface对象
     */
    public void setStpInterface() {
        injectBean(StpInterface.class, SaManager::setStpInterface);
    }

    /**
     * 注入上下文Bean
     *
     * saTokenContext SaTokenContext对象
     */
    public void setSaTokenContext() {
        injectBean(SaTokenContext.class, SaManager::setSaTokenContext);
    }

    /**
     * 注入二级上下文Bean
     *
     * saTokenSecondContextCreator 二级上下文创建器
     */
    public void setSaTokenSecondContext() {
        injectBean(SaTokenSecondContextCreator.class, context -> SaManager.setSaTokenSecondContext(context.create()));
    }

    /**
     * 注入侦听器Bean
     *
     * saTokenListener saTokenListener对象
     */
    public void setSaTokenListener() {

        injectBeanList(SaTokenListener.class, SaTokenEventCenter::registerListenerList);
    }

    /**
     * 注入临时令牌验证模块 Bean
     *
     * saTemp saTemp对象
     */
    public void setSaTemp() {
        injectBean(SaTempInterface.class, SaManager::setSaTemp);
    }

    /**
     * 注入 Sa-Id-Token 模块 Bean
     *
     * saIdTemplate saIdTemplate对象
     */
    public void setSaIdTemplate() {
        injectBean(SaIdTemplate.class, template -> SaIdUtil.saIdTemplate = template);
    }

    /**
     * 注入 Sa-Token Http Basic 认证模块
     *
     * saBasicTemplate saBasicTemplate对象
     */
    public void setSaBasicTemplate() {
        injectBean(SaBasicTemplate.class, template -> SaBasicUtil.saBasicTemplate = template);
    }

    /**
     * 注入自定义的 JSON 转换器 Bean
     *
     * saJsonTemplate JSON 转换器
     */
    public void setSaJsonTemplate() {
        injectBean(SaJsonTemplate.class, SaManager::setSaJsonTemplate);
    }

    /**
     * 注入自定义的 参数签名 Bean
     *
     * saSignTemplate 参数签名 Bean
     */
    public void setSaSignTemplate() {
        injectBean(SaSignTemplate.class, SaManager::setSaSignTemplate);
    }

    /**
     * 注入自定义的 StpLogic
     *
     * stpLogic /
     */
    public void setStpLogic() {
        injectBean(StpLogic.class, StpUtil::setStpLogic);
    }

    public <T> void injectBean(Class<T> clazz, Consumer<T> consumer) {
        CDI.current().select(clazz).stream().findFirst().ifPresent(consumer);
    }

    public <T> void injectBeanList(Class<T> clazz, Consumer<List<T>> consumer) {
        Optional.ofNullable(CDI.current().select(clazz)).map(i -> i.stream().collect(Collectors.toList())).ifPresent(consumer);

    }

}
