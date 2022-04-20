package io.quarkiverse.satoken.context.dubbo.it;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class SatokenContextDubboResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/satoken-context-dubbo")
                .then()
                .statusCode(200)
                .body(is("Hello satoken-context-dubbo"));
    }
}
