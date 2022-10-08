package io.quarkiverse.satoken.dao.redis.jackson.it.utils;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.exception.DisableServiceException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.SaSessionCustomUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.temp.SaTempUtil;
import cn.dev33.satoken.util.SaTokenConsts;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class BasicTest {

    // 持久化Bean
    SaTokenDao dao = SaManager.getSaTokenDao();

    // 开始
    @BeforeAll
    public static void beforeClass() {
        System.out.println("\n\n------------------------ 基础测试 star ...");
    }

    // 结束
    @AfterAll
    public static void afterClass() {
        System.out.println("\n\n------------------------ 基础测试 end ... \n");
    }

    // 测试：登录
    @Test
    public void doLogin() {
        // 登录
        StpUtil.login(10001);
        String token = StpUtil.getTokenValue();

        // API 验证
        Assertions.assertTrue(StpUtil.isLogin());
        Assertions.assertNotNull(token); // token不为null
        Assertions.assertEquals(StpUtil.getLoginIdAsLong(), 10001); // loginId=10001
        Assertions.assertEquals(StpUtil.getLoginDevice(), SaTokenConsts.DEFAULT_LOGIN_DEVICE); // 登录设备

        // db数据 验证
        // token存在
        Assertions.assertEquals(dao.get("satoken:login:token:" + token), "10001");
        // Session 存在
        SaSession session = dao.getSession("satoken:login:session:" + 10001);
        Assertions.assertNotNull(session);
        Assertions.assertEquals(session.getId(), "satoken:login:session:" + 10001);
        Assertions.assertTrue(session.getTokenSignList().size() >= 1);

    }

    // 测试：注销
    @Test
    public void logout() {
        // 登录
        StpUtil.login(10001);
        String token = StpUtil.getTokenValue();
        Assertions.assertEquals(dao.get("satoken:login:token:" + token), "10001");

        // 注销
        StpUtil.logout();
        // token 应该被清除
        Assertions.assertNull(StpUtil.getTokenValue());
        Assertions.assertFalse(StpUtil.isLogin());
        Assertions.assertNull(dao.get("satoken:login:token:" + token));
        // Session 应该被清除
        SaSession session = dao.getSession("satoken:login:session:" + 10001);
        Assertions.assertNull(session);
    }

    // 测试：Session会话
    @Test
    public void testSession() {
        StpUtil.login(10001);

        // API 应该可以获取 Session
        Assertions.assertNotNull(StpUtil.getSession(false));

        // db中应该存在 Session
        SaSession session = dao.getSession("satoken:login:session:" + 10001);
        Assertions.assertNotNull(session);

        // 存取值
        session.set("name", "zhang");
        session.set("age", "18");
        Assertions.assertEquals(session.get("name"), "zhang");
        Assertions.assertEquals(session.getInt("age"), 18);
        Assertions.assertEquals((int) session.getModel("age", int.class), 18);
        Assertions.assertEquals((int) session.get("age", 20), 18);
        Assertions.assertEquals((int) session.get("name2", 20), 20);
        Assertions.assertEquals((int) session.get("name2", () -> 30), 30);
        session.clear();
        Assertions.assertEquals(session.get("name"), null);
    }

    // 测试：权限认证
    @Test
    public void testCheckPermission() {
        StpUtil.login(10001);

        // 权限认证
        Assertions.assertTrue(StpUtil.hasPermission("user-add"));
        Assertions.assertTrue(StpUtil.hasPermission("user-list"));
        Assertions.assertTrue(StpUtil.hasPermission("user"));
        Assertions.assertTrue(StpUtil.hasPermission("art-add"));
        Assertions.assertFalse(StpUtil.hasPermission("get-user"));
        // and
        Assertions.assertTrue(StpUtil.hasPermissionAnd("art-add", "art-get"));
        Assertions.assertFalse(StpUtil.hasPermissionAnd("art-add", "comment-add"));
        // or
        Assertions.assertTrue(StpUtil.hasPermissionOr("art-add", "comment-add"));
        Assertions.assertFalse(StpUtil.hasPermissionOr("comment-add", "comment-delete"));
    }

    // 测试：角色认证
    @Test
    public void testCheckRole() {
        StpUtil.login(10001);

        // 角色认证
        Assertions.assertTrue(StpUtil.hasRole("admin"));
        Assertions.assertFalse(StpUtil.hasRole("teacher"));
        // and
        Assertions.assertTrue(StpUtil.hasRoleAnd("admin", "super-admin"));
        Assertions.assertFalse(StpUtil.hasRoleAnd("admin", "ceo"));
        // or
        Assertions.assertTrue(StpUtil.hasRoleOr("admin", "ceo"));
        Assertions.assertFalse(StpUtil.hasRoleOr("ceo", "cto"));
    }

    // 测试：根据token强制注销
    @Test
    public void testLogoutByToken() {

        // 先登录上
        StpUtil.login(10001);
        Assertions.assertTrue(StpUtil.isLogin());
        String token = StpUtil.getTokenValue();

        // 根据token注销
        StpUtil.logoutByTokenValue(token);
        Assertions.assertFalse(StpUtil.isLogin());

        // token 应该被清除
        Assertions.assertNull(dao.get("satoken:login:token:" + token));
        // Session 应该被清除
        SaSession session = dao.getSession("satoken:login:session:" + 10001);
        Assertions.assertNull(session);

        // 场景值应该是token无效
        try {
            StpUtil.checkLogin();
        } catch (NotLoginException e) {
            Assertions.assertEquals(e.getType(), NotLoginException.INVALID_TOKEN);
        }
    }

    // 测试：根据账号id强制注销
    @Test
    public void testLogoutByLoginId() {

        // 先登录上
        StpUtil.login(10001);
        Assertions.assertTrue(StpUtil.isLogin());
        String token = StpUtil.getTokenValue();

        // 根据账号id注销
        StpUtil.logout(10001);
        Assertions.assertFalse(StpUtil.isLogin());

        // token 应该被清除
        Assertions.assertNull(dao.get("satoken:login:token:" + token));
        // Session 应该被清除
        SaSession session = dao.getSession("satoken:login:session:" + 10001);
        Assertions.assertNull(session);

        // 场景值应该是token无效
        try {
            StpUtil.checkLogin();
        } catch (NotLoginException e) {
            Assertions.assertEquals(e.getType(), NotLoginException.INVALID_TOKEN);
        }
    }

    // 测试Token-Session
    @Test
    public void testTokenSession() {

        // 先登录上
        StpUtil.login(10001);
        String token = StpUtil.getTokenValue();

        // 刚开始不存在
        Assertions.assertNull(StpUtil.stpLogic.getTokenSession(false));
        SaSession session = dao.getSession("satoken:login:token-session:" + token);
        Assertions.assertNull(session);

        // 调用一次就存在了
        StpUtil.getTokenSession();
        Assertions.assertNotNull(StpUtil.stpLogic.getTokenSession(false));
        SaSession session2 = dao.getSession("satoken:login:token-session:" + token);
        Assertions.assertNotNull(session2);
    }

    // 测试自定义Session
    @Test
    public void testCustomSession() {
        // 刚开始不存在
        Assertions.assertFalse(SaSessionCustomUtil.isExists("art-1"));
        SaSession session = dao.getSession("satoken:custom:session:" + "art-1");
        Assertions.assertNull(session);

        // 调用一下
        SaSessionCustomUtil.getSessionById("art-1");

        // 就存在了
        Assertions.assertTrue(SaSessionCustomUtil.isExists("art-1"));
        SaSession session2 = dao.getSession("satoken:custom:session:" + "art-1");
        Assertions.assertNotNull(session2);

        // 给删除掉
        SaSessionCustomUtil.deleteSessionById("art-1");

        // 就又不存在了
        Assertions.assertFalse(SaSessionCustomUtil.isExists("art-1"));
        SaSession session3 = dao.getSession("satoken:custom:session:" + "art-1");
        Assertions.assertNull(session3);
    }

    // 测试：根据账号id踢人
    @Test
    public void kickoutByLoginId() {

        // 踢人下线
        StpUtil.login(10001);
        String token = StpUtil.getTokenValue();
        StpUtil.kickout(10001);

        // token 应该被打标记
        Assertions.assertEquals(dao.get("satoken:login:token:" + token), NotLoginException.KICK_OUT);

        // 场景值应该是token已被踢下线
        try {
            StpUtil.checkLogin();
        } catch (NotLoginException e) {
            Assertions.assertEquals(e.getType(), NotLoginException.KICK_OUT);
        }
    }

    // 测试：账号封禁
    @Test()
    public void testDisable() {
        // 封号
        StpUtil.disable(10007, 200);
        Assertions.assertTrue(StpUtil.isDisable(10007));
        Assertions.assertEquals(dao.get("satoken:login:disable:login:" + 10007),
                String.valueOf(SaTokenConsts.DEFAULT_DISABLE_LEVEL));

        // 封号后检测一下 (会抛出 DisableLoginException 异常)
        Assertions.assertThrows(DisableServiceException.class, () -> StpUtil.checkDisable(10007));

        // 封号时间
        long disableTime = StpUtil.getDisableTime(10007);
        Assertions.assertTrue(disableTime <= 200 && disableTime >= 199);

        // 解封
        StpUtil.untieDisable(10007);
        Assertions.assertFalse(StpUtil.isDisable(10007));
        Assertions.assertEquals(dao.get("satoken:login:disable:login:" + 10007), null);
        Assertions.assertDoesNotThrow(() -> StpUtil.checkDisable(10007));
    }

    // 测试：分类封禁
    @Test
    public void testDisableService() {
        // 封掉评论功能
        StpUtil.disable(10008, "comment", 200);
        Assertions.assertTrue(StpUtil.isDisable(10008, "comment"));
        Assertions.assertEquals(dao.get("satoken:login:disable:comment:" + 10008),
                String.valueOf(SaTokenConsts.DEFAULT_DISABLE_LEVEL));
        Assertions.assertNull(dao.get("satoken:login:disable:login:" + 10008));

        // 封号后检测一下
        Assertions.assertThrows(DisableServiceException.class, () -> StpUtil.checkDisable(10008, "comment"));
        // 检查多个，有一个不通过就报异常
        Assertions.assertThrows(DisableServiceException.class, () -> StpUtil.checkDisable(10008, "comment", "login"));

        // 封号时间
        long disableTime = StpUtil.getDisableTime(10008, "comment");
        Assertions.assertTrue(disableTime <= 200 && disableTime >= 199);

        // 解封 (不加服务名不会成功)
        StpUtil.untieDisable(10008);
        Assertions.assertTrue(StpUtil.isDisable(10008, "comment"));
        Assertions.assertNotNull(dao.get("satoken:login:disable:comment:" + 10008));

        // 解封 (加服务名才会成功)
        StpUtil.untieDisable(10008, "comment");
        Assertions.assertFalse(StpUtil.isDisable(10008, "comment"));
        Assertions.assertEquals(dao.get("satoken:login:disable:comment:" + 10008), null);
        Assertions.assertDoesNotThrow(() -> StpUtil.checkDisable(10007, "comment"));
    }

    // 测试：阶梯封禁
    @Test
    public void testDisableLevel() {
        // 封禁等级5
        StpUtil.disableLevel(10009, 5, 200);
        Assertions.assertTrue(StpUtil.isDisableLevel(10009, 3));
        Assertions.assertTrue(StpUtil.isDisableLevel(10009, 5));
        // 未达到7级
        Assertions.assertFalse(StpUtil.isDisableLevel(10009, 7));
        // 账号未封禁
        Assertions.assertFalse(StpUtil.isDisableLevel(20009, 3));

        // dao中应该有值
        Assertions.assertEquals(dao.get("satoken:login:disable:login:" + 10009), String.valueOf(5));

        // 封号后检测一下
        Assertions.assertThrows(DisableServiceException.class, () -> StpUtil.checkDisableLevel(10009, 3));
        Assertions.assertThrows(DisableServiceException.class, () -> StpUtil.checkDisableLevel(10009, 5));
        // 未达到等级，不抛出异常
        Assertions.assertDoesNotThrow(() -> StpUtil.checkDisableLevel(10009, 7));
        // 账号未被封禁，不抛出异常
        Assertions.assertDoesNotThrow(() -> StpUtil.checkDisableLevel(20009, 3));

        // 封号等级
        Assertions.assertEquals(StpUtil.getDisableLevel(10009), 5);
        Assertions.assertEquals(StpUtil.getDisableLevel(20009), -2);

        // 解封
        StpUtil.untieDisable(10009);
        Assertions.assertFalse(StpUtil.isDisable(10009));
        Assertions.assertFalse(StpUtil.isDisableLevel(10009, 5));
        Assertions.assertNull(dao.get("satoken:login:disable:login:" + 10009));
    }

    // 测试：分类封禁 + 阶梯封禁
    @Test
    public void testDisableServiceLevel() {
        // 封禁服务 shop，等级5
        StpUtil.disableLevel(10010, "shop", 5, 200);
        Assertions.assertTrue(StpUtil.isDisableLevel(10010, "shop", 3));
        Assertions.assertTrue(StpUtil.isDisableLevel(10010, "shop", 5));
        // 未达到7级
        Assertions.assertFalse(StpUtil.isDisableLevel(10010, "shop", 7));
        // 账号未封禁
        Assertions.assertFalse(StpUtil.isDisableLevel(20010, "shop", 3));
        // 服务名不对
        Assertions.assertFalse(StpUtil.isDisableLevel(10010, "shop2", 5));

        // dao中应该有值
        Assertions.assertEquals(dao.get("satoken:login:disable:shop:" + 10010), String.valueOf(5));

        // 封号后检测一下
        Assertions.assertThrows(DisableServiceException.class, () -> StpUtil.checkDisableLevel(10010, "shop", 3));
        Assertions.assertThrows(DisableServiceException.class, () -> StpUtil.checkDisableLevel(10010, "shop", 5));
        // 未达到等级，不抛出异常
        Assertions.assertDoesNotThrow(() -> StpUtil.checkDisableLevel(10010, "shop", 7));
        // 账号未被封禁，不抛出异常
        Assertions.assertDoesNotThrow(() -> StpUtil.checkDisableLevel(20010, "shop", 3));

        // 封号等级
        Assertions.assertEquals(StpUtil.getDisableLevel(10010, "shop"), 5);
        Assertions.assertEquals(StpUtil.getDisableLevel(10010, "shop2"), -2);
        Assertions.assertEquals(StpUtil.getDisableLevel(20010, "shop"), -2);

        // 解封
        StpUtil.untieDisable(10010, "shop");
        Assertions.assertFalse(StpUtil.isDisable(10010, "shop"));
        Assertions.assertFalse(StpUtil.isDisableLevel(10010, "shop", 5));
        Assertions.assertNull(dao.get("satoken:login:disable:shop:" + 10010));
    }

    // 测试：身份切换
    @Test
    public void testSwitch() {
        // 登录
        StpUtil.login(10001);
        Assertions.assertFalse(StpUtil.isSwitch());
        Assertions.assertEquals(StpUtil.getLoginIdAsLong(), 10001);

        // 开始身份切换
        StpUtil.switchTo(10044);
        Assertions.assertTrue(StpUtil.isSwitch());
        Assertions.assertEquals(StpUtil.getLoginIdAsLong(), 10044);

        // 结束切换
        StpUtil.endSwitch();
        Assertions.assertFalse(StpUtil.isSwitch());
        Assertions.assertEquals(StpUtil.getLoginIdAsLong(), 10001);
    }

    // 测试：会话管理
    @Test
    public void testSearchTokenValue() {
        // 登录
        StpUtil.login(10001);
        StpUtil.login(10002);
        StpUtil.login(10003);
        StpUtil.login(10004);
        StpUtil.login(10005);

        // 查询
        List<String> list = StpUtil.searchTokenValue("", 0, 10, true);
        Assertions.assertTrue(list.size() >= 5);
    }

    // 测试：临时Token认证模块
    @Test
    public void testSaTemp() {
        // 生成token
        String token = SaTempUtil.createToken("group-1014", 200);
        Assertions.assertNotNull(token);

        // 解析token
        String value = SaTempUtil.parseToken(token, String.class);
        Assertions.assertEquals(value, "group-1014");
        Assertions.assertEquals(dao.getObject("satoken:temp-token:" + token), "group-1014");

        // 过期时间
        long timeout = SaTempUtil.getTimeout(token);
        Assertions.assertTrue(timeout > 195);

        // 回收token
        SaTempUtil.deleteToken(token);
        String value2 = SaTempUtil.parseToken(token, String.class);
        Assertions.assertEquals(value2, null);
        Assertions.assertEquals(dao.getObject("satoken:temp-token:" + token), null);
    }

    // 测试：二级认证
    @Test
    public void testSafe() {
        // 登录
        StpUtil.login(10001);
        Assertions.assertFalse(StpUtil.isSafe());

        // 开启二级认证
        StpUtil.openSafe(2);
        Assertions.assertTrue(StpUtil.isSafe());
        Assertions.assertTrue(StpUtil.getSafeTime() > 0);

        // 自然结束
        //    	Thread.sleep(2500);
        //    	Assertions.assertFalse(StpUtil.isSafe());

        // 手动结束
        //    	StpUtil.openSafe(2);
        StpUtil.closeSafe();
        Assertions.assertFalse(StpUtil.isSafe());
    }
}
