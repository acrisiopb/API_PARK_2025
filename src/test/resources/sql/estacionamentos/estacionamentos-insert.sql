insert into USUARIOS(id, username, password, role) values (100, 'testeApi@gmail.com', '$2a$10$bxyUsFJtaiRAtKdbbrUU4e88F0YUlT2wB.DNKhwakAzq3U045QLUS', 'ROLE_ADMIN');
insert into USUARIOS(id, username, password, role) values (101, 'teste-Api1@gmail.com', '$2a$10$bxyUsFJtaiRAtKdbbrUU4e88F0YUlT2wB.DNKhwakAzq3U045QLUS', 'ROLE_CLIENTE');
insert into USUARIOS(id, username, password, role) values (102, 'teste$Api2@gmail.com', '$2a$10$bxyUsFJtaiRAtKdbbrUU4e88F0YUlT2wB.DNKhwakAzq3U045QLUS', 'ROLE_CLIENTE');

insert into VAGAS(id,codigo,status) values(100,'A-01', 'OCUPADA');
insert into VAGAS(id,codigo,status) values(200,'B-02', 'OCUPADA');
insert into VAGAS(id,codigo,status) values(300,'C-03', 'OCUPADA');
insert into VAGAS(id,codigo,status) values(400,'D-04', 'LIVRE');
insert into VAGAS(id,codigo,status) values(500,'D-05', 'LIVRE');

INSERT INTO clientes (id , nome , cpf, id_usuario) values (21,'Acrisio F','30570215064',101);
INSERT INTO clientes (id , nome , cpf, id_usuario) values (22,'Luffy duck','77172549058',102);

insert into clientes_tem_vagas (numero_recibo , placa , marca , modelo , cor , data_entrada , id_cliente , id_vaga)
values('20250612-170829','GSL-8589','PALIO','BLUE','2025-06-13 05:06:29',10,100)
insert into clientes_tem_vagas (numero_recibo , placa , marca , modelo , cor , data_entrada , id_cliente , id_vaga)
values('20250614-170529','FAT-1015','SIENA','DARK','2025-06-13 05:06:29',20,200)
insert into clientes_tem_vagas (numero_recibo , placa , marca , modelo , cor , data_entrada , id_cliente , id_vaga)
values( '20250615-170929', 'XIT-2025', 'PALIO', 'RED', '2025-06-13 05:06:29', 300, 102)