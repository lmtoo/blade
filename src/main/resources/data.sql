INSERT INTO t_users(id,username,PASSWORD,is_internal) VALUES(1,'wyf','wyf',1);
INSERT INTO t_users(id,username,PASSWORD,is_internal) VALUES(2,'wisely','wisely',1);


INSERT INTO t_roles(id,NAME) VALUES (1,'ROLE_ADMIN');
INSERT INTO t_roles(id,NAME) VALUES (2,'ROLE_USER');


INSERT INTO t_users_roles(users_id,roles_id) VALUES(1,1);
INSERT INTO t_users_roles(users_id,roles_id) VALUES(2,2);
