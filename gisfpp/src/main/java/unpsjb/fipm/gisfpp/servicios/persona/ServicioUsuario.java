package unpsjb.fipm.gisfpp.servicios.persona;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import unpsjb.fipm.gisfpp.dao.persona.IDaoUsuario;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServicioUsuario implements IServicioUsuario {

	private IDaoUsuario dao;

	@Override
	public Integer nuevoUsuario(Usuario usuario) throws Exception {
		return dao.crear(usuario);
	}

	@Override
	public void editarUsuario(Usuario usuario) throws Exception {
		dao.actualizar(usuario);
	}

	@Override
	public void eliminarUsuario(Usuario usuario) throws Exception {
		dao.eliminar(usuario);
	}

	@Override
	public List<Usuario> recuperarTodos() throws Exception {
		return dao.recuperarTodo();
	}

	@Override
	public Usuario recupararxPersona(PersonaFisica persona) throws Exception {
		return dao.getxPersona(persona);
	}

	@Override
	public Usuario recupararxNombreUsuario(String nickname) throws Exception {
		return dao.getxNombreUsuario(nickname);
	}

	@Autowired(required = true)
	public void setDao(IDaoUsuario dao) {
		this.dao = dao;
	}

}// fin de la clase
