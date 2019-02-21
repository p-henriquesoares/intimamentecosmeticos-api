CREATE TABLE grupo_permissao(
	id_grupo_permissao INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_grupo INT NOT NULL,
	id_permissao INT NOT NULL,
	FOREIGN KEY (id_grupo) REFERENCES grupo(id_grupo),
	FOREIGN KEY (id_permissao) REFERENCES permissao(id_permissao)
) engine=InnoDB DEFAULT CHARSET=utf8;