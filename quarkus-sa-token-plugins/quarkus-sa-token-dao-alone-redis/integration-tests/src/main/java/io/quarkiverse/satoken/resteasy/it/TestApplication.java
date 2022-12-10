package io.quarkiverse.satoken.resteasy.it;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class TestApplication {
    public static void main(String... args) {
        Quarkus.run(args);
    }
}
