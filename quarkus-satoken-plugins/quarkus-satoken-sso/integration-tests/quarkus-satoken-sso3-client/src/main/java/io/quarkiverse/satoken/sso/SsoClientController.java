package io.quarkiverse.satoken.sso;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.ejlchina.okhttps.OkHttps;

import cn.dev33.satoken.config.SaSsoConfig;
import cn.dev33.satoken.sso.SaSsoHandle;
import cn.dev33.satoken.sso.SaSsoUtil;
import cn.dev33.satoken.stp.StpUtil;
import io.quarkus.runtime.StartupEvent;

/**
 * SsoClientController
 *
 * @author nayan
 * @date 2022/7/2 10:31 AM
 */
@Path("/")
@ApplicationScoped
public class SsoClientController {

    @GET
    public String index() {
        String str = "<h2>Sa-Token SSO-Client 应用端</h2>" +
                "<p>当前会话是否登录：" + StpUtil.isLogin() + "</p>" +
                "<p><a href=\"javascript:location.href='/sso/login?back=' + encodeURIComponent(location.href);\">登录</a>" +
                " <a href='/sso/logout?back=self'>注销</a></p>";
        return str;
    }

    /**
     * SSO-Client端：处理所有SSO相关请求
     * http://{host}:{port}/sso/login -- Client端登录地址，接受参数：back=登录后的跳转地址
     * http://{host}:{port}/sso/logout -- Client端单点注销地址（isSlo=true时打开），接受参数：back=注销后的跳转地址
     * http://{host}:{port}/sso/logoutCall -- Client端单点注销回调地址（isSlo=true时打开），此接口为框架回调，开发者无需关心
     */
    @GET
    @Path("/sso/{varget}")
    public Object get() {
        return SaSsoHandle.clientRequest();
    }

    @POST
    @Path("/sso/{var:.*}")
    public Object post() {
        return SaSsoHandle.clientRequest();
    }

    // 查询我的账号信息
    @Path("/sso/myinfo")
    public Object myinfo() {
        Object userinfo = SaSsoUtil.getUserinfo(StpUtil.getLoginId());
        System.out.println("--------info：" + userinfo);
        return userinfo;
    }

    // Sa-SSO 定制化配置
    @PostConstruct
    void init(@Observes StartupEvent startupEvent, SaSsoConfig sso) {
        // 配置Http请求处理器
        sso.setSendHttp(url -> {
            System.out.println("发起请求：" + url);
            return OkHttps.sync(url).get().getBody().toString();
        });
    }

}
