insert into t_users(id,username,password) values(1,'wyf','wyf');
insert into t_users(id,username,password) values(2,'wisely','wisely');


insert into t_roles(id,name) values (1,'ROLE_ADMIN');
insert into t_roles(id,name) values (2,'ROLE_USER');


insert into t_users_roles(users_id,roles_id) values(1,1);
insert into t_users_roles(users_id,roles_id) values(2,2);

