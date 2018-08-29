package org.training.spark.sql;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class SparkSQLForJdtest {

    public static void main(String args[]) {
        String useJdtestSql = "use jdtest";
        String loanSql = "SELECT uid, loan_amount FROM t_loan";
        String orderSql = "SELECT uid, price, qty, discount FROM t_order";

        // warehouseLocation 指定管理数据库和表的默认位置
        SparkConf conf = new SparkConf();
        SparkSession sparkSession = SparkSession.builder().appName("Java Spark Hive JDTEST")
                .config(conf).enableHiveSupport().getOrCreate();

        // SQL查询的结果本身是DataFrames，支持所有正常的功能操作。
        sparkSession.sql(useJdtestSql);
        // 借款金额超过400且购买商品总价值超过借款总金额的用户ID
        Dataset<Row> loanAllDF = sparkSession.sql(loanSql).groupBy("uid").sum("loan_amount")
                                            .withColumnRenamed("sum(loan_amount)","total_loan_amount").cache();
        Dataset<Row> loanDF = loanAllDF.filter("total_loan_amount > 400");
        Dataset<Row> orderAllDF = sparkSession.sql(orderSql).cache();
        Dataset<Row> orderDF = orderAllDF.selectExpr("uid", "(price * qty - discount) as order_amount")
                                            .groupBy("uid").sum("order_amount")
                                            .withColumnRenamed("sum(order_amount)","total_order_amount");

        Dataset<Row> resultDF = orderDF.join(loanDF, loanDF.col("uid").equalTo(orderDF.col("uid")), "inner")
                                            .filter("total_order_amount > total_loan_amount")
                                            .select(loanDF.col("uid"));
        resultDF.show();

        // 从不买打折产品且不借款的用户ID
        Dataset<Row> orderNodisDF = orderAllDF.filter("discount = 0.0")
                                        .join(loanAllDF, orderAllDF.col("uid").equalTo(loanAllDF.col("uid")), "leftouter")
                                        .filter("total_loan_amount is null or total_loan_amount = 0.0")
                                        .select(orderAllDF.col("uid"));
        orderNodisDF.show();
        sparkSession.close();

    }
}
