INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	7;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	11;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	14;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	18;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	23;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	24;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	35;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	41;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	49;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	58;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	61;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	62;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	74;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	78;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	79;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	88;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	92;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	93;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	96;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	103;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	108;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	112;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	115;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	120;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	121;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	122;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	126;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	134;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	137;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	158;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	159;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	160;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	161;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	167;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	168;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	169;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	173;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	187;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	190;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	191;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	192;
INSERT INTO `usuario`(`activo`, `nickname`, `password`, `personaId`) select 1,nombre,nombre, idPersona from persona where idPersona =	193;

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