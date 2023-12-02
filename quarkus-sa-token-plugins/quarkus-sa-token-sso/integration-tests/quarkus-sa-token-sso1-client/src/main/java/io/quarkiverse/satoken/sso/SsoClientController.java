package io.quarkiverse.satoken.sso;

import cn.dev33.satoken.sso.SaSsoManager;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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
    @Produces(MediaType.TEXT_HTML)
    public String get() {
        String authUrl = SaSsoManager.getConfig().getAuthUrl();
        String solUrl = SaSsoManager.getConfig().getSloUrl();
        String str = "<h2>Sa-Token SSO-Client 应用端</h2>" +
                "<p>当前会话是否登录：" + StpUtil.isLogin() + "</p>" +
                "<p><a href=\"javascript:location.href='" + authUrl
                + "?mode=simple&redirect=' + encodeURIComponent(location.href);\">登录</a> " +
                "<a href=\"javascript:location.href='" + solUrl + "?back=' + encodeURIComponent(location.href);\">注销</a> </p>";
        return str;
    }

}
