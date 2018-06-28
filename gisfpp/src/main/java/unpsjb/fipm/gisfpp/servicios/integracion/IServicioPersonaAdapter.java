package unpsjb.fipm.gisfpp.servicios.integracion;


import org.springframework.dao.DataAccessException;

import unpsjb.fipm.gisfpp.integracion.entidades.PersonaAdapter;
import unpsjb.fipm.gisfpp.servicios.IServicioGenerico;

public interface IServicioPersonaAdapter extends IServicioGenerico<PersonaAdapter, Integer>{


	int actualizarOguardar(PersonaAdapter personaAdapter)throws DataAccessException, Exception;

	int existe(String legajo) throws Exception;
}
