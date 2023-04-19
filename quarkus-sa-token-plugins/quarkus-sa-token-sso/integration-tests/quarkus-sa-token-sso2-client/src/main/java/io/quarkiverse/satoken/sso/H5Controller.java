package io.quarkiverse.satoken.sso;

import cn.dev33.satoken.sso.SaSsoHandle;
import cn.dev33.satoken.sso.SaSsoUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

/**
 * H5Controller
 *
 * @author nayan
 * @date 2022/7/2 10:31 AM
 */
@Path("/")
@ApplicationScoped
public class H5Controller {

    // 当前是否登录
    @GET
    @Path("isLogin")
    public Object isLogin() {
        return SaResult.data(StpUtil.isLogin());
    }

    // 返回SSO认证中心登录地址
    @GET
    @Path("getSsoAuthUrl")
    public SaResult getSsoAuthUrl(String clientLoginUrl) {
        String serverAuthUrl = SaSsoUtil.buildServerAuthUrl(clientLoginUrl, "");
        return SaResult.data(serverAuthUrl);
    }

    // 根据ticket进行登录
    @GET
    @Path("doLoginByTicket")
    public SaResult doLoginByTicket(String ticket) {
        Object loginId = SaSsoHandle.checkTicket(ticket, "/doLoginByTicket");
        if (loginId != null) {
            StpUtil.login(loginId);
            return SaResult.data(StpUtil.getTokenValue());
        }
        return SaResult.error("无效ticket：" + ticket);
    }
}
