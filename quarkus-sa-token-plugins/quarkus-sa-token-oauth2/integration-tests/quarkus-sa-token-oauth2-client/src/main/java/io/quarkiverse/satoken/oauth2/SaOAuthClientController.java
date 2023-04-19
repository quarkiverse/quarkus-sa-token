package io.quarkiverse.satoken.oauth2;

import com.ejlchina.okhttps.OkHttps;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import io.quarkus.qute.Template;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

/**
 * Sa-OAuth2 Client端 控制器
 *
 * @author kong
 */
@Path("/")
@ApplicationScoped
public class SaOAuthClientController {

    // 相关参数配置
    private String clientId = "1001"; // 应用id
    private String clientSecret = "aaaa-bbbb-cccc-dddd-eeee"; // 应用秘钥
    private String serverUrl = "http://sa-oauth-server.com:8001"; // 服务端接口

    @Inject
    Template index;

    // 进入首页
    @GET
    public Object index() {
        return index.data("uid", StpUtil.getLoginIdDefaultNull(), "value", "{value}");
    }

    // 根据Code码进行登录，获取 Access-Token 和 openid
    @GET
    @Path("codeLogin")
    public SaResult codeLogin(@QueryParam("code") String code) {
        // 调用Server端接口，获取 Access-Token 以及其他信息
        String str = OkHttps.sync(serverUrl + "/oauth2/token")
                .addUrlPara("grant_type", "authorization_code")
                .addUrlPara("code", code)
                .addUrlPara("client_id", clientId)
                .addUrlPara("client_secret", clientSecret)
                .post()
                .getBody()
                .toString();
        SoMap so = SoMap.getSoMap().setJsonString(str);
        System.out.println("返回结果: " + so);

        // code不等于200  代表请求失败
        if (so.getInt("code") != 200) {
            return SaResult.error(so.getString("msg"));
        }

        // 根据openid获取其对应的userId
        SoMap data = so.getMap("data");
        long uid = getUserIdByOpenid(data.getString("openid"));
        data.set("uid", uid);

        // 返回相关参数
        StpUtil.login(uid);
        return SaResult.data(data);
    }

    // 根据 Refresh-Token 去刷新 Access-Token
    @GET
    @Path("refresh")
    public SaResult refresh(@QueryParam("refreshToken") String refreshToken) {
        // 调用Server端接口，通过 Refresh-Token 刷新出一个新的 Access-Token
        String str = OkHttps.sync(serverUrl + "/oauth2/refresh")
                .addUrlPara("grant_type", "refresh_token")
                .addUrlPara("client_id", clientId)
                .addUrlPara("client_secret", clientSecret)
                .addUrlPara("refresh_token", refreshToken)
                .post()
                .getBody()
                .toString();
        SoMap so = SoMap.getSoMap().setJsonString(str);
        System.out.println("返回结果: " + so);

        // code不等于200  代表请求失败
        if (so.getInt("code") != 200) {
            return SaResult.error(so.getString("msg"));
        }

        // 返回相关参数 (data=新的Access-Token )
        SoMap data = so.getMap("data");
        return SaResult.data(data);
    }

    // 模式三：密码式-授权登录
    @GET
    @Path("passwordLogin")
    public SaResult passwordLogin(@QueryParam("username") String username, @QueryParam("password") String password) {
        // 模式三：密码式-授权登录
        String str = OkHttps.sync(serverUrl + "/oauth2/token")
                .addUrlPara("grant_type", "password")
                .addUrlPara("client_id", clientId)
                .addUrlPara("client_secret", clientSecret)
                .addUrlPara("username", username)
                .addUrlPara("password", password)
                .post()
                .getBody()
                .toString();
        SoMap so = SoMap.getSoMap().setJsonString(str);
        System.out.println("返回结果: " + so);

        // code不等于200  代表请求失败
        if (so.getInt("code") != 200) {
            return SaResult.error(so.getString("msg"));
        }

        // 根据openid获取其对应的userId
        SoMap data = so.getMap("data");
        long uid = getUserIdByOpenid(data.getString("openid"));
        data.set("uid", uid);

        // 返回相关参数
        StpUtil.login(uid);
        return SaResult.data(data);
    }

    // 模式四：获取应用的 Client-Token
    @GET
    @Path("clientToken")
    public SaResult clientToken() {
        // 调用Server端接口
        String str = OkHttps.sync(serverUrl + "/oauth2/client_token")
                .addUrlPara("grant_type", "client_credentials")
                .addUrlPara("client_id", clientId)
                .addUrlPara("client_secret", clientSecret)
                .post()
                .getBody()
                .toString();
        SoMap so = SoMap.getSoMap().setJsonString(str);
        System.out.println("返回结果: " + so);

        // code不等于200  代表请求失败
        if (so.getInt("code") != 200) {
            return SaResult.error(so.getString("msg"));
        }

        // 返回相关参数 (data=新的Client-Token )
        SoMap data = so.getMap("data");
        return SaResult.data(data);
    }

    // 注销登录
    @GET
    @Path("logout")
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok();
    }

    // 根据 Access-Token 置换相关的资源: 获取账号昵称、头像、性别等信息
    @GET
    @Path("getUserinfo")
    public SaResult getUserinfo(@QueryParam("accessToken") String accessToken) {
        // 调用Server端接口，查询开放的资源
        String str = OkHttps.sync(serverUrl + "/oauth2/userinfo")
                .addUrlPara("access_token", accessToken)
                .post()
                .getBody()
                .toString();
        SoMap so = SoMap.getSoMap().setJsonString(str);
        System.out.println("返回结果: " + so);

        // code不等于200  代表请求失败
        if (so.getInt("code") != 200) {
            return SaResult.error(so.getString("msg"));
        }

        // 返回相关参数 (data=获取到的资源 )
        SoMap data = so.getMap("data");
        return SaResult.data(data);
    }

    // ------------ 模拟方法 ------------------
    // 模拟方法：根据openid获取userId
    private long getUserIdByOpenid(String openid) {
        // 此方法仅做模拟，实际开发要根据具体业务逻辑来获取userId
        return 10001;
    }

}
