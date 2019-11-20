create database nietoGarcia;

use nietoGarcia;

create table usuario (
    nome varchar(40) not null,
    apelidos varchar(40) not null,
    email varchar(40) not null,
    id int(6) unsigned not null,
    contrasinal varchar(40) not null,
    dni varchar(9) not null,
    estado_civil varchar(10) not null,
    data_nacemento varchar(10) not null,
    provincia varchar(22) not null,
    direccion varchar(40) not null,
    codigo_postal varchar(5) not null,
    numero_telefono varchar(9)  not null,
    primary key (id,email)
);
    
