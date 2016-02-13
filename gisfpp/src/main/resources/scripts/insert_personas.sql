INSERT INTO `persona` (`nombre`,`tipo`) VALUES ("Devia Jose Luis","PF");
INSERT INTO `persona` (`nombre`,`tipo`) VALUES ("Barry Demián","PF");
INSERT INTO `persona` (`nombre`,`tipo`) VALUES ("Aluar S.A","PJ");
INSERT INTO `persona` (`nombre`,`tipo`) VALUES ("Infa S.A","PJ");
INSERT INTO `persona` (`nombre`,`tipo`) VALUES ("Municipalidad de Puerto Madryn","PJ");
INSERT INTO `persona` (`nombre`,`tipo`) VALUES ("Universidad Nacional de la Patagonia San Juan Bosco","PJ");
INSERT INTO `persona` (`nombre`,`tipo`) VALUES ("Milicic S.A","PJ");
INSERT INTO `persona` (`nombre`,`tipo`) VALUES ("Electroingenieria S.A","PJ");
INSERT INTO `persona` (`nombre`,`tipo`) VALUES ("CONICET Puerto Madryn","PJ");
INSERT INTO `persona` (`nombre`,`tipo`) VALUES ("Aerolineas Argentinas S.A","PJ");
INSERT INTO `persona` (`nombre`,`tipo`) VALUES ("YPF S.A","PJ");
INSERT INTO `persona` (`nombre`,`tipo`) VALUES ("Halliburton S.A","PJ");


INSERT INTO `usuario` (`activo`,`nickname`,`password`,`personaId`) VALUES (1,"jldevia","fidelina",1);
INSERT INTO `usuario` (`activo`,`nickname`,`password`,`personaId`) VALUES (1,"dbarry","gisfpp",2);

insert into datos_contacto (tipo, valor,personaId) values ('EMAIL','jldevia81@gmail.com',1);
insert into datos_contacto (tipo, valor,personaId) values ('EMAIL','demian.barry@gmail.com',2);

INSERT INTO `staff_fi` (desde, hasta,rol, personaId)VALUES('2016-01-01','2016-12-31','AUXILIAR',1);
INSERT INTO `staff_fi` (desde, hasta,rol, personaId)VALUES('2016-01-01','2016-12-31','COORDINADOR',2);

