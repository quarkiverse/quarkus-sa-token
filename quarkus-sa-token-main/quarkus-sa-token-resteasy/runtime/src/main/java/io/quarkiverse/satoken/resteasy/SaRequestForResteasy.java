package io.quarkiverse.satoken.resteasy;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

import org.jboss.resteasy.reactive.server.core.CurrentRequestManager;
import org.jboss.resteasy.reactive.server.core.ResteasyReactiveRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.util.SaFoxUtil;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;

/**
 * SaRequestForRestEasy
 *
 * @author nayan
 * @date 2022/10/11 18:00
 */
public class SaRequestForResteasy implements SaRequest {
    private static final Logger LOG = LoggerFactory.getLogger(SaRequestForResteasy.class);
    private static final Client CLIENT = ClientBuilder.newClient();

    private ResteasyReactiveRequestContext context;

    public SaRequestForResteasy() {
        this.context = CurrentRequestManager.get();
        if (Objects.isNull(context)) {
            LOG.warn("current ResteasyReactiveRequestContext is null , will create MockResteasyReactiveRequestContext");
            this.context = MockResteasyReactiveRequestContext.getInstance();
        }
    }

    @Override
    public Object getSource() {
        return this.context;
    }

    /**
     * if there is not @FormParam in Endpoint,the form data will not parsing.
     *
     * @see <a href="https://github.com/quarkusio/quarkus/discussions/25103">RESTEasy Reactive - get all form data</a>
     * @see <a href="https://github.com/quarkusio/quarkus/issues/22444">RESTEasy Reactive: make body-reading filters force
     *      reading the body</a>
     * @param name param name
     * @return param value
     */
    @Override
    public String getParam(String name) {
        return Optional.ofNullable(this.context.getQueryParameter(name, true, false))
                .or(() -> Optional.ofNullable(this.context.getFormParameter(name, true, true)))
                .map(Object::toString)
                .orElse(null);
    }

    @Override
    public String getHeader(String name) {
        return Optional.ofNullable(this.context.getHeader(name, true))
                .map(Objects::toString)
                .orElse(null);
    }

    @Override
    public String getCookieValue(String name) {
        return this.context.getCookieParameter(name);
    }

    @Override
    public String getRequestPath() {
        return this.context.getPath();
    }

    @Override
    public String getUrl() {
        String currDomain = SaManager.getConfig().getCurrDomain();
        if (SaFoxUtil.isEmpty(currDomain) == false) {
            return currDomain + this.getRequestPath();
        }
        return context.getUriInfo().getAbsolutePath().toString();
    }

    @Override
    public String getMethod() {
        return this.context.getMethod();
    }

    @Override
    public Object forward(String path) {
        URI baseUri = context.getUriInfo().getBaseUri();
        String newUrl = baseUri.getScheme() + "://" + baseUri.getAuthority() + path;
        return CLIENT.target(newUrl).request()
                .post(Entity.json(null), String.class);
    }

}
