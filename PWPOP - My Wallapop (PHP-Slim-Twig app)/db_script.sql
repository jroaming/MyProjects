drop database if exists pwpop;
create database pwpop;
use pwpop;
drop table if exists user;
create table user(
  /*id_user int not null auto_increment,*/
  name varchar(255),
  username varchar(255),
  email varchar(255),
  birthdate date,
  phone_number varchar(255),
  password varchar(255),
  image varchar(255),
  is_active boolean not null default 1,

  primary key(username)
);
drop table if exists product;
create table product(
  id int not null auto_increment,
  username varchar(255),
  title varchar(255),
  description varchar(255),
  price float,
  category varchar(255),
  image varchar(255),
  is_active boolean not null default 1,

  foreign key(username) references user(username),

  primary key(id)
);