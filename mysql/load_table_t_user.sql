
LOAD DATA LOCAL INFILE '/home/bigdata/jdd_dataset/t_user.csv' INTO TABLE jdtest.t_user
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES;
