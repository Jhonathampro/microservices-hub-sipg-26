INSERT INTO tb_pedido(name, cpf, data, status, valor_total) values ('João Veiera', 12345678910, '2025-11-25','CRIADO', 540.0);
INSERT INTO tb_pedido(name, cpf, data, status, valor_total) values ('Kézia', 12345678910, '2026-11-30','CRIADO', 359.0);


INSERT INTO tb_item_do_pedido(quantidade, descricao, preco_unitario, pedido_id) values ( 2, 'Mouse sem fio Microsoft', 250.0, 1);
INSERT INTO tb_item_do_pedido(quantidade, descricao, preco_unitario, pedido_id) values ( 1, 'Tecalado sem fio Microsoft', 290.0, 1);
INSERT INTO tb_item_do_pedido(quantidade, descricao, preco_unitario, pedido_id) values ( 1, 'Smart TV LG LED', 3599.0, 2);