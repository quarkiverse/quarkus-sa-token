package io.quarkiverse.satoken.core.config;

import java.io.Serializable;
import java.util.Optional;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

/**
 * Sa-Token 配置类 Model
 * <p>
 * 你可以通过yml、properties、java代码等形式配置本类参数，具体请查阅官方文档: http://sa-token.dev33.cn/
 *
 * @author nayan
 * @date 2022/4/6 6:27 PM
 */
@ConfigRoot(prefix = "sa-token", name = "", phase = ConfigPhase.RUN_TIME)
public class SaTokenConfigForQuarkus implements Serializable {

    public static final long serialVersionUID = -6541180061782004705L;

    /**
     * token名称 (同时也是cookie名称)
     */
    @ConfigItem(defaultValue = "satoken")
    public String tokenName;

    /**
     * token的长久有效期(单位:秒) 默认30天, -1代表永久
     */
    @ConfigItem(defaultValue = "2592000")
    public long timeout;

    /**
     * token临时有效期 [指定时间内无操作就视为token过期] (单位: 秒), 默认-1 代表不限制
     * (例如可以设置为1800代表30分钟内无操作就过期)
     */
    @ConfigItem(defaultValue = "-1")
    public long activityTimeout;

    /**
     * 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录)
     */
    @ConfigItem(defaultValue = "true")
    public Boolean isConcurrent;

    /**
     * 在多人登录同一账号时，是否共用一个token (为true时所有登录共用一个token, 为false时每次登录新建一个token)
     */
    @ConfigItem(defaultValue = "true")
    public Boolean isShare;

    /**
     * 同一账号最大登录数量，-1代表不限 （只有在 isConcurrent=true, isShare=false 时此配置才有效）
     */
    @ConfigItem(defaultValue = "12")
    public int maxLoginCount;

    /**
     * 是否尝试从请求体里读取token
     */
    @ConfigItem(defaultValue = "true")
    public Boolean isReadBody;

    /**
     * 是否尝试从header里读取token
     */
    @ConfigItem(defaultValue = "true")
    public Boolean isReadHeader;

    /**
     * 是否尝试从cookie里读取token
     */
    @ConfigItem(defaultValue = "true")
    public Boolean isReadCookie;

    /**
     * token风格(默认可取值：uuid、simple-uuid、random-32、random-64、random-128、tik)
     */
    @ConfigItem(defaultValue = "uuid")
    public String tokenStyle;

    /**
     * 默认dao层实现类中，每次清理过期数据间隔的时间 (单位: 秒) ，默认值30秒，设置为-1代表不启动定时清理
     */
    @ConfigItem(defaultValue = "30")
    public int dataRefreshPeriod;

    /**
     * 获取[token专属session]时是否必须登录 (如果配置为true，会在每次获取[token-session]时校验是否登录)
     */
    @ConfigItem(defaultValue = "true")
    public Boolean tokenSessionCheckLogin;

    /**
     * 是否打开自动续签 (如果此值为true, 框架会在每次直接或间接调用getLoginId()时进行一次过期检查与续签操作)
     */
    @ConfigItem(defaultValue = "true")
    public Boolean autoRenew;

    /**
     * token前缀, 格式样例(satoken: Bearer xxxx-xxxx-xxxx-xxxx)
     */
    public Optional<String> tokenPrefix;

    /**
     * 是否在初始化配置时打印版本字符画
     */
    @ConfigItem(defaultValue = "true")
    public Boolean isPrint;

    /**
     * 是否打印操作日志
     */
    @ConfigItem(defaultValue = "false")
    public Boolean isLog;

    /**
     * jwt秘钥 (只有集成 jwt 模块时此参数才会生效)
     */
    public Optional<String> jwtSecretKey;

    /**
     * Id-Token的有效期 (单位: 秒),默认1天
     */
    @ConfigItem(defaultValue = "86400")
    public long idTokenTimeout;

    /**
     * Http Basic 认证的账号和密码
     */
    public Optional<String> basic;

    /**
     * 配置当前项目的网络访问地址
     */
    public Optional<String> currDomain;

    /**
     * 是否校验Id-Token（部分rpc插件有效）
     */
    @ConfigItem(defaultValue = "false")
    public Boolean checkIdToken;

}
