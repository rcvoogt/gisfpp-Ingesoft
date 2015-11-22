package unpsjb.fipm.gisfpp.servicios.persona;

import java.util.List;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;

public interface IServicioUsuario {

	public Integer nuevoUsuario(Usuario usuario) throws Exception;

	public void editarUsuario(Usuario usuario) throws Exception;

	public void eliminarUsuario(Usuario usuario) throws Exception;

	public List<Usuario> recuperarTodos() throws Exception;

	public Usuario recupararxPersona(PersonaFisica persona) throws Exception;

	public Usuario recupararxNombreUsuario(String nickname) throws Exception;

}
