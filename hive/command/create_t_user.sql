
create table if not exists t_user(
uid STRING,
active_date STRING,
limit_count DOUBLE
)
PARTITIONED BY (sex STRING, age STRING)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';