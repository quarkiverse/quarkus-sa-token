package io.quarkiverse.satoken.core.filter;

import cn.dev33.satoken.fun.SaParamFunction;

/**
 * SaRouteInterceptor
 *
 * @author nayan
 * @date 2022/10/9 16:44
 */
public class SaRouteInterceptor {

    public String pathPatterns;
    public SaParamFunction<Object> auth;

    public SaRouteInterceptor() {
    }

    public SaRouteInterceptor(String pathPatterns, SaParamFunction<Object> auth) {
        this.pathPatterns = pathPatterns;
        this.auth = auth;
    }

    public String getPathPatterns() {
        return pathPatterns;
    }

    public void setPathPatterns(String pathPatterns) {
        this.pathPatterns = pathPatterns;
    }

    public SaParamFunction<Object> getAuth() {
        return auth;
    }

    public void setAuth(SaParamFunction<Object> auth) {
        this.auth = auth;
    }
}
