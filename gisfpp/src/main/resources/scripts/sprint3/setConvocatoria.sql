INSERT INTO convocatoria(id, fecha_creacion, fecha_vencimiento, mensaje, usuarioId, proyectoId) 
values (1, '2018-03-02','2018-05-02','Esta es una convocatoria de prueba para un proyecto Nº1',3,4);
INSERT INTO convocatoria(id, fecha_creacion, fecha_vencimiento, mensaje, usuarioId, proyectoId) 
values (2, '2018-05-02','2018-07-02','Esta es una convocatoria de prueba para proyecto Nº2',4,5);
INSERT INTO convocatoria(id, fecha_creacion, fecha_vencimiento, mensaje, usuarioId, proyectoId) 
values (3, '2018-04-02','2018-06-02','Esta es una convocatoria de prueba para proyecto Nº3',5,6);
INSERT INTO convocatoria(id, fecha_creacion, fecha_vencimiento, mensaje, usuarioId, proyectoId) 
values (4, '2018-04-02','2018-09-02','Esta es una convocatoria de prueba para proyecto Nº4',6,7);

INSERT INTO convocado(id, respuesta, convocatoriaId, personaId) VALUES (1,'SIN_RESPUESTA',1,15);
INSERT INTO convocado(id, respuesta, convocatoriaId, personaId) VALUES (1,'ACEPTO',1,16);
INSERT INTO convocado(id, respuesta, convocatoriaId, personaId) VALUES (1,'RECHAZO',1,17);
INSERT INTO convocado(id, respuesta, convocatoriaId, personaId) VALUES (2,'ACEPTO',2,13);
INSERT INTO convocado(id, respuesta, convocatoriaId, personaId) VALUES (2,'RECHAZO',2,5);
INSERT INTO convocado(id, respuesta, convocatoriaId, personaId) VALUES (3,'SIN_RESPUESTA',3,12);
INSERT INTO convocado(id, respuesta, convocatoriaId, personaId) VALUES (3,'RECHAZO',3,11);
INSERT INTO convocado(id, respuesta, convocatoriaId, personaId) VALUES (3,'RECHAZO',3,8);
INSERT INTO convocado(id, respuesta, convocatoriaId, personaId) VALUES (4,'ACEPTO',4,4);
INSERT INTO convocado(id, respuesta, convocatoriaId, personaId) VALUES (4,'ACEPTO',4,19);