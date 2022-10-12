package io.quarkiverse.satoken.core.context;

import io.quarkiverse.satoken.core.utils.AntPathMatcher;

/**
 * SaPathMatcherHolder
 *
 * @author nayan
 * @date 2022/10/12 10:53
 */
public class SaPathMatcherHolder {
    private SaPathMatcherHolder() {
    }

    /**
     * 路由匹配器
     */
    public static AntPathMatcher pathMatcher;

    /**
     * 获取路由匹配器
     * @return 路由匹配器
     */
    public static AntPathMatcher getPathMatcher() {
        if(pathMatcher == null) {
            pathMatcher = new AntPathMatcher();
        }
        return pathMatcher;
    }

    /**
     * 写入路由匹配器
     * @param pathMatcher 路由匹配器
     */
    public static void setPathMatcher(AntPathMatcher pathMatcher) {
        SaPathMatcherHolder.pathMatcher = pathMatcher;
    }
}
