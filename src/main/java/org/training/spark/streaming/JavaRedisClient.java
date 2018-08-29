package org.training.spark.streaming;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by 16081123 on 2018/7/21.
 */
public class JavaRedisClient {
    private static int MAX_IDLE = 200;
    private static int TIMEOUT = 10000;
    private static boolean TEST_ON_BORROW = true;

    private static JedisPool pool = null;

    public static JedisPoolConfig config() {
        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxTotal(1000);
        config.setMaxWaitMillis(1000);
        config.setMaxIdle(MAX_IDLE);
        config.setTestOnBorrow(TEST_ON_BORROW);
        return config;
    }

    public static JedisPool get() {
        if(pool == null) {
            synchronized (JavaRedisClient.class) {
                pool = new JedisPool(config(),
                        KafkaRedisConfig.REDIS_SERVER,
                        KafkaRedisConfig.REDIS_PORT,
                        TIMEOUT);
            }
        }
        return pool;
    }
}
