select persona.nombre from persona;

select rol,count(*) from staff_fi group by rol;

select persona.nombre, staff_fi.rol, staff_fi.desde, staff_fi.hasta from persona inner join staff_fi ON persona.idPersona = staff_fi.personaId;