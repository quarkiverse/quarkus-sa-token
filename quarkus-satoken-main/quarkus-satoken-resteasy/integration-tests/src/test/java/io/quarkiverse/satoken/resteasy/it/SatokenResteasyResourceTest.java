package io.quarkiverse.satoken.resteasy.it;

import io.quarkiverse.satoken.resteasy.it.utils.SoMap;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class SatokenResteasyResourceTest {

    @Test
    void doLogin() {
        request(
                "/acc/doLogin",
                Map.of("name", "zhang", "pwd", "123456"),
                actual -> {
                    SoMap so = SoMap.getSoMap().setJsonString(
                            actual.toString());
                    String token = so.getString("token");
                    assertEquals(so.getInt("code"), 200);
                    assertNotNull(token);
                    return true;
                }).header("Set-Cookie", notNullValue());
    }

    @Test
    void isLogin() {

        // 获取token
        request(
                "/acc/doLogin",
                Map.of("name", "zhang", "pwd", "123456"),
                actual -> {
                    SoMap so = SoMap.getSoMap().setJsonString(
                            actual.toString());
                    String token = so.getString("token");
                    assertEquals(so.getInt("code"), 200);
                    assertNotNull(token);

                    // 是否登录
                    isLogin(token,
                            actual2 -> {
                                SoMap so2 = SoMap.getSoMap().setJsonString(
                                        actual2.toString());
                                assertTrue(so2.getBoolean("data"));
                                return true;
                            });

                    // tokenInfo
                    request(
                            "/acc/tokenInfo",
                            Map.of("satoken", token),
                            actual3 -> {
                                SoMap so3 = SoMap.getSoMap().setJsonString(
                                        actual3.toString());
                                SoMap so4 = SoMap.getSoMap((Map<String, ?>) so3.get("data"));
                                assertEquals(so4.getString("tokenName"), "satoken");
                                assertEquals(so4.getString("tokenValue"), token);
                                return true;
                            });

                    // 注销
                    request(
                            "/acc/logout",
                            Map.of("satoken", token),
                            actual4 -> true);
                    // 是否登录
                    isLogin(token,
                            actual5 -> {
                                SoMap so5 = SoMap.getSoMap().setJsonString(
                                        actual5.toString());
                                assertFalse(so5.getBoolean("data"));
                                return true;
                            });

                    return true;
                });

    }

    private void isLogin(String token, SaTokenMatcher matcher) {
        request(
                "/acc/isLogin",
                Map.of("satoken", token),
                matcher);
    }

    private ValidatableResponse request(String path, Map<String, String> params, SaTokenMatcher matcher) {
        return given()
                .when()
                .queryParams(params)
                .accept(MediaType.APPLICATION_JSON)
                .post(path)
                .then()
                .statusCode(200)
                .body(matcher);

    }

    interface SaTokenMatcher extends Matcher {

        @Override
        default public void describeMismatch(Object actual, Description mismatchDescription) {

        }

        @Override
        default public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

        }

        @Override
        default public void describeTo(Description description) {

        }
    }
}
