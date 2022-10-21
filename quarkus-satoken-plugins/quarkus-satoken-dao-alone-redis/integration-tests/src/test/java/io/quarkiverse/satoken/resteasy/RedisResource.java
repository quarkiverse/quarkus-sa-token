package io.quarkiverse.satoken.resteasy;

import java.util.Collections;
import java.util.Map;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

/**
 * RedisResource
 *
 * @author nayan
 * @date 2022/10/20 10:42
 */
@Deprecated
public class RedisResource implements QuarkusTestResourceLifecycleManager {

    //准备Redis容器，使用withExposedPorts方法暴露端口
    public GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:6-alpine"))
            .withExposedPorts(6379);

    @Override
    public Map<String, String> start() {
        //        redis.setPortBindings(Arrays.asList("63791:6379"));
        redis.start();
        Integer port = redis.getMappedPort(6379);
        return Collections.singletonMap(
                "sa-token.alone-redis.redisson.single-server-config.address",
                "redis://127.0.0.1:" + port);
    }

    @Override
    public void stop() {
        redis.stop();
    }
}
