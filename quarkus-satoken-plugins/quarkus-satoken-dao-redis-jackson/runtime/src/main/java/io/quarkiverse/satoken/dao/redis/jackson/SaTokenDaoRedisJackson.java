package io.quarkiverse.satoken.dao.redis.jackson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.strategy.SaStrategy;
import cn.dev33.satoken.util.SaFoxUtil;

/**
 * Sa-Token持久层接口 [Redis版] (使用 jackson 序列化方式)
 *
 * @author kong
 */
@ApplicationScoped
public class SaTokenDaoRedisJackson implements SaTokenDao {

    /**
     * ObjectMapper对象 (以public作用域暴露出此对象，方便开发者二次更改配置)
     */
    @Inject
    public ObjectMapper objectMapper;

    @Inject
    public RedissonClient redissonClient;

    /**
     * 标记：是否已初始化成功
     */
    public boolean isInit;

    @PostConstruct
    public void init() {
        // 指定相应的序列化方案
        // 通过反射获取Mapper对象, 增加一些配置, 增强兼容性
        try {

            // 重写Session生成策略
            SaStrategy.me.createSession = (sessionId) -> new SaSessionForJacksonCustomized(sessionId);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        // 开始初始化相关组件
        if (this.isInit == false) {
            this.isInit = true;
        }
    }

    /**
     * 获取Value，如无返空
     */
    @Override
    public String get(String key) {
        Object value = redissonClient.getBucket(key).get();
        return value != null ? value.toString() : null;
    }

    /**
     * 写入Value，并设定存活时间 (单位: 秒)
     */
    @Override
    public void set(String key, String value, long timeout) {
        if (timeout == 0 || timeout <= SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        RBucket<Object> bucket = redissonClient.getBucket(key);
        // 判断是否为永不过期
        if (timeout == SaTokenDao.NEVER_EXPIRE) {
            bucket.set(value);
        } else {
            bucket.set(value, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 修修改指定key-value键值对 (过期时间不变)
     */
    @Override
    public void update(String key, String value) {
        long expire = getTimeout(key);
        // -2 = 无此键
        if (expire == SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        this.set(key, value, expire);
    }

    /**
     * 删除Value
     */
    @Override
    public void delete(String key) {
        redissonClient.getBucket(key).delete();
    }

    /**
     * 获取Value的剩余存活时间 (单位: 秒)
     */
    @Override
    public long getTimeout(String key) {
        return redissonClient.getBucket(key).remainTimeToLive() / 1000;
    }

    /**
     * 修改Value的剩余存活时间 (单位: 秒)
     */
    @Override
    public void updateTimeout(String key, long timeout) {
        // 判断是否想要设置为永久
        if (timeout == SaTokenDao.NEVER_EXPIRE) {
            long expire = getTimeout(key);
            if (expire == SaTokenDao.NEVER_EXPIRE) {
                // 如果其已经被设置为永久，则不作任何处理
            } else {
                // 如果尚未被设置为永久，那么再次set一次
                this.set(key, this.get(key), timeout);
            }
            return;
        }
        redissonClient.getBucket(key).expire(timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取Object，如无返空
     */
    @Override
    public Object getObject(String key) {
        return redissonClient.getBucket(key).get();
    }

    /**
     * 写入Object，并设定存活时间 (单位: 秒)
     */
    @Override
    public void setObject(String key, Object object, long timeout) {
        if (timeout == 0 || timeout <= SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        // 判断是否为永不过期
        RBucket<Object> bucket = redissonClient.getBucket(key);
        if (timeout == SaTokenDao.NEVER_EXPIRE) {
            bucket.set(object);
        } else {
            bucket.set(object, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 更新Object (过期时间不变)
     */
    @Override
    public void updateObject(String key, Object object) {
        long expire = getObjectTimeout(key);
        // -2 = 无此键
        if (expire == SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        this.setObject(key, object, expire);
    }

    /**
     * 删除Object
     */
    @Override
    public void deleteObject(String key) {
        redissonClient.getBucket(key).delete();
    }

    /**
     * 获取Object的剩余存活时间 (单位: 秒)
     */
    @Override
    public long getObjectTimeout(String key) {
        return redissonClient.getBucket(key).remainTimeToLive() / 1000;
    }

    /**
     * 修改Object的剩余存活时间 (单位: 秒)
     */
    @Override
    public void updateObjectTimeout(String key, long timeout) {
        // 判断是否想要设置为永久
        if (timeout == SaTokenDao.NEVER_EXPIRE) {
            long expire = getObjectTimeout(key);
            if (expire == SaTokenDao.NEVER_EXPIRE) {
                // 如果其已经被设置为永久，则不作任何处理
            } else {
                // 如果尚未被设置为永久，那么再次set一次
                this.setObject(key, this.getObject(key), timeout);
            }
            return;
        }
        redissonClient.getBucket(key).expire(timeout, TimeUnit.SECONDS);
    }

    /**
     * 搜索数据
     */
    @Override
    public List<String> searchData(String prefix, String keyword, int start, int size) {
        List<String> keys = new ArrayList<>();
        redissonClient.getKeys().getKeysByPattern(prefix + "*" + keyword + "*").forEach(k -> keys.add(k));
        return SaFoxUtil.searchList(keys, start, size);
    }

}
