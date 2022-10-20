package io.quarkiverse.satoken.resteasy;

import java.util.Objects;

import org.jboss.resteasy.reactive.server.core.CurrentRequestManager;
import org.jboss.resteasy.reactive.server.core.ResteasyReactiveRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.dev33.satoken.context.model.SaStorage;

/**
 * SaStorageForResteasy
 *
 * @author nayan
 * @date 2022/10/11 18:59
 */
public class SaStorageForResteasy implements SaStorage {
    private static final Logger LOG = LoggerFactory.getLogger(SaRequestForResteasy.class);

    private ResteasyReactiveRequestContext context;

    public SaStorageForResteasy() {
        this.context = CurrentRequestManager.get();
        if (Objects.isNull(context)) {
            LOG.warn("current ResteasyReactiveRequestContext is null , will create MockResteasyReactiveRequestContext");
            this.context = MockResteasyReactiveRequestContext.getInstance();
        }
    }

    @Override
    public Object getSource() {
        return context;
    }

    @Override
    public Object get(String key) {
        return context.getProperty(key);
    }

    @Override
    public SaStorage set(String key, Object value) {
        context.setProperty(key, value);
        return this;
    }

    @Override
    public SaStorage delete(String key) {
        context.removeProperty(key);
        return this;
    }

}
