CREATE TABLE IF NOT EXISTS product (id INTEGER PRIMARY KEY,name VARCHAR NOT NULL, description VARCHAR NOT NULL, cost INTEGER NOT NULL);

CREATE TABLE IF NOT EXISTS customer (
id INTEGER PRIMARY KEY,
name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS customers_products (
customer_id INTEGER NOT NULL REFERENCES customer(id),
product_id INTEGER NOT NULL REFERENCES product(id)
);