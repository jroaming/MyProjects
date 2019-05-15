-- Database: "BD18_p1"
/*
-- DROP DATABASE "BD18_p1";

CREATE DATABASE "BD18_p1"
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Spanish_Spain.1252'
       LC_CTYPE = 'Spanish_Spain.1252'
       CONNECTION LIMIT = -1;
*/

-- TAULES D'IMPORTACIO:

drop table if exists import_propietaris;
create table import_propietaris(
	id_propietari	bigint,
	license		varchar(255),
	name		varchar(255),
	surname1	varchar(255),
	surname2	varchar(255)
);	-- select * from import_propietaris (tarda 7 segundos en cargar la tabla entera);

drop table if exists import_traffic_violations;
create table import_traffic_violations(
	date_stop	varchar(255),
	time_stop	time,
	agency		varchar(255),
	subagency	varchar(255),
	description	varchar(255),
	location	varchar(255),
	latitude	varchar(255),
	longitude	varchar(255),
	accident	boolean,
	belts		boolean,
	personal_injury	boolean,
	property_dmg	boolean,
	fatal		boolean,
	hazmat		boolean,
	alcohol		boolean,
	work_zone	boolean,
	state		varchar(255),	-- aquesta la farem servir per a la classe vehicle
	license		varchar(255),
	vehicle_type	varchar(255),
	driving_license	char(255),
	year		int,
	make		varchar(255),
	model		varchar(255),
	color		varchar(255),
	violation_type	varchar(255),
	charge		varchar(255),
	contributed_accident	boolean,
	race		varchar(255),
	gender		char,
	driver_city	varchar(255),
	driver_state	varchar(255),	-- aquesta es per a la classe estat
	dl_state	varchar(255),	-- aquesta es per a la classe llicencia
	arrest_type	varchar(255),
	id_persona	bigint
);	-- select * from import_traffic_violations; -- (tarda 9 minutos aprox en cargar la tabla entera)


-- Fiquem la informació dels propietaris del csv: (4 segons en copiar les dades)
copy import_propietaris from 'G:\Universidad\3o\BBDD\p1\Arxius\propietaris.csv' delimiter ';';
-- Fiquem la info de les violacions de trafic dels 23 csv: (35 segons en copiar les dades)
copy import_traffic_violations from 'G:\Universidad\3o\BBDD\p1\Arxius\traffic_violations_1.csv' delimiter ';' csv header;
copy import_traffic_violations from 'G:\Universidad\3o\BBDD\p1\Arxius\traffic_violations_2.csv' delimiter ';' csv header;
copy import_traffic_violations from 'G:\Universidad\3o\BBDD\p1\Arxius\traffic_violations_3.csv' delimiter ';' csv header;
copy import_traffic_violations from 'G:\Universidad\3o\BBDD\p1\Arxius\traffic_violations_4.csv' delimiter ';' csv header;
copy import_traffic_violations from 'G:\Universidad\3o\BBDD\p1\Arxius\traffic_violations_5.csv' delimiter ';' csv header;
copy import_traffic_violations from 'G:\Universidad\3o\BBDD\p1\Arxius\traffic_violations_6.csv' delimiter ';' csv header;
copy import_traffic_violations from 'G:\Universidad\3o\BBDD\p1\Arxius\traffic_violations_7.csv' delimiter ';' csv header;
copy import_traffic_violations from 'G:\Universidad\3o\BBDD\p1\Arxius\traffic_violations_8.csv' delimiter ';' csv header;
copy import_traffic_violations from 'G:\Universidad\3o\BBDD\p1\Arxius\traffic_violations_9.csv' delimiter ';' csv header;
copy import_traffic_violations from 'G:\Universidad\3o\BBDD\p1\Arxius\traffic_violations_10.csv' delimiter ';' csv header;
copy import_traffic_violations from 'G:\Universidad\3o\BBDD\p1\Arxius\traffic_violations_11.csv' delimiter ';' csv header;
copy import_traffic_violations from 'G:\Universidad\3o\BBDD\p1\Arxius\traffic_violations_12.csv' delimiter ';' csv header;
copy import_traffic_violations from 'G:\Universidad\3o\BBDD\p1\Arxius\traffic_violations_13.csv' delimiter ';' csv header;
copy import_traffic_violations from 'G:\Universidad\3o\BBDD\p1\Arxius\traffic_violations_14.csv' delimiter ';' csv header;
copy import_traffic_violations from 'G:\Universidad\3o\BBDD\p1\Arxius\traffic_violations_15.csv' delimiter ';' csv header;
copy import_traffic_violations from 'G:\Universidad\3o\BBDD\p1\Arxius\traffic_violations_16.csv' delimiter ';' csv header;
copy import_traffic_violations from 'G:\Universidad\3o\BBDD\p1\Arxius\traffic_violations_17.csv' delimiter ';' csv header;
copy import_traffic_violations from 'G:\Universidad\3o\BBDD\p1\Arxius\traffic_violations_18.csv' delimiter ';' csv header;
copy import_traffic_violations from 'G:\Universidad\3o\BBDD\p1\Arxius\traffic_violations_19.csv' delimiter ';' csv header;
copy import_traffic_violations from 'G:\Universidad\3o\BBDD\p1\Arxius\traffic_violations_20.csv' delimiter ';' csv header;
copy import_traffic_violations from 'G:\Universidad\3o\BBDD\p1\Arxius\traffic_violations_21.csv' delimiter ';' csv header;
copy import_traffic_violations from 'G:\Universidad\3o\BBDD\p1\Arxius\traffic_violations_22.csv' delimiter ';' csv header;
copy import_traffic_violations from 'G:\Universidad\3o\BBDD\p1\Arxius\traffic_violations_23.csv' delimiter ';' csv header;

-- triga 30 segons en carregar totes les dades d'importació.
