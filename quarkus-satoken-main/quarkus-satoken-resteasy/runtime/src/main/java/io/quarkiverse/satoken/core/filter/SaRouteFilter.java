package io.quarkiverse.satoken.core.filter;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.BackResultException;
import cn.dev33.satoken.exception.StopMatchException;
import cn.dev33.satoken.router.SaRouteFunction;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import io.quarkiverse.satoken.core.config.SaRouteConfigForQuarkus;
import io.quarkiverse.satoken.core.config.SaTokenConfigForQuarkus;

import javax.annotation.Priority;
import javax.enterprise.inject.spi.CDI;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * SaRouteFilter
 *
 * @author nayan
 * @date 2022/4/14 4:48 PM
 */
@Priority(1)
public class SaRouteFilter implements ContainerRequestFilter {

    @Context
    ResourceInfo resourceInfo;

    /**
     * 每次进入拦截器的[执行函数]，默认为登录校验
     */
    public SaRouteFunction function = (req, res, handler) -> StpUtil.checkLogin();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        SaTokenConfigForQuarkus config = CDI.current().select(SaTokenConfigForQuarkus.class).get();
        SaRouteConfigForQuarkus routeConfig = config.route;
        if (routeConfig.excludePaths.isPresent()) {
            if (SaRouter.match(routeConfig.excludePaths.get()).isHit) {
                return;
            }
        } else {
            if (SaRouter.match(routeConfig.includePaths).isHit) {
                try {
                    function.run(SaHolder.getRequest(), SaHolder.getResponse(), resourceInfo.getResourceMethod());
                } catch (StopMatchException e) {
                    // 停止匹配，进入Controller
                } catch (BackResultException e) {
                    // 停止匹配，向前端输出结果

                    requestContext.abortWith(
                            Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                    .entity(e.getMessage())
                                    .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN)
                                    .build());
                }
            }
        }
    }
}
