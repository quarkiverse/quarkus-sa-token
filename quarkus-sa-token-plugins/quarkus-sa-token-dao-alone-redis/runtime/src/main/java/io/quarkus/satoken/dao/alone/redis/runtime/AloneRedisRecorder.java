package io.quarkus.satoken.dao.alone.redis.runtime;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.microprofile.config.ConfigProvider;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ConfigSupport;

import com.fasterxml.jackson.databind.MapperFeature;

import io.quarkiverse.satoken.dao.redis.jackson.core.SaTokenDaoRedisJackson;
import io.quarkus.arc.Arc;
import io.quarkus.runtime.annotations.Recorder;

/**
 * AloneRedisRecorder
 *
 * @author nayan
 * @date 2022/10/19 14:38
 */
@Recorder
public class AloneRedisRecorder {

    /**
     * 配置信息的前缀
     */
    public static final String ALONE_PREFIX = "sa-token.alone-redis.redisson.";

    public void initAloneRedis() throws IOException {
        RedissonClient redissonClient = getSaTokenAloneRedissonClient();
        Arc.container().instance(SaTokenDaoRedisJackson.class).get().init(redissonClient);

    }

    private RedissonClient getSaTokenAloneRedissonClient() throws IOException {
        Optional<String> configFile = ConfigProvider.getConfig().getOptionalValue(ALONE_PREFIX + "file", String.class);
        InputStream configStream = null;
        if (configFile.isPresent()) {
            configStream = this.getClass().getResourceAsStream((String) configFile.get());
        }

        String configStr;
        if (configStream != null) {
            byte[] array = new byte[configStream.available()];
            configStream.read(array);
            configStr = new String(array, StandardCharsets.UTF_8);
            try {
                configStream.close();
            } catch (Exception e) {

            }
        } else {
            Stream<String> s = StreamSupport.stream(ConfigProvider.getConfig().getPropertyNames().spliterator(), false);
            String yaml = toYaml(ALONE_PREFIX, s.sorted().collect(Collectors.toList()), prop -> {
                return ConfigProvider.getConfig().getValue(prop, String.class);
            }, false);
            configStr = yaml;
        }

        ConfigSupport support = new ConfigSupport() {
            {
                this.yamlMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
            }
        };
        Config config = support.fromYAML(configStr, Config.class);
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }

    public static String toYaml(String suffix, Iterable<String> propertyNames, Function<String, String> resolver,
            boolean caseSensitive) {
        Map<String, Object> map = new HashMap<>();

        for (String propertyName : propertyNames) {
            if (!propertyName.startsWith(suffix)) {
                continue;
            }

            List<String> pps = Arrays.asList(propertyName.replace(suffix, "").split("\\."));
            String value = resolver.apply(propertyName);
            String name = convertKey(pps.get(0), caseSensitive);
            if (pps.size() == 2) {
                Map<String, Object> m = (Map<String, Object>) map.computeIfAbsent(name, k -> new HashMap<String, Object>());
                String subName = convertKey(pps.get(1), caseSensitive);
                m.put(subName, value);
            } else {
                map.put(name, value);
            }
        }

        StringBuilder yaml = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof Map) {
                yaml.append(entry.getKey()).append(":").append("\n");

                Map<String, Object> m = (Map) entry.getValue();
                for (Map.Entry<String, Object> subEntry : m.entrySet()) {
                    yaml.append("  ").append(subEntry.getKey()).append(": ");
                    addValue(yaml, subEntry);
                    yaml.append("\n");
                }
            } else {
                yaml.append(entry.getKey()).append(": ");
                addValue(yaml, entry);
                yaml.append("\n");
            }
        }
        return yaml.toString();
    }

    private static String convertKey(String key, boolean caseSensitive) {
        if (!caseSensitive) {
            return key.replace("-", "");
        }

        String[] parts = key.split("-");
        StringBuilder builder = new StringBuilder();
        builder.append(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            builder.append(parts[i].substring(0, 1).toUpperCase())
                    .append(parts[i].substring(1));
        }
        return builder.toString();
    }

    private static final Set<String> LIST_NODES = new HashSet<>(
            Arrays.asList("node-addresses", "nodeaddresses", "slave-addresses", "slaveaddresses", "addresses"));

    private static void addValue(StringBuilder yaml, Map.Entry<String, Object> subEntry) {
        String value = (String) subEntry.getValue();
        if (value.contains(",") || LIST_NODES.contains(subEntry.getKey())) {
            for (String part : value.split(",")) {
                yaml.append("\n  ").append("- \"").append(part.trim()).append("\"");
            }
            return;
        }

        if ("codec".equals(subEntry.getKey())
                || "load-balancer".equals(subEntry.getKey())) {
            value = "!<" + value + "> {}";
        } else {
            try {
                Long.parseLong(value);
            } catch (NumberFormatException e) {
                if (!Boolean.parseBoolean(value)
                        && !"null".equals(value)) {
                    value = "\"" + value + "\"";
                }
            }
        }

        yaml.append(value);
    }
}
