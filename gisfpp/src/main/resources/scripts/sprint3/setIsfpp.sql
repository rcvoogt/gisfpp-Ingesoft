INSERT INTO `isfpp`(`id`, `detalle`, `estado`, `fecha_fin`, `fecha_inicio`, `objetivos`, `titulo`, `subProyectoId`)
VALUES (1,'Isfpp creada de forma estatica, para probar datos','GENERADA','2018-12-12','2018-02-12','Isfpp creada de forma estatica, para probar datos','Sistema en Grid para cultivos',2),
(2,'Isfpp creada de forma estatica, para probar datos','GENERADA','2018-12-12','2018-02-12','Isfpp creada de forma estatica, para probar datos','Sistema en Grid para cultivos',2),
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
