
insert overwrite table t_user partition(sex='01', age='20') select uid, active_date, limit_count from t_user_tmp where sex='01' and age='20';
insert overwrite table t_user partition(sex='02', age='20') select uid, active_date, limit_count from t_user_tmp where sex='02' and age='20';

insert overwrite table t_user partition(sex='01', age='25') select uid, active_date, limit_count from t_user_tmp where sex='01' and age='25';
insert overwrite table t_user partition(sex='02', age='25') select uid, active_date, limit_count from t_user_tmp where sex='02' and age='25';

insert overwrite table t_user partition(sex='01', age='30') select uid, active_date, limit_count from t_user_tmp where sex='01' and age='30';
insert overwrite table t_user partition(sex='02', age='30') select uid, active_date, limit_count from t_user_tmp where sex='02' and age='30';

insert overwrite table t_user partition(sex='01', age='35') select uid, active_date, limit_count from t_user_tmp where sex='01' and age='35';
insert overwrite table t_user partition(sex='02', age='35') select uid, active_date, limit_count from t_user_tmp where sex='02' and age='35';

insert overwrite table t_user partition(sex='01', age='40') select uid, active_date, limit_count from t_user_tmp where sex='01' and age='40';
insert overwrite table t_user partition(sex='02', age='40') select uid, active_date, limit_count from t_user_tmp where sex='02' and age='40';

insert overwrite table t_user partition(sex='01', age='45') select uid, active_date, limit_count from t_user_tmp where sex='01' and age='45';
insert overwrite table t_user partition(sex='02', age='45') select uid, active_date, limit_count from t_user_tmp where sex='02' and age='45';

insert overwrite table t_user partition(sex='01', age='50') select uid, active_date, limit_count from t_user_tmp where sex='01' and age='50';
insert overwrite table t_user partition(sex='02', age='50') select uid, active_date, limit_count from t_user_tmp where sex='02' and age='50';
