SELECT per1.idPersona, 
   per1.nombre 
FROM persona per1
   INNER JOIN (SELECT per2.idPersona, per2.nombre
               FROM   persona per2
               GROUP  BY per2.nombre
               HAVING COUNT(per2.nombre) > 1) dup
           ON per1.nombre = dup.nombre;