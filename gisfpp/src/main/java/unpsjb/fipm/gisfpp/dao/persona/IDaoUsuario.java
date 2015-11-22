package unpsjb.fipm.gisfpp.dao.persona;

import org.springframework.dao.DataAccessException;

import unpsjb.fipm.gisfpp.dao.DaoGenerico;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;

public interface IDaoUsuario extends DaoGenerico<Usuario, Integer> {

	public Usuario getxPersona(PersonaFisica persona) throws DataAccessException;

	public Usuario getxNombreUsuario(String nickname) throws DataAccessException;
}
