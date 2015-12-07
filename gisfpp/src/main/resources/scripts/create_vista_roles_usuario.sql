CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `user_gisfpp`@`localhost` 
    SQL SECURITY DEFINER
VIEW `roles_usuario` AS
    (SELECT 
        `p`.`idPersona` AS `PersonaId`,
        `stf`.`rol` AS `Rol`,
        'Staff-Fi' AS `En`,
        'staff_fi' AS `Relacion`,
        `stf`.`id` AS `RelacionId`
    FROM
        (`persona` `p`
        JOIN `staff_fi` `stf` ON ((`p`.`idPersona` = `stf`.`personaId`)))
    WHERE
        ((`p`.`tipo` = 'PF')
            AND (CURDATE() <= `stf`.`hasta`)))