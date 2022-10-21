package io.quarkiverse.satoken.sso;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

/**
 * SsoServerMain
 *
 * @author nayan
 * @date 2022/4/13 3:58 PM
 */
@QuarkusMain
public class SsoServerMain {
    public static void main(String[] args) {
        Quarkus.run(args);
        System.out.println("\n------ Sa-Token-SSO 统一认证中心启动成功 ");
    }
}
