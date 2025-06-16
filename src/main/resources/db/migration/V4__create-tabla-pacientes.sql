CREATE TABLE pacientes(
    id bigint not null auto_increment,
    nombre varchar(100) not null,
    email varchar(100) not null unique,
    documento varchar(8) not null unique,
    telefono varchar (10) not null,
    obra_social varchar(100),
    numero_afiliado varchar (10) unique,
    calle varchar(100) not null,
    distrito varchar(100) not null,
    complemento varchar(100),
    numero varchar(10) not null,
    ciudad varchar(100) not null,
    activo tinyint,

    primary key (id)
);