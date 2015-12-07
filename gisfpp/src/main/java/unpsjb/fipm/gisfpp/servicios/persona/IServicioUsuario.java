package unpsjb.fipm.gisfpp.servicios.persona;

import java.util.List;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.servicios.IServicioGenerico;
import unpsjb.fipm.gisfpp.util.security.RolUsuario;

public interface IServicioUsuario extends IServicioGenerico<Usuario, Integer> {

	public Usuario getUsuario(PersonaFisica persona) throws Exception;

	public Usuario getUsuario(String nickname) throws Exception;

	public List<RolUsuario> getRoles(Usuario usuario) throws Exception;
}
