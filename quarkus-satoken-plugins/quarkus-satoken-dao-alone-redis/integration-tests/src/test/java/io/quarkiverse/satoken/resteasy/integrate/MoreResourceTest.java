package io.quarkiverse.satoken.resteasy.integrate;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkiverse.satoken.resteasy.AbstractRequestTest;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
//@QuarkusTestResource(RedisResource.class)
public class MoreResourceTest extends AbstractRequestTest {

    // 基础API测试
    @Test
    public void testApi() {
        request("/more/getInfo?name=zhang", Map.of("div", "val"), res -> {
            Assertions.assertEquals(res.getData(), true);
        }).statusCode(200);

    }

    // Http Basic 认证
    @Test
    public void testBasic() throws Exception {

        // ---------------- 认证不通过
        request("/more/basicAuth", res -> {
            Assertions.assertEquals(res.getCode(), 903);
        }).statusCode(401).header("WWW-Authenticate", "Basic Realm=Sa-Token");

        // ---------------- 认证通过
        request("/more/basicAuth", Map.of("Authorization", "Basic c2E6MTIzNDU2"), res -> {
            Assertions.assertEquals(res.getCode(), 200);
        }).statusCode(200);
    }

}
