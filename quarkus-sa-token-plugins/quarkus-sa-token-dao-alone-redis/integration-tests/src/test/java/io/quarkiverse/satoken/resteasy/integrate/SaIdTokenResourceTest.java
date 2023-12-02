package io.quarkiverse.satoken.resteasy.integrate;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.exception.IdTokenInvalidException;
import cn.dev33.satoken.id.SaIdUtil;
import io.quarkiverse.satoken.resteasy.AbstractRequestTest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.Header;
import io.restassured.response.ValidatableResponse;
import jakarta.ws.rs.core.MediaType;

/**
 * SaIdTokenResourceTest
 *
 * @author nayan
 * @date 2022/10/8 14:29
 */
@QuarkusTest
//@QuarkusTestResource(RedisResource.class)
public class SaIdTokenResourceTest extends AbstractRequestTest {

    // 获取信息
    @Test
    public void testGetInfo() {
        String token = SaIdUtil.getToken();
        // 加token，能调通
        request("/id/getInfo", token, res -> {
            Assertions.assertEquals(200, res.getCode());
        });
        // 不加token，不能调通
        request("/id/getInfo", "xxx", res -> {
            Assertions.assertEquals(902, res.getCode());
        });

        // 获取信息2
        token = SaIdUtil.getTokenNh();
        // 加token，能调通
        request("/id/getInfo2", token, res -> {
            Assertions.assertEquals(200, res.getCode());
        });
        // 不加token，不能调通
        request("/id/getInfo2", "xxx", res -> {
            Assertions.assertEquals(902, res.getCode());
        });
    }

    // 基础测试
    @Test
    public void testApi() {
        String token = SaIdUtil.getToken();

        // 刷新一下，会有变化
        SaIdUtil.refreshToken();
        String token2 = SaIdUtil.getToken();
        Assertions.assertNotEquals(token, token2);

        // 旧token，变为次级token
        String pastToken = SaIdUtil.getPastTokenNh();
        Assertions.assertEquals(token, pastToken);

        // dao中应该有值
        String daoToken = SaManager.getSaTokenDao().get("satoken:var:id-token");
        String daoToken2 = SaManager.getSaTokenDao().get("satoken:var:past-id-token");
        Assertions.assertEquals(token2, daoToken);
        Assertions.assertEquals(token, daoToken2);

        // 新旧都有效
        Assertions.assertTrue(SaIdUtil.isValid(token));
        Assertions.assertTrue(SaIdUtil.isValid(token2));

        // 空的不行
        Assertions.assertFalse(SaIdUtil.isValid(null));
        Assertions.assertFalse(SaIdUtil.isValid(""));

        // 不抛出异常
        Assertions.assertDoesNotThrow(() -> SaIdUtil.checkToken(token));
        Assertions.assertDoesNotThrow(() -> SaIdUtil.checkToken(token2));

        // 抛出异常
        Assertions.assertThrows(IdTokenInvalidException.class, () -> SaIdUtil.checkToken(null));
        Assertions.assertThrows(IdTokenInvalidException.class, () -> SaIdUtil.checkToken(""));
        Assertions.assertThrows(IdTokenInvalidException.class, () -> SaIdUtil.checkToken("aaa"));
    }

    protected ValidatableResponse request(String path, String token, SaResultMatcher matcher) {
        return given()
                .when()
                .header(new Header(SaIdUtil.ID_TOKEN, token))
                .accept(MediaType.APPLICATION_JSON)
                .post(path)
                .then()
                .body(matcher);

    }

}
