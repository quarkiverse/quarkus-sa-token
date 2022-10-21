package io.quarkiverse.satoken.oauth2;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

/**
 * Main
 *
 * @author nayan
 * @date 2022/4/13 3:58 PM
 */
@QuarkusMain
public class OAuth2Client {

    static String str = "-------------------- Sa-Token-OAuth2 示例 --------------------\n\n" +
            "首先在host文件 (C:\\windows\\system32\\drivers\\etc\\hosts) 添加以下内容: \r\n" +
            "	127.0.0.1 sa-oauth-server.com \r\n" +
            "	127.0.0.1 sa-oauth-client.com \r\n" +
            "再从浏览器访问：\r\n" +
            "	http://sa-oauth-client.com:8002";

    public static void main(String[] args) {
        Quarkus.run(args);
        System.out.println("\nSa-Token-OAuth Client端启动成功\n\n" + str);
    }
}
