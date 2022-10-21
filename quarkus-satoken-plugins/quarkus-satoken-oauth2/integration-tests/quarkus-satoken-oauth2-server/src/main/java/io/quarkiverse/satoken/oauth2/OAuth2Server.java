package io.quarkiverse.satoken.oauth2;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

/**
 * Main
 *
 * @author nayan
 * @date 2022/4/13 3:58 PM
 */
@QuarkusMain
public class OAuth2Server {
    public static void main(String[] args) {
        Quarkus.run(args);
    }
}
