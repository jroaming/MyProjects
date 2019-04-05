CREATE DATABASE IF NOT EXISTS dbtroner;
USE dbtroner;
drop table if exists partida;
drop table if exists juga;
drop table if exists usuari;

CREATE TABLE IF NOT EXISTS partida (
  id_partida	bigint unsigned NOT NULL AUTO_INCREMENT,
  tipus         int DEFAULT NULL,
  data		date DEFAULT NULL,
  guanyador	varchar(255) DEFAULT NULL,
  PRIMARY KEY(id_partida)
);

CREATE TABLE IF NOT EXISTS usuari (
  nom				varchar(255) NOT NULL,
  correu			varchar(255) NOT NULL,
  contrassenya 		varchar(255) DEFAULT NULL,
  punts_totals 		bigint DEFAULT NULL,
  punts_vs2		bigint DEFAULT NULL,
  punts_vs4		bigint DEFAULT NULL,
  punts_torneig		bigint DEFAULT NULL,
  data_registre 		date DEFAULT NULL,
  data_ultim_acces	date DEFAULT NULL,
  mov_up			varchar(255) DEFAULT NULL,
  mov_down			varchar(255) DEFAULT NULL,
  mov_left			varchar(255) DEFAULT NULL,
  mov_right		varchar(255) DEFAULT NULL,
  PRIMARY KEY(nom, correu)
);

CREATE TABLE IF NOT EXISTS juga (
  nom_usuari		varchar(255) NOT NULL,
  correu_usuari 		varchar(255) NOT NULL,
  id_partida		bigint NOT NULL,
  punts_partida 		bigint DEFAULT NULL, 
  PRIMARY KEY(nom_usuari,correu_usuari,id_partida),
  FOREIGN KEY(nom_usuari, correu_usuari) REFERENCES usuari(nom, correu)
);