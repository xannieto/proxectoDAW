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

create table produto (
    id int(6) unsigned not null auto_increment,
    nome varchar(40) not null,
    descricion varchar(100) not null,
    prezo float(10,2) unsigned not null,
    cantidade int(3) unsigned not null,
    constraint id_unico unique(id),
    constraint nome_unico unique(nome),
    primary key (id, nome)
);
    
