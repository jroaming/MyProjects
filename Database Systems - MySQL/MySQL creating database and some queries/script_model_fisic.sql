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

-- ARA EL NOSTRE DIAGRAMA:

	-- PRIMER LES ENTITATS QUE NO DEPENEN DE NINGU:
drop table if exists agencia cascade;
create table agencia(
	nom		varchar(255),

	primary key(nom)
);	-- select * from agencia;

drop table if exists subagencia cascade;
create table subagencia(
	agencia		varchar(255),
	subagencia	varchar(255),

	foreign key(agencia) references agencia(nom),
	primary key(agencia, subagencia)
);	-- select * from subagencia;


	-- ESQUERRA DEL DIAGRAMA:

drop table if exists estat cascade;
create table estat(
	state	varchar(255),
	agencia	varchar(255),

	foreign key(agencia) references agencia(nom),
	
	primary key (state)
);	-- select * from estat;

drop table if exists ciutat cascade;
create table ciutat(
	city	varchar(255),
	state	varchar(255),

	foreign key(state) references estat(state),
	primary key(city, state)
);	-- select * from ciutat;

drop table if exists persona cascade;
create table persona(
	id_persona	bigint,
	race		varchar(255),
	gender		char,
	state		varchar(255),
	city		varchar(255),
	
	foreign key(state, city) references ciutat(state, city),
	-- "al definir una pk compuesta (ver tabla ciutat), la fk debe ser también compuesta"
	
	primary key(id_persona)
);	-- select * from persona;

drop table if exists llicencia cascade;
create table llicencia(
	id_llicencia	serial,
	dl_state	varchar(255),
	driving_license	varchar(255),
	state		varchar(255),
	id_persona	bigint,

	foreign key(state) references estat(state),
	foreign key(id_persona) references persona(id_persona),
	primary key(id_llicencia)
);	-- select * from llicencia;

drop table if exists propietari cascade;
create table propietari(
	id_propietari	bigint,
	nom		varchar(255),
	cognom1		varchar(255),
	cognom2		varchar(255),
	
	foreign key(id_propietari) references persona(id_persona),
	primary key(id_propietari)
);	-- select * from propietari;

drop table if exists vehicle cascade;
create table vehicle(
	license		varchar(255),	-- matricula
	type_vehicle	varchar(255),
	year		int,
	make		varchar(255),
	color		varchar(255),
	model		varchar(255),
	id_propietari	bigint,
	estat		varchar(255),

	foreign key(id_propietari) references propietari(id_propietari),
	-- foreign key(state) references estat(id_propietari),	-- no es foreign key, porque es un atributo del pdf directamente
	primary key(license)
);	-- select * from vehicle;


	-- DRETA DEL DIAGRAMA
drop table if exists accident cascade;
create table accident(
	id_accident	bigint,
	accident	boolean,
	belts		boolean,
	person_injury	boolean,
	property_dmg	boolean,
	fatal		boolean,
	hazmat		boolean,
	alcohol		boolean,
	work_zone	boolean,

	primary key(id_accident)
);	-- select * from accident;


	-- CENTRE DEL DIAGRAMA:

drop table if exists infraccio cascade;
create table infraccio(
	id_infraccio	bigint,
	violation_type	varchar(255),
	arrest_type	varchar(255),
	charge		varchar(255),
	
	primary key(id_infraccio)
);	-- select * from infraccio;

drop table if exists legisla cascade;
create table legisla(
	state		varchar(255),
	infraccio	bigint,

	foreign key(state) references estat(state),
	foreign key(infraccio) references infraccio(id_infraccio),
	primary key(state, infraccio)
);	-- select * from legisla;

drop table if exists sanciona cascade;
create table sanciona(
	id_infraccio	bigint,
	id_persona	bigint,
	agencia		varchar(255),
	subagencia	varchar(255),
	license		varchar(255),
	id_accident	bigint,		-- porque la necesitamos para hacer la fk compuesta (de la pk compuesta de la tabla "subagencia").
	date_stop	varchar(255),	-- NO PUEDE SER DATE, PORQUE ES DD-MM-YYYY, Y DATE: YYYY-MM-DD
	time_stop	time,
	description	varchar(255),
	location	varchar(255),
	latitude	varchar(255),
	longitude	varchar(255),

	foreign key(id_infraccio) references infraccio(id_infraccio),
	foreign key(id_persona) references persona(id_persona),
	foreign key(agencia, subagencia) references subagencia(agencia, subagencia),
	foreign key(license) references vehicle(license),
	foreign key(id_accident) references accident(id_accident),
	primary key(id_infraccio, id_persona, agencia, subagencia, license, id_accident)
);	-- select * from sanciona;


-- SOBRE LA TAULA IMPORT_TRAFFIC_VIOLATIONS:
-- També agreguem l'atribut id_traffic_violations, que ens serà útil per l'entitat accident (id_accident):
alter table import_traffic_violations add id_itv serial; -- (30 segons)

/*
Ara inserim la informacio a les taules del nostre diagrama desde les de importacio,
seguint el mateix ordre que el de la seva creació, per evitar problemes amb les fks:
*/

-- per tractar les dades de la taula import_traffic_violations ens aniria bé
-- tenir una pk que fos id de les files de l'excel:
-- alter table import_traffic_violations add id_itv serial;
-- select * from import_traffic_violations;

-- PRIMER LES QUE NO DEPENEN DE NINGU:
-- Agencia: (1 secs)
insert into agencia(nom)
select distinct itv.agency
from	import_traffic_violations as itv;
	-- select * from agencia;

-- Subagencia: (5 segons)
insert into subagencia(agencia, subagencia)
select distinct	a.nom, itv.subagency
from	agencia as a, import_traffic_violations as itv
where	a.nom = itv.agency;
	-- select * from subagencia order by agencia;

-- ESQUERRA DEL DIAGRAMA:
-- per ara farem servir les variables driver_state i driver_city per a l'index d'estats i ciutats.
-- Estat: (3 segons, 67 estats)
insert into	estat
select distinct	itv.driver_state, a.nom
from	import_traffic_violations as itv, agencia as a
where	itv.agency like a.nom;
	-- select * from estat;

-- Ciutat: (15 segons, 8K ciutats)
insert into	ciutat(city, state)
select distinct	itv.driver_city, estat.state
from	estat, import_traffic_violations as itv
where	estat.state like itv.driver_state;
	-- select * from ciutat;	-- UN PAR DE CIUDADES DAN COSAS RARAS

-- Persona: (ho farem per passos, perque si no la PK de la taula peta amb els distincts) (50 segons, 510K persones)
-- 1) INSERT para las ID_PERSONAs (5 segundos)
insert into persona(id_persona)
select distinct itv.id_persona
from	import_traffic_violations as itv
order by id_persona;
-- 2) UPDATEs DE LA TAULA ITV: (17 segons)
update	persona
set		-- para los generos y razas
	race = itv.race,
	gender = itv.gender,
		-- para los estados y ciudades
	state = itv.driver_state,
	city = itv.driver_city
from	import_traffic_violations as itv
where	itv.id_persona = persona.id_persona
and	persona.id_persona = itv.id_persona;
	-- select * from persona; -- (20 segons): 510K persones.
-- 3) UPDATEs DE LA TAULA IP: (2 segons)
insert	into persona(id_persona)
select	ip.id_propietari
from	import_propietaris as ip
where	ip.id_propietari not in(select	p.id_persona
				from	persona as p
				where	p.id_persona = ip.id_propietari);
				
-- Llicencia: (1 minut)
-- 1) Primero las ID_PERSONAs, ya que si no saldrán repetidas algunas licencias (517K en vez de 498K), y solo hay 1 ll por persona.
-- (1 minut, 498K llicencies)
insert into llicencia(id_persona)
select distinct	p.id_persona
from	import_traffic_violations as itv, persona as p
where	itv.id_persona = p.id_persona
and	not(itv.driving_license like '%XX%');	-- perque XX vol dir que no tenen llicencia.
	-- select * from llicencia;
-- 2) Despues, cuando no tenemos licencias repetidas y las id_personas estan introducidas, añadimos los demás datos. (30 segons)
update	llicencia
set	driving_license = itv.driving_license,
	dl_state = itv.dl_state,
	state = itv.driver_state	-- usamos esta para ahorrarnos una tabla.
from	import_traffic_violations as itv
where	llicencia.id_persona = itv.id_persona;
	-- select * from llicencia; -- (30 segons, 498K llicencies)

-- Propietari: (15 segons)
insert into propietari(id_propietari)
select distinct p.id_persona
from	persona as p;

update	propietari
set	nom = ip.name,
	cognom1 = ip.surname1,
	cognom2 = ip.surname2
from	import_propietaris as ip
where	propietari.id_propietari = ip.id_propietari;
	-- select * from propietari order by id_propietari;

-- Vehicle: (10 segons)
-- 1) Meter las licenses de los vehiculos en la tabla (2 segundos, 125K filas)
insert into vehicle(license)
select distinct	itv.license
from	import_traffic_violations as itv;
-- 2) Añadir los demás datos: (7 segundos, 125K vehiculos (=propietarios))
update	vehicle
set	type_vehicle = itv.vehicle_type,
	year = itv.year,
	make = itv.make,
	color = itv.color,
	model = itv.model,
	estat = itv.state,
	id_propietari = ip.id_propietari
from	import_traffic_violations as itv, import_propietaris as ip, propietari as p
where	itv.id_persona = p.id_propietari
and	ip.id_propietari = p.id_propietari
and	vehicle.license = itv.license;
-- IMPORTANTE: HAY DOS PROPIETARIOS QUE NO SE VEN PORQUE NO APARECEN EN LA ITV.
	-- select * from vehicle; -- (12 segons)

-- DRETA DEL DIAGRAMA:
-- Accident: (1 sec, 28K accidents)
-- 1) Primer si ha hagut accident
insert into accident(id_accident, accident, belts, person_injury, property_dmg, fatal, hazmat, alcohol, work_zone)
select	id_itv, accident, belts, personal_injury, property_dmg, fatal, hazmat, alcohol, work_zone
from	import_traffic_violations
where	contributed_accident = true;

-- 2) Despres si no hi ha hagut accident, ja que llavors tots els altres atributs son null.
insert	into accident(id_accident)
select	itv.id_itv
from	import_traffic_violations as itv
where	itv.id_itv not in(select a.id_accident
			from	accident as a
			where	itv.id_itv = a.id_accident);
	-- select * from accident order by id_accident;

-- CENTRE DEL DIAGRAMA:
-- Infraccio: (15 segons)
insert into infraccio
select	id_itv, violation_type, arrest_type, charge
from	import_traffic_violations;
	-- select * from infraccio; -- (+1 minut)

-- Legisla: (2 minut)
insert into legisla(state, infraccio)
select	e.state, i.id_infraccio
from	estat as e, infraccio as i, import_traffic_violations as itv
where	e.state like itv.driver_state
and	itv.id_itv = i.id_infraccio;
	-- select * from legisla; -- (20 segons)
	
-- Sanciona 1: (2 minuts, 1204K sancions)
-- aunque todo esto podría sacarlo de itv, usaremos las otras tablas para comprovar que la info introducida es correcta.
insert into sanciona(id_infraccio, id_persona, agencia, subagencia, license, id_accident, 
	date_stop, time_stop, description, location, latitude, longitude)
select	i.id_infraccio, itv.id_persona, ag.nom, sag.subagencia, v.license, a.id_accident,		-- perque les pks no poden valer null, i es el seu valor si contributed_accident = false.
	itv.date_stop, itv.time_stop, itv.description, itv.location, itv.latitude, itv.longitude	-- Atributos normales
from	infraccio as i,
	agencia as ag,
	accident as a,
	subagencia as sag,
	vehicle as v,
	import_traffic_violations as itv
where	i.id_infraccio = itv.id_itv
and	itv.agency = ag.nom
and	sag.agencia = ag.nom
and	sag.subagencia = itv.subagency
and	a.id_accident = itv.id_itv
and	v.license = itv.license;

-- select * from sanciona order by id_accident; -- 3 minuts.