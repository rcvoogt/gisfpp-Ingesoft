select * FROM isfpp i WHERE i.estado = 'GENERADA';
select * FROM isfpp i WHERE i.estado = 'RECHAZADA';
select * FROM isfpp i WHERE i.estado = 'ACTIVADA';
select * FROM isfpp i WHERE i.estado = 'SUSPENDIDA';
select * FROM isfpp i WHERE i.estado = 'CANCELADA';
select * FROM isfpp i WHERE i.estado = 'CONCLUIDA';

SELECT count(*), estado FROM isfpp GROUP BY estado;

SELECT count(*), s.titulo 
FROM isfpp i
INNER JOIN sub_proyecto s ON i.subProyectoId = s.id
group by s.id;

SELECT count(*), p.titulo 
FROM isfpp i 
INNER JOIN sub_proyecto s ON i.subProyectoId = s.id 
INNER JOIN proyecto p ON s.proyectoId = p.idProyecto 
group by p.idProyecto