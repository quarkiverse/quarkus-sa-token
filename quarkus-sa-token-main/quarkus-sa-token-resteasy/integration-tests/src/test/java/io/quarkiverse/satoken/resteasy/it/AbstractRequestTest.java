package io.quarkiverse.satoken.resteasy.it;

import static io.restassured.RestAssured.given;

import java.util.Map;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.util.SaResult;
import io.quarkiverse.satoken.resteasy.it.utils.SoMap;
import io.restassured.response.ValidatableResponse;
import jakarta.ws.rs.core.MediaType;

/**
 * AbstractRequestTest
 *
 * @author nayan
 * @date 2022/10/8 14:12
 */
public class AbstractRequestTest {

    protected ValidatableResponse request(String path, SaResultMatcher matcher) {
        return given()
                .when()
                .accept(MediaType.APPLICATION_JSON)
                .post(path)
                .then()
                .body(matcher);

    }

    protected ValidatableResponse request(String path, Map<String, String> headers, SaResultMatcher matcher) {
        return given()
                .when()
                .headers(headers)
                .accept(MediaType.APPLICATION_JSON)
                .post(path)
                .then()
                .body(matcher);

    }

    protected ValidatableResponse request(String path, Map<String, String> headers, Map<String, String> cookies,
            SaResultMatcher matcher) {
        return given()
                .when()
                .headers(headers)
                .cookies(cookies)
                .accept(MediaType.APPLICATION_JSON)
                .post(path)
                .then()
                .body(matcher);

    }

    public interface SaMapMatcher extends Matcher {

        void match(SoMap result);

        @Override
        default boolean matches(Object actual) {
            SoMap so = SoMap.getSoMap().setJsonString(
                    actual.toString());
            match(so);
            return true;
        }

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

    public interface SaResultMatcher extends Matcher {

        void match(SaResult result);

        @Override
        default boolean matches(Object actual) {
            // 转 Map
            Map<String, Object> map = SaManager.getSaJsonTemplate().parseJsonToMap(actual.toString());

            // 转 SaResult 对象
            match(new SaResult().setMap(map));
            return true;
        }

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
