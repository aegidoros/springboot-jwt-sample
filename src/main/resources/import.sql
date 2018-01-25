 USE `security`;

 INSERT INTO role ( name) VALUES ('ROLE_USER');
 INSERT INTO role ( name) VALUES ('ROLE_ADMIN');


 INSERT INTO user (username, first_name, last_name, email, enabled, role_id) VALUES ('alberto.egido', 'Alberto', 'Egido', 'alberto.egido@tui.com', true, 2);
 INSERT INTO user (username, first_name, last_name, email, enabled, role_id) VALUES ('carlosjose.requena', 'Carlos Jose', 'Requena', 'carlosjose.requena@tui.com', true, 2);
 INSERT INTO user (username, first_name, last_name, email, enabled, role_id) VALUES ('ali.khan', 'Ali', 'Khan', 'ali.khan@tui.com',  true, 1);


 INSERT INTO permission ( name) VALUES ('user_view');
 INSERT INTO permission ( name) VALUES ('user_edit');


 INSERT INTO role_permission ( `role_id`, `permission_id`) VALUES (1,1);
 INSERT INTO role_permission ( `role_id`, `permission_id`) VALUES (2,1);
 INSERT INTO role_permission ( `role_id`, `permission_id`) VALUES (2,2);
