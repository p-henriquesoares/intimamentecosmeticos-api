CREATE TABLE cliente(
	id_cliente INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_pessoa INT NOT NULL,
	FOREIGN KEY (id_pessoa) REFERENCES pessoa(id_pessoa)
) engine=InnoDB DEFAULT CHARSET=utf8;