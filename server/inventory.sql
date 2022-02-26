drop database if exists inventory;

create database inventory;

use inventory;

create table po (
	ord_id int auto_increment not null,
    name varchar(64) not null,
    email varchar(64),
    primary key(ord_id)
);

create table line_item (
	item_id int auto_increment not null,
    name varchar(64),
    quantity int,
    price float,
    ord_id int,
    
    primary key(item_id),
    constraint fk_ord_id
		foreign key(ord_id)
        references po (ord_id)
);