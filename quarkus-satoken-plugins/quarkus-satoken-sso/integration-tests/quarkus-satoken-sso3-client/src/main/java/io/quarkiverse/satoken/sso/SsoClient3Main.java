package io.quarkiverse.satoken.sso;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

/**
 * SsoClient3Main
 *
 * @author nayan
 * @date 2022/4/13 3:58 PM
 */
@QuarkusMain
public class SsoClient3Main {
    public static void main(String[] args) {
        Quarkus.run(args);
        System.out.println("\nSa-Token SSO模式三 Client端启动成功\nhttp://sa-sso-client3.com:9003");

    }
}
