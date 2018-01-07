
 INSERT INTO users (username, password, first_name, last_name, email, phone_number, enabled) VALUES ('user', 'test', 'Fan', 'Jin', 'user@example.com', '+1234567890', true);
 INSERT INTO users (username, password, first_name, last_name, email, phone_number, enabled) VALUES ('admin', 'test', 'Jing', 'Xiao', 'admin@example.com', '+0987654321', true);

 INSERT INTO authority ( name) VALUES ('ROLE_USER');
 INSERT INTO authority ( name) VALUES ('ROLE_ADMIN');

 INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
 INSERT INTO user_authority (user_id, authority_id) VALUES (2, 1);
 INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2);
