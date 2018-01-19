 USE `security`;

 INSERT INTO user (username, first_name, last_name, email, phone_number, enabled) VALUES ('alberto.egido', 'Alberto', 'Egido', 'alberto.egido@tui.com', '+1234567890', true);
 INSERT INTO user (username, first_name, last_name, email, phone_number, enabled) VALUES ('carlosjose.requena', 'Carlos Jose', 'Requena', 'carlosjose.requena@tui.com', '+0987654321', true);
 INSERT INTO user (username, first_name, last_name, email, phone_number, enabled) VALUES ('ali.khan', 'Ali', 'Khan', 'ali.khan@tui.com', '+0987654321', true);


 INSERT INTO role ( name) VALUES ('ROLE_USER');
 INSERT INTO role ( name) VALUES ('ROLE_ADMIN');

 INSERT INTO privilege ( name) VALUES ('user_view');
 INSERT INTO privilege ( name) VALUES ('user_create');
 INSERT INTO privilege ( name) VALUES ('user_delete');
 INSERT INTO privilege ( name) VALUES ('user_update');

 INSERT INTO user_role (user_id, role_id) VALUES (1, 2);
 INSERT INTO user_role (user_id, role_id) VALUES (2, 1);
 INSERT INTO user_role (user_id, role_id) VALUES (3, 2);


 INSERT INTO role_privilege ( `role_id`, `privilege_id`) VALUES (1,1);
 INSERT INTO role_privilege ( `role_id`, `privilege_id`) VALUES (2,1);
 INSERT INTO role_privilege ( `role_id`, `privilege_id`) VALUES (2,2);
 INSERT INTO role_privilege ( `role_id`, `privilege_id`) VALUES (2,3);
 INSERT INTO role_privilege ( `role_id`, `privilege_id`) VALUES (2,4);
