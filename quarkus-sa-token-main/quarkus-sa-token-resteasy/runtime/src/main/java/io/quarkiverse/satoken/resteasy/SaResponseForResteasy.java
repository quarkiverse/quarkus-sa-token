package io.quarkiverse.satoken.resteasy;

import java.net.URI;
import java.util.Objects;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.core.CurrentRequestManager;
import org.jboss.resteasy.reactive.server.core.ResteasyReactiveRequestContext;
import org.jboss.resteasy.reactive.server.spi.ServerHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.dev33.satoken.context.model.SaResponse;
import jakarta.ws.rs.core.Response;

/**
 * SaResponseForResteasy
 *
 * @author nayan
 * @date 2022/10/11 18:47
 */
public class SaResponseForResteasy implements SaResponse {
    private static final Logger LOG = LoggerFactory.getLogger(SaResponseForResteasy.class);

    private final ServerHttpResponse response;

    public SaResponseForResteasy() {
        ResteasyReactiveRequestContext context = CurrentRequestManager.get();
        if (Objects.isNull(context)) {
            LOG.warn("current ResteasyReactiveRequestContext is null , will create MockResteasyReactiveRequestContext");
            context = MockResteasyReactiveRequestContext.getInstance();
        }
        this.response = context.serverResponse();
    }

    @Override
    public Object getSource() {
        return this.response;
    }

    @Override
    public SaResponse setStatus(int sc) {
        response.setStatusCode(sc);
        return this;
    }

    @Override
    public SaResponse setHeader(String name, String value) {
        response.setResponseHeader(name, value);
        return this;
    }

    @Override
    public SaResponse addHeader(String name, String value) {
        response.addResponseHeader(name, value);
        return this;
    }

    @Override
    public Object redirect(String url) {
        Response redirectResponse = Response.seeOther(URI.create(url)).status(RestResponse.Status.FOUND).build();
        response.setStatusCode(RestResponse.Status.FOUND.getStatusCode());
        response.setResponseHeader("Location", url);
        return redirectResponse;
    }
}
