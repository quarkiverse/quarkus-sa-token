package io.quarkiverse.satoken.resteasy.it.configuration;

import java.util.Arrays;
import java.util.List;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.util.SaFoxUtil;
import io.quarkus.arc.DefaultBean;
import io.quarkus.arc.Unremovable;
import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;

/**
 * StpInterfaceConfiguration
 *
 * @author nayan
 * @date 2022/10/8 18:55
 */

@Dependent
public class StpInterfaceConfiguration {

    @Produces
    @Unremovable
    @IfBuildProfile("annotation")
    public StpInterface annotation() {
        return new StpInterface() {
            /**
             * 返回一个账号所拥有的权限码集合
             */
            @Override
            public List<String> getPermissionList(Object loginId, String loginType) {
                int id = SaFoxUtil.getValueByType(loginId, int.class);
                if (id == 10001) {
                    return Arrays.asList("user*", "art-add", "art-delete", "art-update", "art-get");
                } else {
                    return null;
                }
            }

            /**
             * 返回一个账号所拥有的角色标识集合
             */
            @Override
            public List<String> getRoleList(Object loginId, String loginType) {
                int id = SaFoxUtil.getValueByType(loginId, int.class);
                if (id == 10001) {
                    return Arrays.asList("admin", "super-admin");
                } else {
                    return null;
                }
            }
        };
    }

    @Produces
    @Unremovable
    @DefaultBean
    public StpInterface noopTracer() {
        return new StpInterface() {
            /**
             * 返回一个账号所拥有的权限码集合
             */
            @Override
            public List<String> getPermissionList(Object loginId, String loginType) {
                int id = SaFoxUtil.getValueByType(loginId, int.class);
                if (id == 10001) {
                    return Arrays.asList("user*", "art-add", "art-delete", "art-update", "art-get");
                } else {
                    return null;
                }
            }

            /**
             * 返回一个账号所拥有的角色标识集合
             */
            @Override
            public List<String> getRoleList(Object loginId, String loginType) {
                int id = SaFoxUtil.getValueByType(loginId, int.class);
                if (id == 10001) {
                    return Arrays.asList("admin", "super-admin");
                } else {
                    return null;
                }
            }
        };
    }
}
