package io.quarkiverse.satoken.core.context;

import java.util.Objects;

import org.jboss.resteasy.reactive.server.core.CurrentRequestManager;

import cn.dev33.satoken.context.SaTokenContext;
import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.context.model.SaResponse;
import cn.dev33.satoken.context.model.SaStorage;
import io.quarkiverse.satoken.resteasy.SaRequestForResteasy;
import io.quarkiverse.satoken.resteasy.SaResponseForResteasy;
import io.quarkiverse.satoken.resteasy.SaStorageForResteasy;

/**
 * SaTokenContextForQuarkus
 *
 * @author nayan
 * @date 2022/4/6 3:10 PM
 */
public class SaTokenContextForQuarkus implements SaTokenContext {

    @Override
    public SaRequest getRequest() {
        return new SaRequestForResteasy();
    }

    @Override
    public SaResponse getResponse() {
        return new SaResponseForResteasy();
    }

    @Override
    public SaStorage getStorage() {
        return new SaStorageForResteasy();
    }

    @Override
    public boolean matchPath(String pattern, String path) {
        return SaPathMatcherHolder.getPathMatcher().match(pattern, path);
    }

    @Override
    public boolean isValid() {
        //        无论web还是mock都有效
        return true;
    }

    public boolean isWeb() {
        return Objects.nonNull(CurrentRequestManager.get());
    }

    public boolean isMock() {
        return !isWeb();
    }
}
