
create table if not exists t_loan(
uid STRING,
loan_time STRING,
loan_amount DOUBLE,
plannum INT
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';