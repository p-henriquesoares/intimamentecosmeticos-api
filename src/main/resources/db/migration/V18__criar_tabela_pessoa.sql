CREATE TABLE pessoa(
	id_pessoa INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(60) NOT NULL,
	cpf VARCHAR(11),
	cnpj VARCHAR(14),
	email VARCHAR(20),
	telefone VARCHAR(12),
	id_pais INT NOT NULL,
	id_estado INT NOT NULL,
	id_cidade INT NOT NULL,
	id_logradouro INT,
	endereco VARCHAR(20),
	numero INT,
	complemento VARCHAR(20),
	FOREIGN KEY (id_pais) REFERENCES pais(id_pais),
	FOREIGN KEY (id_estado) REFERENCES estado(id_estado),
	FOREIGN KEY (id_cidade) REFERENCES cidade(id_cidade),
	FOREIGN KEY (id_logradouro) REFERENCES logradouro(id_logradouro)
) engine=InnoDB DEFAULT CHARSET=utf8;