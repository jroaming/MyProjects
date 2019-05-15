-- FASE 2, PRACTICA 1 - BBDD - Joel López, Alex Pons

-- query 1 (20 sec)
select	count(s.id_infraccio), v.color
from	sanciona as s, vehicle as v
where	s.license = v.license
and	v.type_vehicle like '%Automobile%'
group by s.license, v.color
order by count(s.id_infraccio) desc
limit	1;

-- query 2 (5 sec)
select	p.nom, per.race
from	vehicle as v natural join
	propietari as p join persona as per on p.id_propietari = per.id_persona
where	(v.license like '%1%'
or	v.license like '%2%'
or	v.license like '%3%'
or	v.license like '%4%'
or	v.license like '%5%')
and	v.type_vehicle like '%Automobile%'
order by make desc, model asc, year desc;

-- query 3 (20-25 sec)
(select	v.license, v.year
from	vehicle as v natural join sanciona as s natural join accident as a
where	v.type_vehicle like '%Automobile%'
and	belts = true
and	accident_contributor = false
and	year = 2007
order by license desc
limit 6)
union all
(select	v.license, v.year
from	vehicle as v natural join sanciona as s natural join accident as a
where	v.type_vehicle like '%Automobile%'
and	belts = true
and	accident_contributor = false
and	year = 2008
order by license asc
limit 6)
union all
(select	v.license, v.year
from	vehicle as v natural join sanciona as s natural join accident as a
where	v.type_vehicle like '%Automobile%'
and	belts = true
and	accident_contributor = false
and	year = 2009
order by license desc
limit 6)
union all
(select	v.license, v.year
from	vehicle as v natural join sanciona as s natural join accident as a
where	v.type_vehicle like '%Automobile%'
and	belts = true
and	accident_contributor = false
and	year = 2010
order by license asc
limit 6)
union all
(select	v.license, v.year
from	vehicle as v natural join sanciona as s natural join accident as a
where	v.type_vehicle like '%Automobile%'
and	belts = true
and	accident_contributor = false
and	year = 2011
order by license desc
limit 6)
union all
(select	v.license, v.year
from	vehicle as v natural join sanciona as s natural join accident as a
where	v.type_vehicle like '%Automobile%'
and	belts = true
and	accident_contributor = false
and	year = 2012
order by license asc
limit 6)
union all
(select	v.license, v.year
from	vehicle as v natural join sanciona as s natural join accident as a
where	v.type_vehicle like '%Automobile%'
and	belts = true
and	accident_contributor = false
and	year = 2013
order by license desc
limit 6)
union all
(select	v.license, v.year
from	vehicle as v natural join sanciona as s natural join accident as a
where	v.type_vehicle like '%Automobile%'
and	belts = true
and	accident_contributor = false
and	year = 2014
order by license asc
limit 6)
union all
(select	v.license, v.year
from	vehicle as v natural join sanciona as s natural join accident as a
where	v.type_vehicle like '%Automobile%'
and	belts = true
and	accident_contributor = false
and	year = 2015
order by license desc
limit 6)
union all
(select	v.license, v.year
from	vehicle as v natural join sanciona as s natural join accident as a
where	v.type_vehicle like '%Automobile%'
and	belts = true
and	accident_contributor = false
and	year = 2016
order by license asc
limit 6)

-- query 4 (1-2 sec)
select distinct	description
from	propietari as pro natural join -- id_propietari
				vehicle as v natural join -- license
						sanciona as s natural join persona as per -- id_persona
where	pro.id_propietari = per.id_persona
and	s.description like '%a%'
and	s.description like '%e%'
and	s.description like '%i%'
and	s.description like '%o%'
and	s.description like '%u%'
and	(v.color like 'GOLD'
or	per.race like 'BLACK'
and	per.gender like 'F')
and 	v.type_vehicle like '%Automobile%';

-- query 5 (1-2 sec)
select	substring(date_stop, 7, 4), count(s1.id_infraccio) as nincidents
from	sanciona as s1 natural join accident as a1
where	(date_stop like '05/07/%'
or	date_stop like '04/07/%')
and	a1.alcohol is true
group by substring(date_stop, 7, 4)
order by nincidents desc
limit 1;

-- query 6 (5-6 sec)
select	per.gender as genere, count(i.id_infraccio) as "nincidents"
from	persona as per natural join
	sanciona as s natural join infraccio as i
group by gender	
order by nincidents desc
limit 1;

-- query 7 (6 sec)
select	per.gender as genere,
(count(i.id_infraccio)/cast((select(count(i2.id_infraccio))
					from infraccio as i2) as float))*100 as nincidents
from	persona as per natural join
	sanciona as s natural join infraccio as i
group by gender;

-- query 8 (4 sec)
select p.id_propietari, p.nom, p.cognom1, p.cognom2,
	(24 - 2*(count(case when a.belts = true then id_propietari end))
	- count(case when a.alcohol = true then id_propietari end)) as punts_restants
from	propietari as p, sanciona as s natural join accident as a
where	p.id_propietari = s.id_persona
and	nom is not null
group by p.id_propietari
order by punts_restants asc
limit	5;

-- query 9 (6 sec)
select	p.*, count(v.license) as nvehicles
from	propietari as p natural join vehicle as v
group by p.id_propietari
having	count(v.license) >= all(select	count(v.license)
				from	vehicle as v
				group by v.id_propietari);

-- query 10 (-1 sec)
select	v.make, v.type_vehicle, count(v.license) as ncotxes
from	vehicle as v
group by v.make, v.type_vehicle
order by ncotxes desc
limit 5;

-- fi de la fase 2