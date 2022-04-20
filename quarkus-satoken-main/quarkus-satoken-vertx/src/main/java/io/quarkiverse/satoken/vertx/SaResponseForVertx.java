package io.quarkiverse.satoken.vertx;

import cn.dev33.satoken.context.model.SaResponse;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

/**
 * SaResponseForVert
 *
 * @author nayan
 * @date 2021-11-17 15:11
 */
public class SaResponseForVertx implements SaResponse {

    private final HttpServerResponse response;
    private final RoutingContext routerContext;

    public SaResponseForVertx(HttpServerResponse response, RoutingContext routerContext) {
        this.response = response;
        this.routerContext = routerContext;
    }

    @Override
    public Object getSource() {
        return this.response;
    }

    @Override
    public SaResponse setStatus(int i) {
        response.setStatusCode(i);
        return this;
    }

    @Override
    public SaResponse setHeader(String s, String s1) {
        response.headers().set(s, s1);
        return this;
    }

    @Override
    public SaResponse addHeader(String s, String s1) {
        response.headers().add(s, s1);
        return this;
    }

    @Override
    public Object redirect(String s) {
        routerContext.redirect(s);
        return null;
    }
}
