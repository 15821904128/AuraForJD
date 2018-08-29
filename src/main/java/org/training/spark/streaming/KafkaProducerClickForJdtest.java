package org.training.spark.streaming;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class KafkaProducerClickForJdtest {

//    SparkConf conf = new SparkConf().setMaster("local[3]").setAppName("KafkaProducerForJdtest");
//    JavaSparkContext sc = new JavaSparkContext(conf);


    // t_click主题
    public void clickProduceMessage() {

        try {
//            // 将t_click数据依次写入kafka中的 t_click主题
//            String pathname = "hdfs://master:9000/jdtest/click";
//            JavaRDD<String> input = sc.textFile(pathname).repartition(5).filter(line -> line.contains("uid") ? false : true);
//            input.foreachPartition(
//                    lines -> {
//                        Properties props = new Properties();
//                        props.put("bootstrap.servers", "localhost:9092");
//                        props.put("acks", "all");
//                        props.put("retries", 0);
//                        props.put("batch.size", 16384);
//                        props.put("linger.ms", 1);
//                        props.put("buffer.memory", 33554432);
//                        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//                        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//
//                        Producer<String, String> producer = new KafkaProducer<String, String>(props);
//                        while (lines.hasNext()) {
//                            String line = lines.next();
//                            System.out.println(line);
//                            String key = "";
//                            String value = "";
//                            String[] strArray = line.split(",");
//                            if (strArray != null && strArray.length > 0) {
//                                key = strArray[0];
//                            }
//                            if (strArray != null && strArray.length >= 3) {
//                                value = strArray[1] + "," + strArray[2];
//                            }
//
//                            // uid为key，click_time+”,”+pid为value
//                            producer.send(new ProducerRecord<String, String>("t_click", key, value));
//                            // 每条数据写入间隔为 10毫秒
//                            Thread.sleep(10);
//                        }
//                        producer.close();
//                    }
//            );
            Properties props = getConfig();
            Producer<String, String> producer = new KafkaProducer<String, String>(props);

            // 将t_click数据依次写入kafka中的 t_click主题
            String pathname = "/home/bigdata/jdd_dataset/t_click.csv";
            File filename = new File(pathname);
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = br.readLine();
            System.out.println(line);
            String[] strArray = null;
            String key = "";
            String value = "";
            while (line != null) {
                line = br.readLine();
                System.out.println(line);
                strArray = line.split(",");
                if (strArray != null && strArray.length > 0) {
                    key = strArray[0];
                }
                if (strArray != null && strArray.length >= 3) {
                    value = strArray[1] + "," + strArray[2];
                }
                // uid为key，click_time+”,”+pid为value
                producer.send(new ProducerRecord<String, String>("t_click_topic_tmp", key, value));
                // 每条数据写入间隔为 10毫秒
                Thread.sleep(10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // config
    public Properties getConfig() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }

    public static void main(String[] args)
    {
        KafkaProducerClickForJdtest jdtest = new KafkaProducerClickForJdtest();
        jdtest.clickProduceMessage();
    }
}
