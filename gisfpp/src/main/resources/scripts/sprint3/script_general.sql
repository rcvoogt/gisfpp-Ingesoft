delete from staff_fi;
delete from datos_contacto;
delete from persona;



insert into persona (tipo,idPersona, nombre) values ('PF',1, 'Normand');
insert into persona (tipo,idPersona, nombre) values ('PF',2, 'Benyamin');
insert into persona (tipo,idPersona, nombre) values ('PF',3, 'Dave');
insert into persona (tipo,idPersona, nombre) values ('PF',4, 'Sidney');
insert into persona (tipo,idPersona, nombre) values ('PF',5, 'Jakob');
insert into persona (tipo,idPersona, nombre) values ('PF',6, 'Hilly');
insert into persona (tipo,idPersona, nombre) values ('PF',7, 'Leonard');
insert into persona (tipo,idPersona, nombre) values ('PF',8, 'Ingra');
insert into persona (tipo,idPersona, nombre) values ('PF',9, 'Phil');
insert into persona (tipo,idPersona, nombre) values ('PF',10, 'Aguie');
insert into persona (tipo,idPersona, nombre) values ('PF',11, 'Daniel');
insert into persona (tipo,idPersona, nombre) values ('PF',12, 'Adolphus');
insert into persona (tipo,idPersona, nombre) values ('PF',13, 'Marcello');
insert into persona (tipo,idPersona, nombre) values ('PF',14, 'Marlow');
insert into persona (tipo,idPersona, nombre) values ('PF',15, 'Chane');
insert into persona (tipo,idPersona, nombre) values ('PF',16, 'Tibold');
insert into persona (tipo,idPersona, nombre) values ('PF',17, 'Erick');
insert into persona (tipo,idPersona, nombre) values ('PF',18, 'Vernor');
insert into persona (tipo,idPersona, nombre) values ('PF',19, 'Yorgo');
insert into persona (tipo,idPersona, nombre) values ('PF',20, 'Guy');

insert into datos_contacto (id, tipo, valor, personaId) values ('1', 'EMAIL', 'arza.isaias@gmail.com', 1);
insert into datos_contacto (id, tipo, valor, personaId) values ('2', 'EMAIL', 'arza.isaias@gmail.com', 2);
insert into datos_contacto (id, tipo, valor, personaId) values ('3', 'EMAIL', 'arza.isaias@gmail.com', 3);
insert into datos_contacto (id, tipo, valor, personaId) values ('4', 'EMAIL', 'arza.isaias@gmail.com', 4);
insert into datos_contacto (id, tipo, valor, personaId) values ('5', 'EMAIL', 'arza.isaias@gmail.com', 5);
insert into datos_contacto (id, tipo, valor, personaId) values ('6', 'EMAIL', 'robertoc.voogt@gmail.com', 6);
insert into datos_contacto (id, tipo, valor, personaId) values ('7', 'EMAIL', 'robertoc.voogt@gmail.com', 7);
insert into datos_contacto (id, tipo, valor, personaId) values ('8', 'EMAIL', 'robertoc.voogt@gmail.com', 8);
insert into datos_contacto (id, tipo, valor, personaId) values ('9', 'EMAIL', 'robertoc.voogt@gmail.com', 9);
insert into datos_contacto (id, tipo, valor, personaId) values ('10', 'EMAIL', 'robertoc.voogt@gmail.com', 10);
insert into datos_contacto (id, tipo, valor, personaId) values ('11', 'EMAIL', 'savarromauricio@gmail.com', 11);
insert into datos_contacto (id, tipo, valor, personaId) values ('12', 'EMAIL', 'savarromauricio@gmail.com', 12);
insert into datos_contacto (id, tipo, valor, personaId) values ('13', 'EMAIL', 'savarromauricio@gmail.com', 13);
insert into datos_contacto (id, tipo, valor, personaId) values ('14', 'EMAIL', 'savarromauricio@gmail.com', 14);
insert into datos_contacto (id, tipo, valor, personaId) values ('15', 'EMAIL', 'savarromauricio@gmail.com', 15);
insert into datos_contacto (id, tipo, valor, personaId) values ('16', 'EMAIL', 'lucasboba@gmail.com', 16);
insert into datos_contacto (id, tipo, valor, personaId) values ('17', 'EMAIL', 'lucasboba@gmail.com', 17);
insert into datos_contacto (id, tipo, valor, personaId) values ('18', 'EMAIL', 'lucasboba@gmail.com', 18);
insert into datos_contacto (id, tipo, valor, personaId) values ('19', 'EMAIL', 'lucasboba@gmail.com', 19);
insert into datos_contacto (id, tipo, valor, personaId) values ('20', 'EMAIL', 'lucasboba@gmail.com', 20);

  insert into staff_fi (id, personaId, ROL, desde, hasta ) values (1, 1,'PROFESOR','2017,12,1','2021,12,1');
  insert into staff_fi (id, personaId, ROL, desde, hasta ) values (2, 2,'PROFESOR','2017,12,1','2021,12,1');
  insert into staff_fi (id, personaId, ROL, desde, hasta ) values (3, 3,'PROFESOR','2017,12,1','2021,12,1');
  insert into staff_fi (id, personaId, ROL, desde, hasta ) values (4, 4,'INVESTIGADOR','2017,12,1','2021,12,1');
  insert into staff_fi (id, personaId, ROL, desde, hasta ) values (5, 5,'INVESTIGADOR','2017,12,1','2021,12,1');
  insert into staff_fi (id, personaId, ROL, desde, hasta ) values (6, 6,'ALUMNO','2017,12,1','2021,12,1');
  insert into staff_fi (id, personaId, ROL, desde, hasta ) values (7, 7,'ALUMNO','2017,12,1','2021,12,1');
  insert into staff_fi (id, personaId, ROL, desde, hasta ) values (8, 8,'AUXILIAR','2017,12,1','2021,12,1');
  insert into staff_fi (id, personaId, ROL, desde, hasta ) values (9, 9,'AUXILIAR','2017,12,1','2021,12,1');
  insert into staff_fi (id, personaId, ROL, desde, hasta ) values (10, 10,'JTP','2017,12,1','2021,12,1');
  insert into staff_fi (id, personaId, ROL, desde, hasta ) values (11, 11,'JTP','2017,12,1','2021,12,1');
  insert into staff_fi (id, personaId, ROL, desde, hasta ) values (12, 12,'ALUMNO','2017,12,1','2021,12,1');
  insert into staff_fi (id, personaId, ROL, desde, hasta ) values (13, 13,'ALUMNO','2017,12,1','2021,12,1');
  insert into staff_fi (id, personaId, ROL, desde, hasta ) values (14, 14,'COORDINADOR','2017,12,1','2021,12,1');
  insert into staff_fi (id, personaId, ROL, desde, hasta ) values (15, 15,'DELEGADO','2017,12,1','2021,12,1');
  insert into staff_fi (id, personaId, ROL, desde, hasta ) values (16, 16,'JTP','2017,12,1','2021,12,1');
  insert into staff_fi (id, personaId, ROL, desde, hasta ) values (17, 17,'JTP','2017,12,1','2021,12,1');
  insert into staff_fi (id, personaId, ROL, desde, hasta ) values (18, 18,'ALUMNO','2017,12,1','2021,12,1');
  insert into staff_fi (id, personaId, ROL, desde, hasta ) values (19, 19,'ALUMNO','2017,12,1','2021,12,1');
  insert into staff_fi (id, personaId, ROL, desde, hasta ) values (20, 20,'COORDINADOR','2017,12,1','2021,12,1');

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

 INSERT INTO `proyecto`(`idProyecto`, `codigo`, `descripcion`, `detalle`, `estado`,
  `fecha_inicio`, `fecha_fin`, `num_resolucion`, `tipo`, `titulo`)
 VALUES (1,1425,'Proyecto concluido Nº1','Datos para pruebas de aceptación ','CONCLUIDO' ,'2017-03-01' ,'2017-09-01' ,'Resolucion Nº 0001', 'EXTENSION' ,'Sistema Monitoreo invernáculo'),
 (2,9115,'Proyecto concluido Nº2','Datos para pruebas de aceptación','CONCLUIDO' ,'2017-03-01' ,'2017-09-01' ,'Resolucion Nº 0002', 'EXTENSION' ,'Sistema en Grid para cultivos'),
 (3,8926,'Proyecto concluido Nº3','Datos para pruebas de aceptación','CONCLUIDO','2017-03-01' ,'2017-09-01' ,'Resolucion Nº 0003', 'EXTENSION' ,'CORREL. GEOLÓGICAS Y PALEOMAG'),
 (4,9537,'Proyecto activo Nº1','Datos para pruebas de aceptación','ACTIVO' ,'2017-11-01' ,'2019-09-01' ,'Resolucion Nº 0004', 'EXTENSION' ,'PRODUCCION DE COLZA-CANOLA'),
 (5,10418,'Proyecto activo Nº2','Datos para pruebas de aceptación','ACTIVO' ,'2017-11-01' ,'2019-09-01' ,'Resolucion Nº 0005', 'INVESTIGACION' ,'DESARROLLO MODULOS CLIMATICOS'),
 (6,9919,'Proyecto activo Nº3','Datos para pruebas de aceptación','ACTIVO' ,'2017-11-01' ,'2019-09-01' ,'Resolucion Nº 0006', 'INVESTIGACION' ,'Plataforma para ACC'),
 (7,8505,'Proyecto activo Nº4','Datos para pruebas de aceptación','ACTIVO' ,'2017-11-01' ,'2019-09-01' ,'Resolucion Nº 0007', 'INTERNO' ,'Ce-Bios'),
 (8,9845,'Proyecto activo Nº5','Datos para pruebas de aceptación','ACTIVO' ,'2017-11-01' ,'2019-09-01' ,'Resolucion Nº 0008', 'INTERNO' ,'Arandu Recursos Biológicos'),
 (9,10692,'Proyecto activo Nº6','Datos para pruebas de aceptación','ACTIVO' ,'2017-11-01' ,'2019-09-01' ,'Resolucion Nº 0009', 'EMPRESA' ,'Predictor de CiberAtacantes'),
 (10,11111,'Proyecto activo Nº7','Datos para pruebas de aceptación','ACTIVO' ,'2017-11-01' ,'2019-09-01' ,'Resolucion Nº 0010', 'EMPRESA' ,'Monitoreo de ganado ovino');

 insert into staff_proyecto (idStaffProyecto, rol, personaId, proyectoId) values (2, 'RESPONSABLE_PROYECTO', 1,1);
 insert into staff_proyecto (idStaffProyecto, rol, personaId, proyectoId) values (3, 'RESPONSABLE_PROYECTO', 2,2);
 insert into staff_proyecto (idStaffProyecto, rol, personaId, proyectoId) values (4, 'RESPONSABLE_PROYECTO', 3,3);
 insert into staff_proyecto (idStaffProyecto, rol, personaId, proyectoId) values (5, 'RESPONSABLE_PROYECTO', 4,4);
 insert into staff_proyecto (idStaffProyecto, rol, personaId, proyectoId) values (6, 'RESPONSABLE_PROYECTO', 5,5);
 insert into staff_proyecto (idStaffProyecto, rol, personaId, proyectoId) values (7, 'RESPONSABLE_PROYECTO', 6,6);
 insert into staff_proyecto (idStaffProyecto, rol, personaId, proyectoId) values (8, 'RESPONSABLE_PROYECTO', 7,7);
 insert into staff_proyecto (idStaffProyecto, rol, personaId, proyectoId) values (9, 'RESPONSABLE_PROYECTO', 8,8);
 insert into staff_proyecto (idStaffProyecto, rol, personaId, proyectoId) values (10, 'RESPONSABLE_PROYECTO', 9,9);
 insert into staff_proyecto (idStaffProyecto, rol, personaId, proyectoId) values (1, 'RESPONSABLE_PROYECTO', 10,10);
   
 INSERT INTO `sub_proyecto`(`Id`, `descripcion`, `detalle`, `titulo`, `proyectoId`)
 VALUES (1,'Subproyecto Nº1','Datos para prueba de aceptación','Sistema Monitoreo invernáculo',4),
 (2,'Subproyecto Nº2','Datos para prueba de aceptación','Sistema en Grid para cultivos',4),
 (3,'Subproyecto Nº3','Datos para prueba de aceptación','CORREL. GEOLÓGICAS Y PALEOMAG',4),
 (4,'Subproyecto Nº4','Datos para prueba de aceptación','PRODUCCION DE COLZA-CANOLA',4),
 (5,'Subproyecto Nº5','Datos para prueba de aceptación','DESARROLLO MODULOS CLIMATICOS',5),
 (6,'Subproyecto Nº6','Datos para prueba de aceptación','Plataforma para ACC',6),
 (7,'Subproyecto Nº7','Datos para prueba de aceptación','Ce-Bios',7),
 (8,'Subproyecto Nº8','Datos para prueba de aceptación','Arandu Recursos Biológicos',6),
 (9,'Subproyecto Nº9','Datos para prueba de aceptación','Predictor de CiberAtacantes',6),
(10,'Subproyecto Nº10','Datos para prueba de aceptación','Monitoreo de ganado ovino',7);

INSERT INTO `isfpp`(`id`, `detalle`, `estado`, `fecha_fin`, `fecha_inicio`, `objetivos`, `titulo`, `subProyectoId`)
VALUES (1,'Isfpp creada de forma estatica, para probar datos','GENERADA','2018-12-12','2018-02-12','Isfpp creada de forma estatica, para probar datos','Sistema en Grid para cultivos',2),
(2,'Isfpp creada de forma estatica, para probar datos','GENERADA','2018-12-12','2018-02-12','Isfpp creada de forma estatica, para probar datos','Sistema en Grid para cultivos 2',2),
(3,'Isfpp creada de forma estatica, para probar datos','RECHAZADA','2018-12-12','2018-03-12','Isfpp creada de forma estatica, para probar datos','CORREL. GEOLÓGICAS Y PALEOMAG',3),
(4,'Isfpp creada de forma estatica, para probar datos','CONCLUIDA','2018-12-12','2018-01-12','Isfpp creada de forma estatica, para probar datos','PRODUCCION DE COLZA-CANOLA',4),
(5,'Isfpp creada de forma estatica, para probar datos','SUSPENDIDA','2018-12-12','2018-01-12','Isfpp creada de forma estatica, para probar datos','DESARROLLO MODULOS CLIMATICOS',5);

INSERT INTO `staff_isfpp`(`idStaffIsfpp`, `rol`, `isfppId`, `personaId`)
VALUES (1,'TUTOR_ACADEMICO',1,1),
(2,'TUTOR_EXTERNO',1,2),
(3,'TUTOR_ACADEMICO',2,3),
(4,'TUTOR_EXTERNO',2,4),
(5,'TUTOR_ACADEMICO',3,5),
(6,'TUTOR_EXTERNO',3,6),
(7,'TUTOR_ACADEMICO',4,7),
(8,'TUTOR_EXTERNO',4,8),
(9,'TUTOR_ACADEMICO',5,9),
(10,'TUTOR_EXTERNO',5,10);
