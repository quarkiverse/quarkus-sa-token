package io.quarkiverse.satoken.vertx;

import cn.dev33.satoken.context.model.SaRequest;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.HttpServerRequest;

import java.util.Optional;

/**
 * SaRequestForVertx
 *
 * @author nayan
 * @date 2021-11-17 14:46
 */

public class SaRequestForVertx implements SaRequest {

    private final HttpServerRequest request;

    public SaRequestForVertx(HttpServerRequest request) {
        this.request = request;
    }

    @Override
    public Object getSource() {
        return this.request;
    }

    @Override
    public String getParam(String name) {
        return this.request.getParam(name);
    }

    @Override
    public String getHeader(String name) {
        return request.getHeader(name);
    }

    @Override
    public String getCookieValue(String name) {
        Cookie cookie = this.request.getCookie(name);
        return Optional.ofNullable(cookie).map(c -> c.getValue()).orElse(null);
    }

    @Override
    public String getRequestPath() {
        return this.request.path();
    }

    @Override
    public String getUrl() {
        return this.request.absoluteURI();
    }

    @Override
    public String getMethod() {
        return this.request.method().name();
    }

    @Override
    public Object forward(String path) {
        throw new UnsupportedOperationException("not implement");
    }

}
