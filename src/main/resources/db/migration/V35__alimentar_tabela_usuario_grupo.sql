INSERT INTO usuario_grupo (id_usuario, id_grupo) VALUES((select distinct id_usuario from usuario where lower(nome) = lower('pedro.soares')), (select distinct id_grupo from grupo where lower(descricao) = lower('Administrador')));