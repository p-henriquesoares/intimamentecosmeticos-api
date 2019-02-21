CREATE TABLE produto_venda(
	id_produto_venda INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_produto INT NOT NULL,
	id_venda INT NOT NULL,
	FOREIGN KEY (id_produto) REFERENCES produto(id_produto),
	FOREIGN KEY (id_venda) REFERENCES venda(id_venda)
) engine=InnoDB DEFAULT CHARSET=utf8;