CREATE TABLE usuario_grupo(
	id_usuario_grupo INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_usuario INT NOT NULL,
	id_grupo INT NOT NULL,
	FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
	FOREIGN KEY (id_grupo) REFERENCES grupo(id_grupo)
) engine=InnoDB DEFAULT CHARSET=utf8;