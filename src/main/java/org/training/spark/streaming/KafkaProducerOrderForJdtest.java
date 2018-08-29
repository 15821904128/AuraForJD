package org.training.spark.streaming;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class KafkaProducerOrderForJdtest {

    //  t_order主题
    public void orderProduceMessage() {

        try {
//            // 从mysql中获取t_user数据
//            SparkSession session = SparkSession.builder().appName("dataframe").master("local[3]").getOrCreate();
//            Map<String,String> options = new HashMap<>();
//            options.put("url", "jdbc:mysql://localhost:3306/jdtest");
//            options.put("user","root");
//            options.put("password","newpass");
//            options.put("dbtable","t_user");
//            options.put("driver","com.mysql.jdbc.Driver");
//            Dataset<Row> userDf = session.read().format("jdbc").options(options).load();
//            userDf.show(10);
//
//            // 将t_order数据依次写入kafka中的 t_order主题
//            // ratings.dat显式注入Schema
//            String ratSchemaString = "uid buy_time price qty cate_id discount";
//            List<StructField> ratFields = new ArrayList<>();
//            for (String fieldName : ratSchemaString.split(" ")) {
//                StructField field = DataTypes.createStructField(fieldName, DataTypes.StringType, true);
//                ratFields.add(field);
//            }
//            StructType schema = DataTypes.createStructType(ratFields);
//            String pathname = "hdfs://master:9000/jdtest/order";
//            JavaRDD<Row> orderRdd = sc.textFile(pathname).filter(line -> !line.contains("uid")).map(line -> RowFactory.create(line.split(",")));
//            Dataset<Row> orderDf = session.createDataFrame(orderRdd, schema);
//            orderDf.show(10);
//            Dataset<Row> joinDf = orderDf.join(userDf, userDf.col("uid").equalTo(orderDf.col("uid")), "leftouter");
//            joinDf.show(10);
//            joinDf.foreachPartition(
//                    iterator -> {
//                        System.out.println("iterator-----------------");
//                        System.out.println(iterator);
//
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
//                        while (iterator.hasNext()) {
//                            Row row = iterator.next();
//                            System.out.println("Loop-----------------");
//                            System.out.println(row);
//                            String key = row.getAs("uid");
//                            String age = row.getAs("age");
//                            Double price = new Double(row.getAs("price"));
//                            int qty = new Integer(row.getAs("qty"));
//                            Double discount = new Double(row.getAs("discount"));
//                            Double value = price * qty - discount;
//                            System.out.println(key + "," + age + "," + value);
//                            // uid为key，uid+”,”+age + “，” + vaule为value
//                            producer.send(new ProducerRecord<String, String>("t_click1", key, key + "," + age + "," + value));
//                            // 每条数据写入间隔为 10毫秒
//                            Thread.sleep(10);
//                        }
//                        producer.close();
//                    }
//            );
            Properties props = getConfig();
            Producer<String, String> producer = new KafkaProducer<String, String>(props);

            // 将t_order数据依次写入kafka中的 t_order主题
            String pathname = "/home/bigdata/jdd_dataset/t_order.csv";
            File filename = new File(pathname);
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = br.readLine();
            System.out.println(line);
            String[] strArray = null;
            String key = "";
            double price = 0;
            int qty = 0;
            double discount = 0;
            double value = 0;
            while (line != null) {
                line = br.readLine();
                System.out.println(line);
                strArray = line.split(",");
                if (strArray != null && strArray.length > 0) {
                    key = strArray[0];
                }
                if (strArray != null && strArray.length >= 6) {
                    value = new Double(strArray[2]) * Integer.parseInt(strArray[3]) - new Double(strArray[5]);
                }
                // uid为key，uid+”,”+price为value
                producer.send(new ProducerRecord<String, String>("t_order_topic_tmp", key, key + "," + value));
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
        KafkaProducerOrderForJdtest jdtest = new KafkaProducerOrderForJdtest();
        jdtest.orderProduceMessage();
    }
}
