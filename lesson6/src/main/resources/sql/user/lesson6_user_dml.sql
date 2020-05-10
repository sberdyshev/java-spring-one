DELETE FROM user_role;
DELETE FROM user;
DELETE FROM role;

INSERT INTO role (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO role (id, name) VALUES (2, 'ROLE_USER');

INSERT INTO user (id, name, password) VALUES (1, 'user1', 'pass1');
INSERT INTO user (id, name, password) VALUES (1, 'user2', 'pass2');
INSERT INTO user (id, name, password) VALUES (1, 'user3', 'pass3');
INSERT INTO user (id, name, password) VALUES (1, 'admin', 'pass4');

INSERT INTO product (id, name, description, cost) VALUES (2, 'tv', 'a good tv', 12);
INSERT INTO product (id, name, description, cost) VALUES (3, 'chips', 'food', 134);
INSERT INTO product (id, name, description, cost) VALUES (4, 'product4', '44444',  34);
INSERT INTO product (id, name, description, cost) VALUES (5, 'product5', '55555', 5);
INSERT INTO product (id, name, description, cost) VALUES (6, 'product6', '66666', 7);
INSERT INTO product (id, name, description, cost) VALUES (7, 'product7', '77777', 17);
INSERT INTO product (id, name, description, cost) VALUES (8, 'product8', '88888', 22);
INSERT INTO product (id, name, description, cost) VALUES (9, 'product9', '99999', 26);
INSERT INTO product (id, name, description, cost) VALUES (10, 'product10', '00000', 31);
INSERT INTO product (id, name, description, cost) VALUES (11, 'product11', '11111', 33);
INSERT INTO product (id, name, description, cost) VALUES (12, 'product12', '22222', 35);
INSERT INTO product (id, name, description, cost) VALUES (13, 'product13', '33333', 39);
INSERT INTO product (id, name, description, cost) VALUES (14, 'product14', '44444', 44);
INSERT INTO product (id, name, description, cost) VALUES (15, 'product15', '55555', 46);
INSERT INTO product (id, name, description, cost) VALUES (16, 'product16', '66666', 48);
INSERT INTO product (id, name, description, cost) VALUES (17, 'product17', '77777', 55);
INSERT INTO product (id, name, description, cost) VALUES (18, 'product18', '88888', 57);
INSERT INTO product (id, name, description, cost) VALUES (19, 'product19', '99999', 58);
INSERT INTO product (id, name, description, cost) VALUES (20, 'product20', '00000', 58);