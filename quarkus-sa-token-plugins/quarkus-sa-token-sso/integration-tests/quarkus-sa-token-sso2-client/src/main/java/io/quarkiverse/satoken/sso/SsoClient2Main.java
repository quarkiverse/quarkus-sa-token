package io.quarkiverse.satoken.sso;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

/**
 * SsoClient2Main
 *
 * @author nayan
 * @date 2022/4/13 3:58 PM
 */
@QuarkusMain
public class SsoClient2Main {
    public static void main(String[] args) {
        Quarkus.run(args);
        System.out.println("\nSa-Token SSO模式二 Client端启动成功\nhttp://sa-sso-client2.com:9002");
    }
}
