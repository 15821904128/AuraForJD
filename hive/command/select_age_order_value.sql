select
o.buy_time,
u.age,
sum((o.price * o.qty) - o.discount) as totalPrice
from t_order o join t_user u on o.uid = u.uid
group by o.buy_time, u.age
order by o.buy_time, u.age;