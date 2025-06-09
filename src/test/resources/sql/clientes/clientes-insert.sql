
/*usuarios*/
insert into USUARIOS(id, username, password, role) values (100, 'testeApi@gmail.com', '$2a$10$bxyUsFJtaiRAtKdbbrUU4e88F0YUlT2wB.DNKhwakAzq3U045QLUS', 'ROLE_ADMIN');
insert into USUARIOS(id, username, password, role) values (101, 'teste-Api1@gmail.com', '$2a$10$bxyUsFJtaiRAtKdbbrUU4e88F0YUlT2wB.DNKhwakAzq3U045QLUS', 'ROLE_CLIENTE');
insert into USUARIOS(id, username, password, role) values (102, 'teste$Api2@gmail.com', '$2a$10$bxyUsFJtaiRAtKdbbrUU4e88F0YUlT2wB.DNKhwakAzq3U045QLUS', 'ROLE_CLIENTE');
insert into USUARIOS(id, username, password, role) values (103, 'testeApi3@gmail.com', '$2a$10$bxyUsFJtaiRAtKdbbrUU4e88F0YUlT2wB.DNKhwakAzq3U045QLUS', 'ROLE_CLIENTE');

/*Clientes + relação com usuarios*/
insert into clientes(id, nome, cpf, id_usuario) values (10, 'Acrísio Cruz','89395284099', 100);
insert into clientes(id, nome, cpf, id_usuario) values (11, 'Raditz API', '48644714015', 101);
insert into clientes(id, nome, cpf, id_usuario) values (12, 'Luffy API', '14186131007', 102);
