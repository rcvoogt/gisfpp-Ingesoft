CREATE OR REPLACE
    ALGORITHM = UNDEFINED 
    DEFINER = `user_gisfpp`@`localhost` 
    SQL SECURITY DEFINER
VIEW `roles_usuario` AS
    SELECT 
        `p`.`idPersona` AS `PersonaId`,
        `stf`.`rol` AS `Rol`,
        'Staff-Fi' AS `Titulo`,
        'STAFF-FI' AS `Entidad`,
        `stf`.`id` AS `IdEntidad`
    FROM
        (`persona` `p`
        JOIN `staff_fi` `stf` ON ((`p`.`idPersona` = `stf`.`personaId`)))
    WHERE
        ((`p`.`tipo` = 'PF')
            AND (CURDATE() <= `stf`.`hasta`)) 
    UNION SELECT 
        `prs`.`idPersona` AS `idPersona`,
        `stf_pry`.`rol` AS `rol`,
        `pry`.`titulo` AS `titulo`,
        'PROYECTO' AS `proyecto`,
        `pry`.`idProyecto` AS `idProyecto`
    FROM
        ((`persona` `prs`
        JOIN `staff_proyecto` `stf_pry` ON ((`prs`.`idPersona` = `stf_pry`.`personaId`)))
        JOIN `proyecto` `pry` ON ((`stf_pry`.`proyectoId` = `pry`.`idProyecto`))) 
    UNION SELECT 
        `prs`.`idPersona` AS `idPersona`,
        `stf_isfpp`.`rol` AS `rol`,
        `isfpp`.`titulo` AS `titulo`,
        'ISFPP' AS `isfpp`,
        `isfpp`.`id` AS `id`
    FROM
        ((`persona` `prs`
        JOIN `staff_isfpp` `stf_isfpp` ON ((`prs`.`idPersona` = `stf_isfpp`.`personaId`)))
        JOIN `isfpp` ON ((`stf_isfpp`.`isfppId` = `isfpp`.`id`))) 
    UNION SELECT 
        `prs`.`idPersona` AS `idPersona`,
        'DEMANDANTE_PROYECTO' AS `rol`,
        `pry`.`titulo` AS `titulo`,
        'PROYECTO' AS `proyecto`,
        `pry`.`idProyecto` AS `idProyecto`
    FROM
        ((`persona` `prs`
        JOIN `demandantes` `dmts` ON ((`prs`.`idPersona` = `dmts`.`personaId`)))
        JOIN `proyecto` `pry` ON ((`dmts`.`proyectoId` = `pry`.`idProyecto`))) 
    UNION SELECT 
        `prs`.`idPersona` AS `idPersona`,
        'PRACTICANTE' AS `rol`,
        `isfpp`.`titulo` AS `titulo`,
        'ISFPP' AS `isfpp`,
        `isfpp`.`id` AS `idIsfpp`
    FROM
        ((`persona` `prs`
        JOIN `practicantes` `prtcs` ON ((`prs`.`idPersona` = `prtcs`.`personaId`)))
        JOIN `isfpp` ON ((`prtcs`.`isfppId` = `isfpp`.`id`)))