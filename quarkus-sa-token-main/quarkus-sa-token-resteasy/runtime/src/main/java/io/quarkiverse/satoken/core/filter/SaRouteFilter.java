package io.quarkiverse.satoken.core.filter;

import java.io.IOException;
import java.util.List;

import org.jboss.resteasy.reactive.server.ServerRequestFilter;

import cn.dev33.satoken.exception.BackResultException;
import cn.dev33.satoken.exception.StopMatchException;
import cn.dev33.satoken.router.SaRouter;
import io.quarkiverse.satoken.core.config.SaRouteConfigForQuarkus;
import io.quarkus.arc.All;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * SaRouteFilter
 *
 * @author nayan
 * @date 2022/4/14 4:48 PM
 */
public class SaRouteFilter {

    @Context
    ResourceInfo resourceInfo;

    /**
     * 认证函数：每次请求执行
     * <p>
     * 参数：路由处理函数指针
     */
    @All
    @Inject
    List<SaRouteInterceptor> interceptors;

    @Inject
    SaRouteConfigForQuarkus routeConfig;

    @ServerRequestFilter(priority = Priorities.AUTHENTICATION)
    public Response pre(ContainerRequestContext requestContext) throws IOException {
        if (routeConfig.excludePaths.isPresent()) {
            if (SaRouter.match(routeConfig.excludePaths.get()).isHit) {
                return null;
            }
        } else {
            if (SaRouter.match(routeConfig.includePaths).isHit) {
                try {
                    interceptors.stream()
                            .filter(interceptor -> SaRouter.match(interceptor.getPathPatterns()).isHit)
                            .findFirst()
                            .ifPresent(interceptor -> {
                                interceptor.auth.run(resourceInfo.getResourceMethod());
                            });
                } catch (StopMatchException e) {
                    // 停止匹配，进入Controller
                } catch (BackResultException e) {
                    // 停止匹配，向前端输出结果
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(e.result)
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN)
                            .build();
                }
            }
        }
        return null;
    }
}
