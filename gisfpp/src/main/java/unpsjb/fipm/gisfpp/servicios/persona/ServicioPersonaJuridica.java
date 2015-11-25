package unpsjb.fipm.gisfpp.servicios.persona;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import unpsjb.fipm.gisfpp.dao.persona.IDaoPersonaJuridica;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;

@Service("servPersonaJuridica")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServicioPersonaJuridica implements IServicioPersonaJuridica {

	private IDaoPersonaJuridica dao;

	@Override
	public Integer nuevaPersonaJuridica(PersonaJuridica persona) throws Exception {
		try {
			return dao.crear(persona);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void actualizarPersonaJuridica(PersonaJuridica persona) throws Exception {
		try {
			dao.actualizar(persona);
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public void eliminarPersonaJuridica(PersonaJuridica persona) throws Exception {
		try {
			dao.eliminar(persona);
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public List<PersonaJuridica> recuperarTodo() throws Exception {
		try {
			return dao.recuperarTodo();
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public PersonaJuridica recuperarxId(Integer id) throws Exception {
		try {
			return dao.recuperarxId(id);
		} catch (Exception e) {
			throw e;
		}
	}

	@Autowired(required = true)
	public void setDao(IDaoPersonaJuridica dao) {
		this.dao = dao;
	}

}// fin de la clase
