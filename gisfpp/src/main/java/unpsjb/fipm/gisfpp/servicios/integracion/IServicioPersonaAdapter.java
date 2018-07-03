package unpsjb.fipm.gisfpp.servicios.integracion;


import org.springframework.dao.DataAccessException;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.integracion.entidades.PersonaAdapter;
import unpsjb.fipm.gisfpp.servicios.IServicioGenerico;

public interface IServicioPersonaAdapter extends IServicioGenerico<PersonaAdapter, Integer>{

	/**
	 * 
	 * @param personaAdapter
	 * @return Id de la persona en GISFPP
	 * @throws DataAccessException
	 * @throws Exception
	 */
	int actualizarOguardar(PersonaAdapter personaAdapter)throws DataAccessException, Exception;

	int existe(String legajo) throws Exception;
	
	PersonaFisica getPFxLegajo(String legajo) throws Exception;

	PersonaAdapter getPAxNroInscripcion(String nro_inscripcion);
}
