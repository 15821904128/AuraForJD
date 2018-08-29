package org.training.spark.streaming;

/**
 * Created by 16081123 on 2018/7/21.
 */
public class KafkaRedisConfig {
    public static String REDIS_SERVER = "localhost";
    public static int REDIS_PORT = 6379;

    public static String KAFKA_SERVER = "localhost";
    public static String KAFKA_ADDR = KAFKA_SERVER + ":9092";
    public static String KAFKA_USER_TOPIC = "wolftest";
}
