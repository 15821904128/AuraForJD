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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JavaStreamingRedisOrderForJdtest {

    public static void main(String[] args) throws Exception {
        SparkConf sparkConf = new SparkConf().setMaster("local[3]").setAppName("Java SparkStreaming Kafka JDTEST");
        JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, Durations.seconds(10));

        //配置kafka
        Map<String, String> kafkaParams = new HashMap<>();
        kafkaParams.put("metadata.broker.list", "master:9092");
        kafkaParams.put("serializer.class", "kafka.serializer.StringEncoder");

        // kafka主题
        Set<String> topics = new HashSet<>();
        topics.add("t_order_topic_tmp");

        JavaPairInputDStream<String, String> lines =
                KafkaUtils.createDirectStream(ssc,
                        String.class, String.class,
                        StringDecoder.class, StringDecoder.class,
                        kafkaParams,
                        topics);

        JavaPairDStream<String, Double> lineMap = lines.mapToPair(
                // key为”buy+<age>”
                (item) -> new Tuple2<>(item._1, new Double(item._2().split(",")[1]))
        );
        JavaPairDStream<String, Double> lineMap2 = lineMap.reduceByKey(
                // value累计
                (x, y) ->  x + y
        );
        lineMap2.print();
        lineMap2.foreachRDD(
                rdd -> {
                    rdd.foreach(
                            x -> {
                                Class.forName("com.mysql.jdbc.Driver");
                                Connection conn = DriverManager.getConnection(
                                        "jdbc:mysql://localhost:3306/jdtest", "root", "newpass");
                                Statement statement = conn.createStatement();
                                String sql = "select age from t_user where uid = " + x._1();
                                ResultSet rs = statement.executeQuery(sql);
                                while (rs.next()) {
                                    String age = rs.getString("age");
                                    Jedis jedis = JavaRedisClient.get().getResource();
                                    System.out.println("input redis " + age + "," + x._2());
                                    jedis.hincrBy("order", age, Math.round(x._2()));
                                    jedis.close();
                                }
                                rs.close();
                                statement.close();
                                conn.close();
                            }
                    );
                }
        );
        ssc.start();
        ssc.awaitTermination();
    }
}
