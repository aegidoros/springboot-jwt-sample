 USE `security`;

 INSERT INTO user (username, first_name, last_name, email, phone_number, enabled) VALUES ('alberto.egido', 'Alberto', 'Egido', 'alberto.egido@tui.com', '+1234567890', true);
 INSERT INTO user (username, first_name, last_name, email, phone_number, enabled) VALUES ('carlosjose.requena', 'Carlos Jose', 'Requena', 'carlosjose.requena@tui.com', '+0987654321', true);
 INSERT INTO user (username, first_name, last_name, email, phone_number, enabled) VALUES ('ali.khan', 'Ali', 'Khan', 'ali.khan@tui.com', '+0987654321', true);

 INSERT INTO api_client(api_key, secret_id) VALUES ('624d6572-367f-48d2-bc54-bcf997f6f40b', 'mYseCretApIKeY');

 INSERT INTO role ( name) VALUES ('ROLE_USER');
 INSERT INTO role ( name) VALUES ('ROLE_ADMIN');

 INSERT INTO permission ( name) VALUES ('user_view');
 INSERT INTO permission ( name) VALUES ('user_edit');

 INSERT INTO user_role (user_id, role_id) VALUES (1, 2);
 INSERT INTO user_role (user_id, role_id) VALUES (2, 1);
 INSERT INTO user_role (user_id, role_id) VALUES (3, 2);

 INSERT INTO api_role (api_client_id, role_id) VALUES (1,2);

 INSERT INTO role_permission ( `role_id`, `permission_id`) VALUES (1,1);
 INSERT INTO role_permission ( `role_id`, `permission_id`) VALUES (2,1);
 INSERT INTO role_permission ( `role_id`, `permission_id`) VALUES (2,2);


