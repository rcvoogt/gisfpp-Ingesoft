package unpsjb.fipm.gisfpp.dao.persona;

import java.util.List;

import unpsjb.fipm.gisfpp.dao.DaoGenerico;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;

public interface IDaoPersonaJuridica extends DaoGenerico<PersonaJuridica, Integer> {

	public List<PersonaJuridica> getxNombre(String patron) throws Exception;
	
	public List<PersonaJuridica> getxCuit (String patron) throws Exception;
	
}
