CREATE TABLE estoque(
	id_estoque INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_produto INT NOT NULL,
	id_fornecedor INT NOT NULL,
	quantidade INT NOT NULL,
	FOREIGN KEY (id_produto) REFERENCES produto(id_produto),
	FOREIGN KEY (id_fornecedor) REFERENCES fornecedor(id_fornecedor)
) engine=InnoDB DEFAULT CHARSET=utf8;