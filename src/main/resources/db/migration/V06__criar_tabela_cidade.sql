CREATE TABLE cidade(
	id_cidade INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_estado INT NOT NULL,
	descricao VARCHAR(20) NOT NULL,
	FOREIGN KEY (id_estado) REFERENCES estado(id_estado)
) engine=InnoDB DEFAULT CHARSET=utf8;