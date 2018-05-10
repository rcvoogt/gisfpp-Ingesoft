package unpsjb.fipm.gisfpp.servicios.persona;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.dao.persona.IDaoUsuario;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.util.security.DetalleUsuario;
import unpsjb.fipm.gisfpp.util.security.RolUsuario;

@Service("servUsuario")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServicioUsuario implements IServicioUsuario {

	private IDaoUsuario dao;
	

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public Integer persistir(Usuario instancia) throws Exception {
		dao.crear(instancia);
		return instancia.getId();
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void editar(Usuario instancia) throws Exception {
		dao.actualizar(instancia);
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void eliminar(Usuario instancia) throws Exception {
		dao.eliminar(instancia);
	}

	@Override
	@Transactional(value="gisfpp", readOnly = true)
	public Usuario getInstancia(Integer id) throws Exception {
		return getInstancia(id);
	}

	@Override
	@Transactional(value="gisfpp", readOnly = true)
	public List<Usuario> getListado() throws Exception {
		return dao.recuperarTodo();
	}
	
	@Override
	@Transactional(value="gisfpp", readOnly = true)
	public List<Usuario> getListadoAutorizado() throws Exception {
		return null;
		
	}

	@Override
	@Transactional(value="gisfpp", readOnly = true)
	public Usuario getUsuario(PersonaFisica persona) throws Exception {
		return dao.getxPersona(persona);
	}

	@Override
	@Transactional(value="gisfpp", readOnly = true)
	public Usuario getUsuario(String nickname) throws Exception {
		return dao.getxNombreUsuario(nickname);
	}

	@Override
	@Transactional(value="gisfpp", readOnly = true)
	public List<RolUsuario> getRoles(Usuario usuario) throws Exception {
		return dao.getRoles(usuario);
	}
	
	
	@Override
	@Transactional(value="gisfpp", readOnly = true)
	public List<Usuario> getUsuariosAptos(String operacion) throws Exception {
		return dao.getUsuariosAptos(operacion);
	}

	@Autowired(required = true)
	public void setDao(IDaoUsuario dao) {
		this.dao = dao;
	}

}// fin de la clase
