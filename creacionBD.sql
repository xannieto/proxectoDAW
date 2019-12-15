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
    imaxe varchar(20) not null,
    constraint id_unico unique(id),
    constraint nome_unico unique(nome),
    primary key (id, nome)
);

create table compra (
    usuario_email varchar(40) not null,
    produto_id int(6) unsigned not null,
    data_compra timestamp default current_timestamp,
    cantidade int(3) not null,
    primary key (usuario_email, produto_id, data_compra)
);
    
insert into produto
    (nome,descricion,prezo,cantidade, imaxe)
    values
    ("Viño ribeiro","Viño cultivado nas nosas parras e elaborado nas nosas adegas","21.00", 60, "1.png"),
    ("Sidra","Elaborada a partir das nosas maceiras que rodean o pazo","17.55", 15, "2.jpg"),
    ("Licor café","Lico café elaborado a partir da caña branca e dos viños da zona","12.00", 20, "3.jpg");
