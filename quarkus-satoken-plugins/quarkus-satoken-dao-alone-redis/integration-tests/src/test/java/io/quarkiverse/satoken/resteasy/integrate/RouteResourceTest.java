package io.quarkiverse.satoken.resteasy.integrate;

import java.util.Arrays;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.router.SaRouterStaff;
import io.quarkiverse.satoken.resteasy.AbstractRequestTest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.QuarkusTestProfile;
import io.quarkus.test.junit.TestProfile;

/**
 * RouteResourceTest
 *
 * @author nayan
 * @date 2022/10/9 16:26
 */
@QuarkusTest
@TestProfile(RouteResourceTest.RouteTestProfile.class)
//@QuarkusTestResource(RedisResource.class)
public class RouteResourceTest extends AbstractRequestTest {

    // 基础API测试
    @Test
    public void testApi() {
        // 是否命中
        SaRouterStaff staff = SaRouter.match(false);
        Assertions.assertFalse(staff.isHit());

        // 重置
        staff.reset();
        Assertions.assertTrue(staff.isHit());

        // lambda 形式
        SaRouterStaff staff2 = SaRouter.match(r -> false);
        Assertions.assertFalse(staff2.isHit());

        // 匹配
        Assertions.assertTrue(SaRouter.isMatch("/user/**", "/user/add"));
        Assertions.assertTrue(SaRouter.isMatch(new String[] { "/user/**", "/art/**", "/goods/**" }, "/art/delete"));
        Assertions.assertTrue(SaRouter.isMatch(Arrays.asList("/user/**", "/art/**", "/goods/**"), "/art/delete"));
        Assertions.assertTrue(SaRouter.isMatch(new String[] { "POST", "GET", "PUT" }, "GET"));

        // 不匹配的
        Assertions.assertTrue(SaRouter.notMatch(false).isHit());
        Assertions.assertTrue(SaRouter.notMatch(r -> false).isHit());
    }

    // 各种路由测试
    @Test
    public void testRouter() {
        // getInfo
        request("/rt/getInfo?name=zhang", res -> Assertions.assertEquals(res.getCode(), 201));

        // getInfo2
        request("/rt/getInfo2", res2 -> Assertions.assertEquals(res2.getCode(), 202));

        // getInfo3
        request("/rt/getInfo3", res3 -> Assertions.assertEquals(res3.getCode(), 203));

        // getInfo4
        request("/rt/getInfo4", res4 -> Assertions.assertEquals(res4.getCode(), 204));

        // getInfo5
        request("/rt/getInfo5", res5 -> Assertions.assertEquals(res5.getCode(), 205));

        // getInfo6
        request("/rt/getInfo6", res6 -> Assertions.assertEquals(res6.getCode(), 206));

        // getInfo7
        request("/rt/getInfo7", res7 -> Assertions.assertEquals(res7.getCode(), 200));

        // getInfo8
        request("/rt/getInfo8", res8 -> Assertions.assertEquals(res8.getCode(), 200));

        // getInfo9
        request("/rt/getInfo9", res9 -> Assertions.assertEquals(res9.getCode(), 209));

        // getInfo10
        request("/rt/getInfo10", res10 -> Assertions.assertEquals(res10.getCode(), 200));

        // getInfo11
        request("/rt/getInfo11", res11 -> Assertions.assertEquals(res11.getCode(), 211));

        // getInfo12
        request("/rt/getInfo12", res12 -> Assertions.assertEquals(res12.getCode(), 212));

        // getInfo13
        request("/rt/getInfo13", res13 -> Assertions.assertEquals(res13.getCode(), 213));

        // getInfo14
        request("/rt/getInfo14", res14 -> Assertions.assertEquals(res14.getCode(), 214));

        // getInfo15
        request("/rt/getInfo15", res15 -> Assertions.assertEquals(res15.getCode(), 215));

    }

    // 测试 getUrl()
    @Test
    public void testGetUrl() {
        // getInfo_101
        request("/rt/getInfo_101", res -> Assertions.assertTrue(res.getData().toString().endsWith("/rt/getInfo_101")));

        // getInfo_101，不包括后面的参数
        request("/rt/getInfo_101?id=1", res2 -> Assertions.assertTrue(res2.getData().toString().endsWith("/rt/getInfo_101")));

        // 自定义当前域名
        SaManager.getConfig().setCurrDomain("http://xxx.com");
        request("/rt/getInfo_101?id=1",
                res3 -> Assertions.assertEquals(res3.getData().toString(), "http://xxx.com/rt/getInfo_101"));
        SaManager.getConfig().setCurrDomain(null);
    }

    // 测试读取Cookie
    @Test
    public void testGetCookie() throws Exception {
        request("/rt/getInfo_102", Map.of(), Map.of("x-token", "token-111"), res -> {
            Assertions.assertEquals(res.getData(), "token-111");
        }).statusCode(200);

    }

    // 测试重定向
    @Test
    public void testRedirect() throws Exception {
        request("/rt/getInfo16", res -> {
        }).statusCode(302).header("Location", "/rt/getInfo3");
    }

    // 空接口
    @Test
    public void testGetInfo200() {
        request("/rt/getInfo_200", res -> Assertions.assertEquals(res.getCode(), 200));
        request("/rt/getInfo_201", res1 -> Assertions.assertEquals(res1.getCode(), 201));
        request("/rt/getInfo_202", res2 -> Assertions.assertEquals(res2.getCode(), 401));

        // 登录拿到Token
        request("/rt/login?id=10001", resLogin -> {
            String satoken = resLogin.get("token", String.class);
            request("/rt/getInfo_202?satoken=" + satoken, res3 -> Assertions.assertEquals(res3.getCode(), 200));
            ;
        });

    }

    // 测试转发
    @Test
    public void testForward() {
        request("/rt/getInfo_103", res -> Assertions.assertEquals(res.getCode(), 200));

    }

    public static class RouteTestProfile implements QuarkusTestProfile {
        public RouteTestProfile() {
        }

        public String getConfigProfile() {
            return "route";
        }
    }
}
