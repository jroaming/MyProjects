use new_movies2; -- taula local

delimiter $$
drop procedure if exists afegeixUsuari$$
create procedure afegeixUsuari(in name varchar(255), in pass varchar(255))
begin
	set @aux = concat('create user \'', name,'\' identified by \'', pass, '\';');
    set @grants = concat('grant all on *.* to \'', name,'\' identified by \'', pass, '\';');
	-- select @aux;
    -- select @grants;
    prepare stmt1 from @aux;
	execute stmt1;
	deallocate prepare stmt1;
	prepare stmt2 from @grants;
	execute stmt2;
	deallocate prepare stmt2;
end $$
delimiter ;

delimiter $$
drop procedure if exists fesCercaUltimate$$
create procedure fesCercaUltimate(in title varchar(255), in genre varchar(255), in actor varchar(255), in director varchar(255), in country varchar(255), in ob_what varchar(255), in ob_how varchar(255))
begin
	declare cerca text;
	set cerca = concat('(select m.title as title, g.description as genre, p.name as director, m.country as country, m.imdb_score as imdb_score from movie as m, genre_movie as gm, genre as g, person as p, director as d, person as a, actor_movie as am  where m.id_movie = gm.id_movie and gm.id_genre = g.id_genre and a.id_person = am.id_actor and am.id_movie = m.id_movie and p.id_person = d.id_director and d.id_director = m.id_director');
	if genre not like '' then
		set cerca = concat(cerca, ' and genre like \'%', genre,'%\'');
    end if;
    if title not like '' then
		set cerca = concat(cerca, ' and title like \'%', title,'%\'');
    end if;
	if actor not like '' then
		set cerca = concat(cerca, ' and actor like \'%', actor,'%\'');
    end if;
	if director not like '' then
		set cerca = concat(cerca, ' and director like \'%', director,'%\'');
    end if;
	if country not like '' then
		set cerca = concat(cerca, ' and country like \'%', country,'%\'');
    end if;

	set cerca = concat(cerca, ') union (select m.title as title, \'\', \'\', m.country as country, m.imdb_score as imdb_score from movie as m where (m.id_director not in (select d1.id_director from director as d1) or (m.id_movie not in(select am1.id_movie from actor_movie as am1) or m.id_movie not in (select gm1.id_movie from genre_movie as gm1)))');
	if title not like '' then
		set cerca = concat(cerca, ' and title like \'%', title,'%\'');
    end if;
    if country not like '' then
		set cerca = concat(cerca, ' and country like \'%', country,'%\'');
    end if;
	set cerca = concat(cerca,') order by title asc limit 10;');


    set @aux = cerca;
    prepare stmt1 from @aux;
    execute stmt1;
    deallocate prepare stmt1;

end $$
delimiter ;
