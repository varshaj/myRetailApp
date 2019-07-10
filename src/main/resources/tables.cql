CREATE KEYSPACE IF NOT EXISTS my_retail WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
CREATE TABLE IF NOT EXISTS my_retail.product_info (
product_id    bigint,
product_name  text,
current_price decimal,
currency_code text,
PRIMARY KEY ((product_id)));

INSERT INTO my_retail.product_info(product_id, product_name,current_price,currency_code)
VALUES (13860428, 'The Big Lebowski (Blu-ray) (Widescreen)',10.11,'USD');

INSERT INTO my_retail.product_info(product_id, product_name,current_price,currency_code)
VALUES (15117729, 'Robo Mini Movie',10.00,'USD');