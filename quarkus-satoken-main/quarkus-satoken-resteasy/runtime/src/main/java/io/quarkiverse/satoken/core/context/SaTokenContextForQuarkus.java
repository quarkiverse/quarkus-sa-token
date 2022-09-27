package io.quarkiverse.satoken.core.context;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.inject.IllegalProductException;
import javax.enterprise.inject.spi.CDI;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.security.cert.X509Certificate;

import org.jboss.resteasy.reactive.server.core.CurrentRequestManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.dev33.satoken.context.SaTokenContext;
import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.context.model.SaResponse;
import cn.dev33.satoken.context.model.SaStorage;
import io.netty.handler.codec.DecoderResult;
import io.quarkiverse.satoken.core.utils.AntPathMatcher;
import io.quarkiverse.satoken.vertx.SaRequestForVertx;
import io.quarkiverse.satoken.vertx.SaResponseForVertx;
import io.quarkiverse.satoken.vertx.SaStorageForVertx;
import io.quarkus.vertx.http.runtime.CurrentVertxRequest;
import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.HttpConnection;
import io.vertx.core.http.HttpFrame;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerFileUpload;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.http.HttpVersion;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.http.StreamPriority;
import io.vertx.core.net.NetSocket;
import io.vertx.ext.web.RoutingContext;

/**
 * SaTokenContextForQuarkus
 *
 * @author nayan
 * @date 2022/4/6 3:10 PM
 */
public class SaTokenContextForQuarkus implements SaTokenContext {
    private final static Logger log = LoggerFactory.getLogger(SaTokenContextForQuarkus.class);
    private final static String FORM = "form";

    private static AntPathMatcher antPathMatcher = new AntPathMatcher();

    private static ThreadLocal<HttpServerResponse> responseTl = new InheritableThreadLocal<HttpServerResponse>();
    private static ThreadLocal<HttpServerRequest> requestTl = new InheritableThreadLocal<HttpServerRequest>();

    @Override
    public SaRequest getRequest() {
        HttpServerRequest request = getHttpServerRequest();
        return new SaRequestForVertx(request);
    }

    @Override
    public SaResponse getResponse() {
        HttpServerResponse response = getHttpServerResponse();
        RoutingContext context = CDI.current().select(CurrentVertxRequest.class).get().getCurrent();
        return new SaResponseForVertx(response, context);
    }

    @Override
    public SaStorage getStorage() {
        HttpServerRequest request = getHttpServerRequest();
        return new SaStorageForVertx(request);
    }

    @Override
    public boolean matchPath(String pattern, String path) {
        return antPathMatcher.match(pattern, path);
    }

    private HttpServerRequest getHttpServerRequest() {

        HttpServerRequest request = null;
        try {
            request = getRoutingContext().request();
            String contextType = request.getHeader(HttpHeaders.CONTENT_TYPE);
            //如果是APPLICATION_X_WWW_FORM_URLENCODED类型，将参数加入vertx-request
            if (Objects.nonNull(contextType) && contextType.toLowerCase().indexOf(FORM) != -1) {
                MultiMap params = request.params();
                MultiMap forms = request.formAttributes();
                Optional.ofNullable(CurrentRequestManager.get().getFormData()).ifPresent(formParameters -> {
                    formParameters.forEach((k) -> {
                        List<String> collect = formParameters.get(k).stream().map(vl -> vl.getValue())
                                .collect(Collectors.toList());
                        if (!forms.contains(k)) {
                            forms.add(k, collect);
                        }
                        if (!params.contains(k)) {
                            params.add(k, collect);
                        }
                    });
                });
            }
        } catch (IllegalProductException e) {
            log.warn("request is null,will create mock response");
            request = requestTl.get();
            if (Objects.isNull(request)) {
                request = new MockHttpServerRequest();
                requestTl.set(request);
            }
        }

        return request;
    }

    private RoutingContext getRoutingContext() {
        return CDI.current().select(RoutingContext.class).get();
    }

    private HttpServerResponse getHttpServerResponse() {

        HttpServerResponse response = null;
        try {
            response = getRoutingContext().response();
        } catch (IllegalProductException e) {
            log.warn("response is null,will use mock request");
            response = responseTl.get();
            if (Objects.isNull(response)) {
                response = new MockHttpServerResponse();
                responseTl.set(response);
            }
        }
        return response;
    }

    static class MockHttpServerRequest implements HttpServerRequest {

        MultiMap headers = MultiMap.caseInsensitiveMultiMap();
        MultiMap params = MultiMap.caseInsensitiveMultiMap();
        MultiMap forms = MultiMap.caseInsensitiveMultiMap();
        Charset paramsCharset;

        @Override
        public HttpServerRequest exceptionHandler(Handler<Throwable> handler) {
            return this;
        }

        @Override
        public HttpServerRequest handler(Handler<Buffer> handler) {
            return this;
        }

        @Override
        public HttpServerRequest pause() {
            return null;
        }

        @Override
        public HttpServerRequest resume() {
            return this;
        }

        @Override
        public HttpServerRequest fetch(long amount) {
            return this;
        }

        @Override
        public HttpServerRequest endHandler(Handler<Void> endHandler) {
            return this;
        }

        @Override
        public HttpVersion version() {
            return HttpVersion.HTTP_1_1;
        }

        @Override
        public HttpMethod method() {
            return HttpMethod.GET;
        }

        @Override
        public @Nullable String scheme() {
            return "http";
        }

        @Override
        public String uri() {
            return "";
        }

        @Override
        public @Nullable String path() {
            return "";
        }

        @Override
        public @Nullable String query() {
            return "";
        }

        @Override
        public @Nullable String host() {
            return "localhost";
        }

        @Override
        public long bytesRead() {
            return 0;
        }

        @Override
        public HttpServerResponse response() {
            return null;
        }

        @Override
        public MultiMap headers() {
            return headers;
        }

        @Override
        public HttpServerRequest setParamsCharset(String charset) {
            Charset current = this.paramsCharset;
            this.paramsCharset = Charset.forName(charset);
            if (!this.paramsCharset.equals(current)) {
                this.params = null;
            }
            return this;
        }

        @Override
        public String getParamsCharset() {
            return null;
        }

        @Override
        public MultiMap params() {
            return params;
        }

        @Override
        public X509Certificate[] peerCertificateChain() throws SSLPeerUnverifiedException {
            return new X509Certificate[0];
        }

        @Override
        public String absoluteURI() {
            return "";
        }

        @Override
        public Future<Buffer> body() {
            return null;
        }

        @Override
        public Future<Void> end() {
            return null;
        }

        @Override
        public Future<NetSocket> toNetSocket() {
            return null;
        }

        @Override
        public HttpServerRequest setExpectMultipart(boolean expect) {
            return null;
        }

        @Override
        public boolean isExpectMultipart() {
            return false;
        }

        @Override
        public HttpServerRequest uploadHandler(@Nullable Handler<HttpServerFileUpload> uploadHandler) {
            return this;
        }

        @Override
        public MultiMap formAttributes() {
            return forms;
        }

        @Override
        public @Nullable String getFormAttribute(String attributeName) {
            return formAttributes().get(attributeName);
        }

        @Override
        public Future<ServerWebSocket> toWebSocket() {
            return null;
        }

        @Override
        public boolean isEnded() {
            return false;
        }

        @Override
        public HttpServerRequest customFrameHandler(Handler<HttpFrame> handler) {
            return this;
        }

        @Override
        public HttpConnection connection() {
            return null;
        }

        @Override
        public HttpServerRequest streamPriorityHandler(Handler<StreamPriority> handler) {
            return this;
        }

        @Override
        public DecoderResult decoderResult() {
            return null;
        }

        @Override
        public @Nullable Cookie getCookie(String name) {
            return null;
        }

        @Override
        public @Nullable Cookie getCookie(String name, String domain, String path) {
            return null;
        }

        @Override
        public Set<Cookie> cookies(String name) {
            return new HashSet<>();
        }

        @Override
        public Set<Cookie> cookies() {
            return new HashSet<>();
        }
    }

    static class MockHttpServerResponse implements HttpServerResponse {

        MultiMap headers = MultiMap.caseInsensitiveMultiMap();
        MultiMap trailers = MultiMap.caseInsensitiveMultiMap();

        @Override
        public HttpServerResponse exceptionHandler(Handler<Throwable> handler) {
            return this;
        }

        @Override
        public Future<Void> write(Buffer data) {
            return null;
        }

        @Override
        public void write(Buffer data, Handler<AsyncResult<Void>> handler) {

        }

        @Override
        public HttpServerResponse setWriteQueueMaxSize(int maxSize) {
            return this;
        }

        @Override
        public boolean writeQueueFull() {
            return false;
        }

        @Override
        public HttpServerResponse drainHandler(Handler<Void> handler) {
            return this;
        }

        @Override
        public int getStatusCode() {
            return 200;
        }

        @Override
        public HttpServerResponse setStatusCode(int statusCode) {
            return this;
        }

        @Override
        public String getStatusMessage() {
            return "";
        }

        @Override
        public HttpServerResponse setStatusMessage(String statusMessage) {
            return this;
        }

        @Override
        public HttpServerResponse setChunked(boolean chunked) {
            return this;
        }

        @Override
        public boolean isChunked() {
            return false;
        }

        @Override
        public MultiMap headers() {
            return headers;
        }

        @Override
        public HttpServerResponse putHeader(String name, String value) {
            headers.add(name, value);
            return this;
        }

        @Override
        public HttpServerResponse putHeader(CharSequence name, CharSequence value) {
            headers.add(name, value);
            return this;
        }

        @Override
        public HttpServerResponse putHeader(String name, Iterable<String> values) {
            values.forEach(v -> headers.add(name, v));
            return this;
        }

        @Override
        public HttpServerResponse putHeader(CharSequence name, Iterable<CharSequence> values) {
            values.forEach(v -> headers.add(name, v));
            return this;
        }

        @Override
        public MultiMap trailers() {
            return trailers;
        }

        @Override
        public HttpServerResponse putTrailer(String name, String value) {
            trailers.add(name, value);
            return this;
        }

        @Override
        public HttpServerResponse putTrailer(CharSequence name, CharSequence value) {
            trailers.add(name, value);
            return this;
        }

        @Override
        public HttpServerResponse putTrailer(String name, Iterable<String> values) {
            values.forEach(v -> headers.add(name, v));
            return this;
        }

        @Override
        public HttpServerResponse putTrailer(CharSequence name, Iterable<CharSequence> value) {
            value.forEach(v -> headers.add(name, v));
            return this;
        }

        @Override
        public HttpServerResponse closeHandler(@Nullable Handler<Void> handler) {
            return this;
        }

        @Override
        public HttpServerResponse endHandler(@Nullable Handler<Void> handler) {
            return this;
        }

        @Override
        public Future<Void> write(String chunk, String enc) {
            return null;
        }

        @Override
        public void write(String chunk, String enc, Handler<AsyncResult<Void>> handler) {

        }

        @Override
        public Future<Void> write(String chunk) {
            return null;
        }

        @Override
        public void write(String chunk, Handler<AsyncResult<Void>> handler) {

        }

        @Override
        public HttpServerResponse writeContinue() {
            return this;
        }

        @Override
        public Future<Void> end(String chunk) {
            return null;
        }

        @Override
        public void end(String chunk, Handler<AsyncResult<Void>> handler) {

        }

        @Override
        public Future<Void> end(String chunk, String enc) {
            return null;
        }

        @Override
        public void end(String chunk, String enc, Handler<AsyncResult<Void>> handler) {

        }

        @Override
        public Future<Void> end(Buffer chunk) {
            return null;
        }

        @Override
        public void end(Buffer chunk, Handler<AsyncResult<Void>> handler) {

        }

        @Override
        public Future<Void> end() {
            return null;
        }

        @Override
        public void end(Handler<AsyncResult<Void>> handler) {

        }

        @Override
        public Future<Void> sendFile(String filename, long offset, long length) {
            return null;
        }

        @Override
        public HttpServerResponse sendFile(String filename, long offset, long length,
                Handler<AsyncResult<Void>> resultHandler) {
            return this;
        }

        @Override
        public void close() {

        }

        @Override
        public boolean ended() {
            return false;
        }

        @Override
        public boolean closed() {
            return false;
        }

        @Override
        public boolean headWritten() {
            return false;
        }

        @Override
        public HttpServerResponse headersEndHandler(@Nullable Handler<Void> handler) {
            return this;
        }

        @Override
        public HttpServerResponse bodyEndHandler(@Nullable Handler<Void> handler) {
            return this;
        }

        @Override
        public long bytesWritten() {
            return 0;
        }

        @Override
        public int streamId() {
            return 0;
        }

        @Override
        public Future<HttpServerResponse> push(HttpMethod method, String host, String path, MultiMap headers) {
            return null;
        }

        @Override
        public boolean reset(long code) {
            return false;
        }

        @Override
        public HttpServerResponse writeCustomFrame(int type, int flags, Buffer payload) {
            return this;
        }

        @Override
        public HttpServerResponse addCookie(Cookie cookie) {
            return this;
        }

        @Override
        public @Nullable Cookie removeCookie(String name, boolean invalidate) {
            return null;
        }

        @Override
        public Set<Cookie> removeCookies(String name, boolean invalidate) {
            return new HashSet<>();
        }

        @Override
        public @Nullable Cookie removeCookie(String name, String domain, String path, boolean invalidate) {
            return null;
        }
    }
}
