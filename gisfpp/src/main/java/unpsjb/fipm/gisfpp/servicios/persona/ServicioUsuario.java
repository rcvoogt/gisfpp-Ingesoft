package unpsjb.fipm.gisfpp.servicios.persona;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.dao.persona.IDaoUsuario;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.util.security.RolUsuario;

@Service("servUsuario")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServicioUsuario implements IServicioUsuario {

	private IDaoUsuario dao;

	@Override
	@Transactional(readOnly = false)
	public Integer persistir(Usuario instancia) throws Exception {
		dao.crear(instancia);
		return instancia.getId();
	}

	@Override
	@Transactional(readOnly = false)
	public void editar(Usuario instancia) throws Exception {
		dao.actualizar(instancia);
	}

	@Override
	@Transactional(readOnly = false)
	public void eliminar(Usuario instancia) throws Exception {
		dao.eliminar(instancia);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario getInstancia(Integer id) throws Exception {
		return getInstancia(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> getListado() throws Exception {
		return dao.recuperarTodo();
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario getUsuario(PersonaFisica persona) throws Exception {
		return dao.getxPersona(persona);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario getUsuario(String nickname) throws Exception {
		return dao.getxNombreUsuario(nickname);
	}

	@Override
	@Transactional(readOnly = true)
	public List<RolUsuario> getRoles(Usuario usuario) throws Exception {
		return dao.getRoles(usuario);
	}

	@Autowired(required = true)
	public void setDao(IDaoUsuario dao) {
		this.dao = dao;
	}

}// fin de la clase
