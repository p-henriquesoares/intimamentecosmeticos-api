CREATE TABLE venda(
	id_venda INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_cliente INT NOT NULL,
	valor DOUBLE NOT NULL,
	id_plataforma INT NOT NULL,
	FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
	FOREIGN KEY (id_plataforma) REFERENCES plataforma(id_plataforma)
) engine=InnoDB DEFAULT CHARSET=utf8;