package io.quarkiverse.satoken.resteasy.it.integrate;

import io.quarkiverse.satoken.resteasy.it.AbstractRequestTest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.QuarkusTestProfile;
import io.quarkus.test.junit.TestProfile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * SaAnnotationResteasyResourceTest
 *
 * @author nayan
 * @date 2022/10/8 14:11
 */
@QuarkusTest
@TestProfile(SaAnnotationResourceTest.AnnotationTestProfile.class)
public class SaAnnotationResourceTest extends AbstractRequestTest {

    // 校验通过的情况
    @Test
    public void testPassing() {
        // 登录拿到Token
        request("/at/login?id=10001", res -> {
            String satoken = res.get("token", String.class);
            Assertions.assertNotNull(satoken);

            // 登录校验，通过
            request("/at/checkLogin?satoken=" + satoken, res2 -> Assertions.assertEquals(res2.getCode(), 200));

            // 角色校验，通过
            request("/at/checkRole?satoken=" + satoken, res3 -> Assertions.assertEquals(res3.getCode(), 200));

            // 权限校验，通过
            request("/at/checkPermission?satoken=" + satoken, res4 -> Assertions.assertEquals(res4.getCode(), 200));

            // 权限校验or角色校验，通过
            request("/at/checkPermission2?satoken=" + satoken, res5 -> Assertions.assertEquals(res5.getCode(), 200));

            // 开启二级认证
            request("/at/openSafe?satoken=" + satoken, res6 -> Assertions.assertEquals(res6.getCode(), 200));

            // 校验二级认证，通过
            request("/at/checkSafe?satoken=" + satoken, res7 -> Assertions.assertEquals(res7.getCode(), 200));

            // 访问校验封禁的接口 ，通过
            request("/at/checkDisable?satoken=" + satoken, res9 -> Assertions.assertEquals(res9.getCode(), 200));
        });

    }

    // 校验不通过的情况
    @Test
    public void testNotPassing() {
        // 登录拿到Token

        request("/at/login?id=10002", res -> {
            String satoken = res.get("token", String.class);
            Assertions.assertNotNull(satoken);

            // 登录校验，不通过
            request("/at/checkLogin", res2 -> Assertions.assertEquals(res2.getCode(), 401));

            // 角色校验，不通过
            request("/at/checkRole?satoken=" + satoken, res3 -> Assertions.assertEquals(res3.getCode(), 402));

            // 权限校验，不通过
            request("/at/checkPermission?satoken=" + satoken, res4 -> Assertions.assertEquals(res4.getCode(), 403));

            // 权限校验or角色校验，不通过
            request("/at/checkPermission2?satoken=" + satoken, res5 -> Assertions.assertEquals(res5.getCode(), 403));

            // 校验二级认证，不通过
            request("/at/checkSafe?satoken=" + satoken, res7 -> Assertions.assertEquals(res7.getCode(), 901));

        });

        // -------- 登录拿到Token
        request("/at/login?id=10042", res -> {
            String satoken10042 = res.get("token", String.class);
            Assertions.assertNotNull(satoken10042);

            // 校验账号封禁 ，通过
            request("/at/disable?id=10042", res8 -> Assertions.assertEquals(res8.getCode(), 200));

            // 访问校验封禁的接口 ，不通过
            request("/at/checkDisable?satoken=" + satoken10042, res9 -> Assertions.assertEquals(res9.getCode(), 904));

            // 解封后就能访问了
            request("/at/untieDisable?id=10042", result -> {
            });
            request("/at/checkDisable?satoken=" + satoken10042, res10 -> Assertions.assertEquals(res10.getCode(), 200));

        });

    }

    // 测试忽略认证
    @Test
    public void testIgnore() {
        // 必须登录才能访问的
        request("/ig/show1", res1 -> Assertions.assertEquals(res1.getCode(), 401));

        // 不登录也可以访问的
        request("/ig/show2", res2 -> Assertions.assertEquals(res2.getCode(), 200));
    }



    public static class AnnotationTestProfile implements QuarkusTestProfile {
        public AnnotationTestProfile() {
        }

        public String getConfigProfile() {
            return "annotation";
        }
    }

}
