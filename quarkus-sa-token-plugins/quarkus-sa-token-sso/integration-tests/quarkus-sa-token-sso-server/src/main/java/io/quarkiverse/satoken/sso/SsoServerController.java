package io.quarkiverse.satoken.sso;

import com.ejlchina.okhttps.OkHttps;

import cn.dev33.satoken.config.SaSsoConfig;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.sso.SaSsoHandle;
import cn.dev33.satoken.sso.SaSsoUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
// import io.quarkus.qute.Template;
import io.quarkus.runtime.StartupEvent;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;

/**
 * SsoServerController
 *
 * @author nayan
 * @date 2022/7/2 10:31 AM
 */
@Path("/")
@ApplicationScoped
public class SsoServerController {

    //    @Inject
    //    Template login;

    /**
     * SSO-Server端：处理所有SSO相关请求
     * http://{host}:{port}/sso/auth -- 单点登录授权地址，接受参数：redirect=授权重定向地址
     * http://{host}:{port}/sso/doLogin -- 账号密码登录接口，接受参数：name、pwd
     * http://{host}:{port}/sso/checkTicket -- Ticket校验接口（isHttp=true时打开），接受参数：ticket=ticket码、ssoLogoutCall=单点注销回调地址 [可选]
     * http://{host}:{port}/sso/logout -- 单点注销地址（isSlo=true时打开），接受参数：loginId=账号id、secretkey=接口调用秘钥
     */
    @GET
    @Path("sso/{var:.*}")
    public Object get() {
        return SaSsoHandle.serverRequest();
    }

    @POST
    @Path("sso/{var:.*}")
    public Object post() {
        return SaSsoHandle.serverRequest();
    }

    /**
     * 自定义接口：获取userinfo, 供模式三使用
     *
     * @param loginId
     * @return
     */
    @Path("sso/userinfo")
    public Object userinfo(String loginId) {
        System.out.println("---------------- 获取userinfo --------");

        // 校验签名，防止敏感信息外泄
        SaSsoUtil.checkSign(SaHolder.getRequest());

        // 自定义返回结果（模拟）
        return SaResult.ok()
                .set("id", loginId)
                .set("name", "linxiaoyu")
                .set("sex", "女")
                .set("age", 18);
    }

    // Sa-SSO 定制化配置
    @PostConstruct
    void init(@Observes StartupEvent startupEvent, SaSsoConfig sso) {
        final Client CLIENT = ClientBuilder.newClient();
        // 配置：未登录时返回的View
        sso.setNotLoginView(() -> {
            return CLIENT.target("http://127.0.0.1:9000/sa-res/login.html").request()
                    .get();
        });

        // 配置：登录处理函数
        sso.setDoLoginHandle((name, pwd) -> {
            // 此处仅做模拟登录，真实环境应该查询数据进行登录
            if ("sa".equals(name) && "123456".equals(pwd)) {
                StpUtil.login(10001);
                return SaResult.ok("登录成功！").setData(StpUtil.getTokenValue());
            }
            return SaResult.error("登录失败！");
        });

        // 配置 Http 请求处理器 （在模式三的单点注销功能下用到，如不需要可以注释掉）
        sso.setSendHttp(url -> {
            try {
                // 发起 http 请求
                System.out.println("发起请求：" + url);
                return OkHttps.sync(url).get().getBody().toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
    }
}
