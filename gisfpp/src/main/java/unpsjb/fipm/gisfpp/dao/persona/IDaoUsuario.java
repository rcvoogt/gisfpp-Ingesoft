package unpsjb.fipm.gisfpp.dao.persona;

import java.util.List;

import org.springframework.dao.DataAccessException;

import unpsjb.fipm.gisfpp.dao.DaoGenerico;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.util.security.RolUsuario;

public interface IDaoUsuario extends DaoGenerico<Usuario, Integer> {

	public Usuario getxPersona(PersonaFisica persona) throws DataAccessException;

	public Usuario getxNombreUsuario(String nickname) throws DataAccessException;

	public List<RolUsuario> getRoles(Usuario usuario) throws Exception;

}
