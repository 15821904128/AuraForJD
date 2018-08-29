
create table if not exists t_user_tmp(
uid STRING,
age STRING,
sex STRING,
active_date STRING,
limit_count DOUBLE
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';