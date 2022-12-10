package io.satoken.dao.alone.redis.deployment;

import java.io.IOException;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.satoken.dao.alone.redis.runtime.AloneRedisRecorder;

class QuarkusSatokenDaoAloneRedisProcessor {

    private static final String FEATURE = "satoken-dao-alone-redis";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    @Record(ExecutionTime.RUNTIME_INIT)
    public SaTokenAloneRedisBuildItem configure(AloneRedisRecorder recorder) throws IOException {
        recorder.initAloneRedis();
        return new SaTokenAloneRedisBuildItem();
    }
}
