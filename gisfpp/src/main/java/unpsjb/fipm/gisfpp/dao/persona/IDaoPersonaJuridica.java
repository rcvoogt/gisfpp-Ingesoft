package unpsjb.fipm.gisfpp.dao.persona;

import java.util.List;

import org.springframework.dao.DataAccessException;

import unpsjb.fipm.gisfpp.dao.DaoGenerico;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaDeContacto;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;

public interface IDaoPersonaJuridica extends DaoGenerico<PersonaJuridica, Integer> {

	public void agregarPersonaContacto(PersonaDeContacto contacto) throws DataAccessException;

	public void quitarPersonaContacto(PersonaDeContacto contacto) throws DataAccessException;

	public List<PersonaDeContacto> getPersonasContacto(PersonaJuridica organizacion) throws DataAccessException;

}
