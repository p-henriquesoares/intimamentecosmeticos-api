CREATE TABLE usuario(
	id_usuario INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_pessoa INT NOT NULL,
	nome VARCHAR(20) NOT NULL,
	senha VARCHAR(20) NOT NULL,
	ativo BOOLEAN DEFAULT true not null,
	FOREIGN KEY (id_pessoa) REFERENCES pessoa(id_pessoa)
) engine=InnoDB DEFAULT CHARSET=utf8;