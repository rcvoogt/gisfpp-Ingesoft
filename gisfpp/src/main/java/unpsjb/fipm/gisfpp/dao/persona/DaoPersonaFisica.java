package unpsjb.fipm.gisfpp.dao.persona;

import java.util.List;

import unpsjb.fipm.gisfpp.dao.DaoGenerico;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;

public interface DaoPersonaFisica extends DaoGenerico<PersonaFisica, Integer> {

	public List<PersonaFisica> recuperarxApellido(String patron) throws Exception;

	public List<PersonaFisica> filtrarxDni(String patron) throws Exception;

	public List<PersonaFisica> filtrarxCuil(String cuil) throws Exception;

}
