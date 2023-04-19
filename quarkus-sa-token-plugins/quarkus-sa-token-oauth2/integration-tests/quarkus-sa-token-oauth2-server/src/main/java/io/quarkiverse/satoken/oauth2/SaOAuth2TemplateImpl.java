package io.quarkiverse.satoken.oauth2;

import cn.dev33.satoken.oauth2.config.SaOAuth2Config;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Template;
import cn.dev33.satoken.oauth2.model.SaClientModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import io.quarkus.qute.Template;
import io.quarkus.runtime.StartupEvent;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

/**
 * SaOAuth2TemplateImpl
 *
 * @author nayan
 * @date 2022/4/13 2:37 PM
 */
@ApplicationScoped
public class SaOAuth2TemplateImpl extends SaOAuth2Template {

    @Inject
    Template login;

    @Inject
    Template confirm;

    // Sa-OAuth2 定制化配置
    @PostConstruct
    void init(@Observes StartupEvent startupEvent, SaOAuth2Config config) {
        //        SaOAuth2Util.saOAuth2Template = this;
        // 配置：未登录时返回的View
        //        config.setNotLoginView(oauth2Forward::login).setDoLoginHandle(this::login)
        //                .setConfirmView(oauth2Forward::confirm);

        config.setNotLoginView(() -> {
            // 未登录的视图
            return login.instance();
        }).setDoLoginHandle((name, pwd) -> {
            // 登录处理函数
            if ("sa".equals(name) && "123456".equals(pwd)) {
                StpUtil.login(10001);
                return SaResult.ok();
            }
            return SaResult.error("账号名或密码错误");
        }).setConfirmView((clientId, scope) -> {
            // 授权确认视图
            return confirm.data(
                    "clientId", clientId,
                    "scope", scope);
        });
    }

    // 根据 id 获取 Client 信息
    @Override
    public SaClientModel getClientModel(String clientId) {
        // 此为模拟数据，真实环境需要从数据库查询
        if ("1001".equals(clientId)) {
            return new SaClientModel()
                    .setClientId("10001")
                    .setClientSecret("aaaa-bbbb-cccc-dddd-eeee")
                    .setAllowUrl("*")
                    .setContractScope("userinfo")
                    .setIsAutoMode(true);
        }
        return null;
    }

    // 根据ClientId 和 LoginId 获取openid
    @Override
    public String getOpenid(String clientId, Object loginId) {
        // 此为模拟数据，真实环境需要从数据库查询
        return "gr_SwoIN0MC1ewxHX_vfCW3BothWDZMMtx__";
    }

    // -------------- 其它需要重写的函数
}
