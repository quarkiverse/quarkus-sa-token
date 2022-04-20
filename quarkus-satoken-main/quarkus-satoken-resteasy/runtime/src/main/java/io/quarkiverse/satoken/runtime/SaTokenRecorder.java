package io.quarkiverse.satoken.runtime;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.action.SaTokenAction;
import cn.dev33.satoken.basic.SaBasicTemplate;
import cn.dev33.satoken.basic.SaBasicUtil;
import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.context.SaTokenContext;
import cn.dev33.satoken.context.second.SaTokenSecondContextCreator;
import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.id.SaIdTemplate;
import cn.dev33.satoken.id.SaIdUtil;
import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.sso.SaSsoTemplate;
import cn.dev33.satoken.sso.SaSsoUtil;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.temp.SaTempInterface;
import io.quarkus.arc.Arc;
import io.quarkus.runtime.annotations.Recorder;

import javax.enterprise.inject.spi.CDI;
import java.util.function.Consumer;

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
        setSaTokenAction();
        setSaTokenContext();
        setSaTokenSecondContext();
        setSaTokenListener();
        setSaTemp();
        setSaIdTemplate();
        setSaSsoBasicTemplate();
        setSaSsoTemplate();
        setStpLogic();
    }

    /**
     * 注入配置Bean
     *
     * @param saTokenConfig 配置对象
     */
    public void setConfig() {
        injectbean(SaTokenConfig.class, SaManager::setConfig);
    }

    /**
     * 注入持久化Bean
     *
     * @param saTokenDao SaTokenDao对象
     */
    public void setSaTokenDao() {
        injectbean(SaTokenDao.class, SaManager::setSaTokenDao);
    }

    /**
     * 注入权限认证Bean
     *
     * @param stpInterface StpInterface对象
     */
    public void setStpInterface() {
        injectbean(StpInterface.class, SaManager::setStpInterface);
    }

    /**
     * 注入框架行为Bean
     *
     * @param saTokenAction SaTokenAction对象
     */
    public void setSaTokenAction() {
        injectbean(SaTokenAction.class, SaManager::setSaTokenAction);
    }

    /**
     * 注入上下文Bean
     *
     * @param saTokenContext SaTokenContext对象
     */
    public void setSaTokenContext() {
        injectbean(SaTokenContext.class, SaManager::setSaTokenContext);
    }

    /**
     * 注入二级上下文Bean
     *
     * @param saTokenSecondContextCreator 二级上下文创建器
     */
    public void setSaTokenSecondContext() {
        injectbean(SaTokenSecondContextCreator.class, context -> SaManager.setSaTokenSecondContext(context.create()));
    }

    /**
     * 注入侦听器Bean
     *
     * @param saTokenListener saTokenListener对象
     */
    public void setSaTokenListener() {
        injectbean(SaTokenListener.class, SaManager::setSaTokenListener);
    }

    /**
     * 注入临时令牌验证模块 Bean
     *
     * @param saTemp saTemp对象
     */
    public void setSaTemp() {
        injectbean(SaTempInterface.class, SaManager::setSaTemp);
    }

    /**
     * 注入 Sa-Id-Token 模块 Bean
     *
     * @param saIdTemplate saIdTemplate对象
     */
    public void setSaIdTemplate() {
        injectbean(SaIdTemplate.class, template -> SaIdUtil.saIdTemplate = template);
    }

    /**
     * 注入 Sa-Token Http Basic 认证模块
     *
     * @param saBasicTemplate saBasicTemplate对象
     */
    public void setSaSsoBasicTemplate() {
        injectbean(SaBasicTemplate.class, template -> SaBasicUtil.saBasicTemplate = template);
    }

    /**
     * 注入 Sa-Token-SSO 单点登录模块 Bean
     *
     * @param saSsoTemplate saSsoTemplate对象
     */
    public void setSaSsoTemplate() {
        injectbean(SaSsoTemplate.class, template -> SaSsoUtil.saSsoTemplate = template);
    }

    /**
     * 注入自定义的 StpLogic
     *
     * @param stpLogic /
     */
    public void setStpLogic() {
        injectbean(StpLogic.class, StpUtil::setStpLogic);
    }

    public <T> void injectbean(Class<T> clazz, Consumer<T> consumer) {
        CDI.current().select(clazz).stream().findFirst().ifPresent(consumer);
    }

}
