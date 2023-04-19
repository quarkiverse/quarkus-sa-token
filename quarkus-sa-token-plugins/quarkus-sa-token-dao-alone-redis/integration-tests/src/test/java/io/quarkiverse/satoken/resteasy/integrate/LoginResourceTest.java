package io.quarkiverse.satoken.resteasy.integrate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkiverse.satoken.resteasy.AbstractRequestTest;
import io.quarkiverse.satoken.resteasy.utils.SoMap;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.ValidatableResponse;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
//@QuarkusTestResource(RedisResource.class)
public class LoginResourceTest extends AbstractRequestTest {

    @Test
    void testLogin() {
        requestSoMap(
                "/acc/doLogin?name=zhang&pwd=123456",
                so -> {
                    String token = so.getString("token");
                    assertEquals(so.getInt("code"), 200);
                    assertNotNull(token);
                }).header("Set-Cookie", notNullValue());
    }

    @Test
    void testLogin2() {
        requestSoMap(
                "/acc/doLogin?name=zhang&pwd=123456",
                so -> {
                    Assertions.assertNotNull(so.getString("token"));
                    String token = so.getString("token");

                    // 是否登录
                    requestSoMap("/acc/isLogin?satoken=" + token, so2 -> {
                        Assertions.assertTrue(so2.getBoolean("data"));
                    });

                    // tokenInfo
                    requestSoMap("/acc/tokenInfo?satoken=" + token, so3 -> {
                        SoMap so4 = SoMap.getSoMap((Map<String, ?>) so3.get("data"));
                        Assertions.assertEquals(so4.getString("tokenName"), "satoken");
                        Assertions.assertEquals(so4.getString("tokenValue"), token);
                    });

                    // 注销
                    requestSoMap("/acc/logout?satoken=" + token, so4 -> {
                    });

                    // 是否登录
                    requestSoMap("/acc/isLogin?satoken=" + token, so5 -> {
                        Assertions.assertFalse(so5.getBoolean("data"));
                    });

                });
    }

    protected ValidatableResponse requestSoMap(String path, SaMapMatcher matcher) {
        return given()
                .when()
                .accept(MediaType.APPLICATION_JSON)
                .post(path)
                .then()
                .statusCode(200)
                .body(matcher);

    }

}
