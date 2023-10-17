insert into user_group(id, name) values(1, 'ROLE_ADMIN');
insert into user_group(id, name) values(2, 'ROLE_USER');
insert into user(id, user_name, password, group_id) values(2, 'Ayşe', '$2a$12$oOt.7qwjIgKRfH1UIv8UCO3aF3.d59QWrDeZWhydnmG102uQC.YkK', 2);
insert into user(id, user_name, password, group_id) values(1, 'Ali', '$2a$12$oOt.7qwjIgKRfH1UIv8UCO3aF3.d59QWrDeZWhydnmG102uQC.YkK', 1);