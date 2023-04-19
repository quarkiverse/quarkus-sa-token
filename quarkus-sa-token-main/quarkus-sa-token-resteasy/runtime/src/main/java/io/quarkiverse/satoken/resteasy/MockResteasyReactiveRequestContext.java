package io.quarkiverse.satoken.resteasy;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

import org.jboss.resteasy.reactive.server.core.ResteasyReactiveRequestContext;
import org.jboss.resteasy.reactive.server.core.multipart.FormData;
import org.jboss.resteasy.reactive.server.spi.ServerHttpRequest;
import org.jboss.resteasy.reactive.server.spi.ServerHttpResponse;

import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpMethod;

public class MockResteasyReactiveRequestContext extends ResteasyReactiveRequestContext {

    private static final ThreadLocal<MockResteasyReactiveRequestContext> requestTl = new InheritableThreadLocal<>();

    public static MockResteasyReactiveRequestContext getInstance() {
        MockResteasyReactiveRequestContext mockResteasyReactiveRequestContext = requestTl.get();
        if (Objects.isNull(mockResteasyReactiveRequestContext)) {
            mockResteasyReactiveRequestContext = new MockResteasyReactiveRequestContext();
            requestTl.set(mockResteasyReactiveRequestContext);
        }
        return mockResteasyReactiveRequestContext;
    }

    public static boolean exists() {
        return Objects.nonNull(requestTl.get());
    }

    public static void remove() {
        requestTl.remove();
    }

    MultiMap headers = MultiMap.caseInsensitiveMultiMap();
    MultiMap params = MultiMap.caseInsensitiveMultiMap();
    FormData formData = new FormData(100);
    MockServerHttpRequest request = new MockServerHttpRequest();
    MockServerHttpResponse response = new MockServerHttpResponse();

    private MockResteasyReactiveRequestContext() {
        super(null, null, null, null);
    }

    @Override
    public String getPath() {
        return "";
    }

    @Override
    public String getMethod() {
        return HttpMethod.GET.name();
    }

    @Override
    public String getAbsoluteURI() {
        return "";
    }

    @Override
    public Object getQueryParameter(String name, boolean single, boolean encoded) {
        return params.get(name);
    }

    @Override
    public Object getHeader(String name, boolean single) {
        return headers.get(name);
    }

    @Override
    public FormData getFormData() {
        return formData;
    }

    @Override
    public String getCookieParameter(String name) {
        return null;
    }

    @Override
    public ServerHttpRequest serverRequest() {
        return request;
    }

    @Override
    public ServerHttpResponse serverResponse() {
        return response;
    }

    @Override
    protected Executor getEventLoop() {
        return null;
    }

    @Override
    public Runnable registerTimer(long millis, Runnable task) {
        return null;
    }

    @Override
    public boolean resumeExternalProcessing() {
        return false;
    }

    static class MockServerHttpRequest implements ServerHttpRequest {

        @Override
        public String getRequestHeader(CharSequence name) {
            return null;
        }

        @Override
        public Iterable<Map.Entry<String, String>> getAllRequestHeaders() {
            return null;
        }

        @Override
        public List<String> getAllRequestHeaders(String name) {
            return null;
        }

        @Override
        public boolean containsRequestHeader(CharSequence accept) {
            return false;
        }

        @Override
        public String getRequestPath() {
            return null;
        }

        @Override
        public String getRequestMethod() {
            return null;
        }

        @Override
        public String getRequestNormalisedPath() {
            return null;
        }

        @Override
        public String getRequestAbsoluteUri() {
            return null;
        }

        @Override
        public String getRequestScheme() {
            return null;
        }

        @Override
        public String getRequestHost() {
            return null;
        }

        @Override
        public void closeConnection() {

        }

        @Override
        public String getQueryParam(String name) {
            return null;
        }

        @Override
        public List<String> getAllQueryParams(String name) {
            return null;
        }

        @Override
        public String query() {
            return "";
        }

        @Override
        public Collection<String> queryParamNames() {
            return null;
        }

        @Override
        public boolean isRequestEnded() {
            return false;
        }

        @Override
        public InputStream createInputStream(ByteBuffer existingData) {
            return null;
        }

        @Override
        public InputStream createInputStream() {
            return null;
        }

        @Override
        public ServerHttpResponse pauseRequestInput() {
            return null;
        }

        @Override
        public ServerHttpResponse resumeRequestInput() {
            return null;
        }

        @Override
        public ServerHttpResponse setReadListener(ReadCallback callback) {
            return null;
        }

        @Override
        public <T> T unwrap(Class<T> theType) {
            return null;
        }

        @Override
        public boolean isOnIoThread() {
            return false;
        }
    }

    static class MockServerHttpResponse implements ServerHttpResponse {

        @Override
        public ServerHttpResponse setStatusCode(int code) {
            return null;
        }

        @Override
        public ServerHttpResponse end() {
            return null;
        }

        @Override
        public boolean headWritten() {
            return false;
        }

        @Override
        public ServerHttpResponse end(byte[] data) {
            return null;
        }

        @Override
        public ServerHttpResponse end(String data) {
            return null;
        }

        @Override
        public ServerHttpResponse addResponseHeader(CharSequence name, CharSequence value) {
            return null;
        }

        @Override
        public ServerHttpResponse setResponseHeader(CharSequence name, CharSequence value) {
            return null;
        }

        @Override
        public ServerHttpResponse setResponseHeader(CharSequence name, Iterable<CharSequence> values) {
            return null;
        }

        @Override
        public Iterable<Map.Entry<String, String>> getAllResponseHeaders() {
            return null;
        }

        @Override
        public String getResponseHeader(String name) {
            return null;
        }

        @Override
        public void removeResponseHeader(String name) {

        }

        @Override
        public boolean closed() {
            return false;
        }

        @Override
        public ServerHttpResponse setChunked(boolean chunked) {
            return null;
        }

        @Override
        public ServerHttpResponse write(byte[] data, Consumer<Throwable> asyncResultHandler) {
            return null;
        }

        @Override
        public CompletionStage<Void> write(byte[] data) {
            return null;
        }

        @Override
        public ServerHttpResponse sendFile(String path, long offset, long length) {
            return null;
        }

        @Override
        public OutputStream createResponseOutputStream() {
            return null;
        }

        @Override
        public void setPreCommitListener(Consumer<ResteasyReactiveRequestContext> task) {

        }

        @Override
        public ServerHttpResponse addCloseHandler(Runnable onClose) {
            return null;
        }

        @Override
        public boolean isWriteQueueFull() {
            return false;
        }

        @Override
        public ServerHttpResponse addDrainHandler(Runnable onDrain) {
            return null;
        }
    }
}
