DELETE FROM customers_products;
DELETE FROM customer;
DELETE FROM product;


INSERT INTO customer (id, name) VALUES (1, 'Ivan');
INSERT INTO customer (id, name) VALUES (2, 'Masha');
INSERT INTO customer (id, name) VALUES (3, 'Petya');

INSERT INTO product (id, name, cost) VALUES (1, 'phone', 8);
INSERT INTO product (id, name, cost) VALUES (2, 'tv', 12);
INSERT INTO product (id, name, cost) VALUES (3, 'chips', 134);

INSERT INTO customers_products (customer_id, product_id) VALUES (1,1);
INSERT INTO customers_products (customer_id, product_id) VALUES (1,2);
INSERT INTO customers_products (customer_id, product_id) VALUES (1,3);
INSERT INTO customers_products (customer_id, product_id) VALUES (2,3);