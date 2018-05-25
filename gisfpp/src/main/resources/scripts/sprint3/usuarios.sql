-- Crear usuarios para las personas que aun no tengan y que no esten duplicadas:
insert into usuario (activo, nickname, password,personaId) 
select 1,nombre, nombre,idPersona 
	from persona 
	-- Que no tenga usuario ya creado
	where idPersona not in (select personaId from usuario) 
		-- Que no este duplicado sino tira error de UK en la tabla de usuarios
		and idPersona not in (SELECT per1.idPersona
								FROM persona per1
								   INNER JOIN (SELECT per2.idPersona, per2.nombre
								               FROM   persona per2
								               GROUP  BY per2.nombre
								               HAVING COUNT(per2.nombre) > 1) dup
								           ON per1.nombre = dup.nombre)