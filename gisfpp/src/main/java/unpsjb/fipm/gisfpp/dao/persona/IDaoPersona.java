package unpsjb.fipm.gisfpp.dao.persona;

import java.util.List;

import org.springframework.dao.DataAccessException;

import unpsjb.fipm.gisfpp.dao.DaoGenerico;
import unpsjb.fipm.gisfpp.entidades.persona.Persona;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;

public interface IDaoPersona extends DaoGenerico<Persona, Integer> {

	public List<PersonaFisica> getPersonasFisicas() throws DataAccessException;

	public List<PersonaJuridica> getPersonasJuridicas() throws DataAccessException;
}
