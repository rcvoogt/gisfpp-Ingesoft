INSERT INTO `isfpp`(`id`, `detalle`, `estado`, `fecha_fin`, `fecha_inicio`, `objetivos`, `titulo`, `subProyectoId`)
VALUES (2,' ','GENERADA','2018-12-12','2018-02-12','','Sistema en Grid para cultivos',2),
(3,' ','RECHAZADA','2018-12-12','2018-03-12','','CORREL. GEOLÓGICAS Y PALEOMAG',3),
(4,' ','CONCLUIDA','2018-12-12','2018-01-12','','PRODUCCION DE COLZA-CANOLA',4),
(5,' ','SUSPENDIDA','2018-12-12','2018-01-12','','DESARROLLO MODULOS CLIMATICOS',5),
(6,' ','ACTIVA','2018-12-12','2018-01-12','','Plataforma para ACC',6),
(7,' ','ACTIVA','2018-12-12','2018-03-12','','Ce-Bios',7),
(8,' ','ACTIVA','2018-12-12','2018-04-12','','Arandu Recursos Biológicos',8),
(9,' ','SUSPENDIDA','2018-12-12','2018-05-12','','Predictor de CiberAtacantes',9),
(10,' ','SUSPENDIDA','2018-12-12','2018-08-12','','Monitoreo de ganado ovino',10),
(11,' ','GENERADA','2018-12-12','2018-01-12','','Edificios públicos eficientes',11),
(12,' ','ACTIVA','2018-12-12','2018-02-12','','TIC EN LA AGROINDUSTRIA',12),
(13,' ','SUSPENDIDA','2018-12-12','2018-02-12','','Optimización Valor reciclado',13),
(14,' ','ACTIVA','2018-12-12','2018-07-12','','Resiliencia Urbana',14),
(15,' ','CONCLUIDA','2018-12-12','2018-09-12','','Construyendo ciudadanía',15),
(16,' ','ACTIVA','2018-12-12','2018-03-12','','Moda y cuerpos disidentes',16),
(17,' ','ACTIVA','2018-12-12','2018-02-12','','SUSTENTABILIDAD Y TABACO',17),
(18,' ','GENERADA','2018-12-12','2018-04-12','','Sistema de riego en Azampay',18),
(19,' ','GENERADA','2018-12-12','2018-03-12','','Energía renovable con biomasa',19),
(20,' ','SUSPENDIDA','2018-12-12','2018-05-12','','Alperujos y calidad de carne',20),
(21,' ','ACTIVA','2018-12-12','2018-09-12','','Colágeno para usos biomédicos',21),
(22,' ','CONCLUIDA','2018-12-12','2018-01-12','','APROVECHAMIENTO DE LACTOSUERO',22),
(23,' ','GENERADA','2018-12-12','2018-02-12','','OpenSIG de Suelos de Córdoba',23),
(24,' ','SUSPENDIDA','2018-12-12','2018-05-12','','Nutracéuticos innovadores',24),
(25,' ','ACTIVA','2018-12-12','2018-04-12','','SIEECObservatorio emprendedor',25),
(26,' ','GENERADA','2018-12-12','2018-03-12','','Sistema para cultivo orienta',26),
(28,' ','SUSPENDIDA','2018-12-12','2018-01-12','','Sistema de riego en Azampay parte dos',18);

INSERT INTO `practicantes`(`isfppId`, `personaId`) VALUES (2,4);
INSERT INTO `practicantes`(`isfppId`, `personaId`) VALUES (2,5);
INSERT INTO `practicantes`(`isfppId`, `personaId`) VALUES (2,6);
INSERT INTO `practicantes`(`isfppId`, `personaId`) VALUES (2,7);
INSERT INTO `practicantes`(`isfppId`, `personaId`) VALUES (2,8);
INSERT INTO `practicantes`(`isfppId`, `personaId`) VALUES (2,9);

INSERT INTO `staff_isfpp`(`idStaffIsfpp`, `rol`, `isfppId`, `personaId`) VALUES (1,"TUTOR_ACADEMICO",2,41);
INSERT INTO `staff_isfpp`(`idStaffIsfpp`, `rol`, `isfppId`, `personaId`) VALUES (2,"TUTOR_EXTERNO",2,44);
