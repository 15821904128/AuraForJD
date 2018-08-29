select
to_date(t_loan.loan_time),
t_user.sex,
sum(t_loan.loan_amount) as total_loan_amount
from t_loan join t_user on t_loan.uid = t_user.uid
group by to_date(t_loan.loan_time), t_user.sex；