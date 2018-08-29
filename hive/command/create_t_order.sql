
create table if not exists t_order(
uid STRING,
buy_time STRING,
price DOUBLE,
qty INT,
cate_id STRING,
discount DOUBLE
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';