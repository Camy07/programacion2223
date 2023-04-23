create database usuarios;

CREATE USER 'usuario'@'localhost' IDENTIFIED BY '1234';

GRANT ALL PRIVILEGES ON usuarios.* TO 'usuario'@'localhost' WITH GRANT OPTION;

FLUSH PRIVILEGES;


