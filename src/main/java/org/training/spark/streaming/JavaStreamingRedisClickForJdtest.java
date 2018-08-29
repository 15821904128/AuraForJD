package org.training.spark.streaming;

import kafka.serializer.StringDecoder;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import redis.clients.jedis.Jedis;
import scala.Tuple2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JavaStreamingRedisClickForJdtest {

    public static void main(String[] args) throws Exception {

        SparkConf sparkConf = new SparkConf().setMaster("local[3]").setAppName("Java SparkStreaming Kafka JDTEST");
        JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, Durations.seconds(10));

        //配置kafka
        Map<String, String> kafkaParams = new HashMap<>();
        kafkaParams.put("metadata.broker.list", "localhost:9092");
        kafkaParams.put("serializer.class", "kafka.serializer.StringEncoder");

        // kafka主题
        Set<String> topics = new HashSet<>();
        topics.add("t_click_topic_tmp");

        JavaPairInputDStream<String, String> lines =
                KafkaUtils.createDirectStream(ssc,
                        String.class, String.class,
                        StringDecoder.class, StringDecoder.class,
                        kafkaParams,
                        topics);

        JavaPairDStream<String, Integer> lineMap = lines.mapToPair(
                // key为”click+<pid>"
                (item) -> new Tuple2<String, Integer>("click".concat(item._2().split(",")[1]), 1)
        );
        JavaPairDStream<String, Integer> lineMap2 = lineMap.reduceByKey(
                // value为累计的次数
                (x, y) ->  x + y
        );
        lineMap2.print();
        lineMap2.foreachRDD(
                lineRdds -> {
                    lineRdds.foreach(
                            lineRdd -> {
                                System.out.println("input redis " + lineRdd._1() + "," + lineRdd._2());
                                Jedis jedis = JavaRedisClient.get().getResource();
                                jedis.hincrBy("click", lineRdd._1(), lineRdd._2());
                                jedis.close();
                            }
                    );
                }
        );
        ssc.start();
        ssc.awaitTermination();
    }
}
