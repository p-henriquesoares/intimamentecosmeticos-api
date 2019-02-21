CREATE TABLE produto(
	id_produto INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(100) NOT NULL,
	cod_barra VARCHAR(20) NOT NULL,
	id_marca INT NOT NULL,
	id_cor INT,
	id_categoria INT NOT NULL,
	ativo BOOLEAN DEFAULT TRUE NOT NULL,
	FOREIGN KEY (id_marca) REFERENCES marca(id_marca),
	FOREIGN KEY (id_cor) REFERENCES cor(id_cor),
	FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
) engine=InnoDB DEFAULT CHARSET=utf8;