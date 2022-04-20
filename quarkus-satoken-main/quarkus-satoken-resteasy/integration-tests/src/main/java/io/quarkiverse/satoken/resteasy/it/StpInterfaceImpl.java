package io.quarkiverse.satoken.resteasy.it;

import cn.dev33.satoken.stp.StpInterface;
import io.quarkus.arc.DefaultBean;
import io.quarkus.arc.Unremovable;

import javax.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.List;

/**
 * StpInterfaceImpl
 *
 * @author nayan
 * @date 2022/4/8 5:54 PM
 */

@DefaultBean
@Unremovable
@ApplicationScoped
public class StpInterfaceImpl implements StpInterface {

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return Arrays.asList("user*", "art-add", "art-delete", "art-update", "art-get");
    }

    /**
     * 返回一个账号所拥有的角色标识集合
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return Arrays.asList("admin", "super-admin");
    }
}
