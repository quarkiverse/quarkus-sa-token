package io.quarkiverse.satoken.resteasy.it.configuration;

import java.util.Arrays;

import org.jboss.resteasy.reactive.server.core.CurrentRequestManager;
import org.jboss.resteasy.reactive.server.core.ResteasyReactiveRequestContext;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import io.quarkiverse.satoken.core.filter.SaRouteInterceptor;
import io.quarkus.arc.Unremovable;
import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;

/**
 * RouterFilterConfiguration
 *
 * @author nayan
 * @date 2022/10/9 16:20
 */
@Dependent
public class RouterFilterConfiguration {

    @Produces
    @Unremovable
    @Priority(1)
    public SaRouteInterceptor configure2() {
        return new SaRouteInterceptor("/**", handle -> {

            // 匹配 getInfo ，返回code=201
            SaRouter.match("/**")
                    .match(SaHttpMethod.POST)
                    .matchMethod("POST")
                    .match(SaHolder.getRequest().getMethod().equals("POST"))
                    .match(r -> SaHolder.getRequest().isPath("/rt/getInfo"))
                    .match(r -> SaHolder.getRequest().isParam("name", "zhang"))
                    .back(SaResult.code(201));

            // 匹配 getInfo2 ，返回code=202
            SaRouter.match("/rt/getInfo2")
                    .match(Arrays.asList("/rt/getInfo2", "/rt/*"))
                    .notMatch("/rt/getInfo3")
                    .notMatch(false)
                    .notMatch(r -> false)
                    .notMatch(SaHttpMethod.GET)
                    .notMatchMethod("PUT")
                    .notMatch(Arrays.asList("/rt/getInfo4", "/rt/getInfo5"))
                    .back(SaResult.code(202));

            // 匹配 getInfo3 ，返回code=203
            SaRouter.match("/rt/getInfo3", "/rt/getInfo4", () -> SaRouter.back(SaResult.code(203)));
            SaRouter.match("/rt/getInfo4", "/rt/getInfo5", r -> SaRouter.back(SaResult.code(204)));
            SaRouter.match("/rt/getInfo5", () -> SaRouter.back(SaResult.code(205)));
            SaRouter.match("/rt/getInfo6", r -> SaRouter.back(SaResult.code(206)));

            // 通往 Controller
            SaRouter.match(Arrays.asList("/rt/getInfo7")).stop();

            // 通往 Controller
            SaRouter.match("/rt/getInfo8", () -> SaRouter.stop());

            SaRouter.matchMethod("POST").match("/rt/getInfo9").free(r -> SaRouter.back(SaResult.code(209)));
            SaRouter.match(SaHttpMethod.POST).match("/rt/getInfo10").setHit(false).back();

            // 11
            SaRouter.notMatch("/rt/getInfo11").reset().match("/rt/getInfo11").back(SaResult.code(211));
            SaRouter.notMatch(SaHttpMethod.GET).match("/rt/getInfo12").back(SaResult.code(212));
            SaRouter.notMatch(Arrays.asList("/rt/getInfo12", "/rt/getInfo14")).match("/rt/getInfo13").back(SaResult.code(213));
            SaRouter.notMatchMethod("GET", "PUT").match("/rt/getInfo14").back(SaResult.code(214));

            //        	SaRouter.match(Arrays.asList("/rt/getInfo15", "/rt/getInfo16"))
            ResteasyReactiveRequestContext resteasyReactiveRequestContext = CurrentRequestManager.get();
            if (SaRouter.isMatchCurrURI("/rt/getInfo15")) {
                if (SaHolder.getRequest().getCookieValue("ddd") == null
                        && SaHolder.getStorage().getSource() == resteasyReactiveRequestContext
                        && SaHolder.getRequest().getSource() == resteasyReactiveRequestContext
                        && SaHolder.getResponse().getSource() == resteasyReactiveRequestContext.serverResponse()) {
                    SaRouter.newMatch().free(r -> SaRouter.back(SaResult.code(215)));
                }
            }

            SaRouter.match("/rt/getInfo16", () -> {
                try {
                    SaHolder.getResponse().redirect(null);
                } catch (Exception e) {

                }
                SaHolder.getResponse().redirect("/rt/getInfo3");
            });
        });
    }

    @Produces
    @Unremovable
    @IfBuildProfile("route")
    @Priority(100)
    public SaRouteInterceptor configure31() {
        return new SaRouteInterceptor("/rt/getInfo_200", handle -> {
            SaRouter.stop();
        });
    }

    @Produces
    @Unremovable
    @IfBuildProfile("route")
    @Priority(100)
    public SaRouteInterceptor configure32() {
        return new SaRouteInterceptor("/rt/getInfo_201", handle -> {
            StpUtil.checkLogin();
        });
    }

    @Produces
    @Unremovable
    @IfBuildProfile("route")
    @Priority(101)
    public SaRouteInterceptor configure33() {
        return new SaRouteInterceptor("/rt/getInfo_201", handle -> {
            SaRouter.back(SaResult.code(201));
        });
    }

    @Produces
    @Unremovable
    @IfBuildProfile("route")
    @Priority(100)
    public SaRouteInterceptor configure34() {
        return new SaRouteInterceptor("/rt/getInfo_202", handle -> {
            StpUtil.checkLogin();
        });
    }
}
