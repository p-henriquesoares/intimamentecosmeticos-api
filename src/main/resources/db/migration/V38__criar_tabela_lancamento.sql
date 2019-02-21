CREATE TABLE lancamento(
	id_lancamento INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_categoria_lancamento INT NOT NULL,
	id_pessoa INT NOT NULL,
	data_vencimento DATETIME,
	data_pagamento DATETIME,
	descricao VARCHAR(20) NOT NULL,
	valor DECIMAL NOT NULL,
	FOREIGN KEY (id_categoria_lancamento) REFERENCES categoria_lancamento(id_categoria_lancamento),
	FOREIGN KEY (id_pessoa) REFERENCES pessoa(id_pessoa)
) engine=InnoDB DEFAULT CHARSET=utf8;