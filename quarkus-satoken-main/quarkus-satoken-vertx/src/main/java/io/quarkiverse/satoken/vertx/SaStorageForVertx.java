package io.quarkiverse.satoken.vertx;

import cn.dev33.satoken.context.model.SaStorage;
import io.vertx.core.http.HttpServerRequest;

/**
 * SaStorageForVertx
 *
 * @author nayan
 * @date 2021-11-17 16:36
 */
public class SaStorageForVertx implements SaStorage {

    private final HttpServerRequest request;

    public SaStorageForVertx(HttpServerRequest request) {
        this.request = request;
    }

    @Override
    public Object getSource() {
        return this.request;
    }

    @Override
    public void set(String s, Object o) {
        this.request.formAttributes().set(s, o.toString());
    }

    @Override
    public Object get(String s) {
        return this.request.getFormAttribute(s);
    }

    @Override
    public void delete(String s) {
        this.request.formAttributes().remove(s);
    }
}
