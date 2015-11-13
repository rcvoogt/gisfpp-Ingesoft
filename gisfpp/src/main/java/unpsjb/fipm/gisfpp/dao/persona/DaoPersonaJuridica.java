package unpsjb.fipm.gisfpp.dao.persona;

import java.util.List;

import unpsjb.fipm.gisfpp.dao.DaoGenerico;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;

public interface DaoPersonaJuridica extends DaoGenerico<PersonaJuridica, Integer> {

	public List<PersonaJuridica> filtrarxCuit(String patron);
}
