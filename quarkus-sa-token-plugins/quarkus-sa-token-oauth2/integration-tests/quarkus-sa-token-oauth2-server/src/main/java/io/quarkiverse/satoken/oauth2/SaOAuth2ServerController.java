package io.quarkiverse.satoken.oauth2;

import java.util.LinkedHashMap;
import java.util.Map;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Handle;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Util;
import cn.dev33.satoken.util.SaResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

/**
 * Sa-OAuth2 Server端 控制器
 *
 * @author kong
 */

@Path("/")
@ApplicationScoped
public class SaOAuth2ServerController {

    // 处理所有OAuth相关请求
    @POST
    @Path("oauth2/{var:.*}")
    public Object post() {
        System.out.println("------- 进入请求: " + SaHolder.getRequest().getUrl());
        return SaOAuth2Handle.serverRequest();
    }

    // 处理所有OAuth相关请求
    @GET
    @Path("oauth2/{var:.*}")
    public Object get() {
        System.out.println("------- 进入请求: " + SaHolder.getRequest().getUrl());
        return SaOAuth2Handle.serverRequest();
    }

    // ---------- 开放相关资源接口： Client端根据 Access-Token ，置换相关资源 ------------

    // 获取Userinfo信息：昵称、头像、性别等等
    @POST
    @Path("oauth2/userinfo")
    public SaResult userinfo() {
        // 获取 Access-Token 对应的账号id
        String accessToken = SaHolder.getRequest().getParamNotNull("access_token");
        Object loginId = SaOAuth2Util.getLoginIdByAccessToken(accessToken);
        System.out.println("-------- 此Access-Token对应的账号id: " + loginId);

        // 校验 Access-Token 是否具有权限: userinfo
        SaOAuth2Util.checkScope(accessToken, "userinfo");

        // 模拟账号信息 （真实环境需要查询数据库获取信息）
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("nickname", "shengzhang_");
        map.put("avatar", "http://xxx.com/1.jpg");
        map.put("age", "18");
        map.put("sex", "男");
        map.put("address", "山东省 青岛市 城阳区");
        return SaResult.data(map);
    }

}
