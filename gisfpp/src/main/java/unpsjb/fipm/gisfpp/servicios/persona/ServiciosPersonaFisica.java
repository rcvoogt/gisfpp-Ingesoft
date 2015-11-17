package unpsjb.fipm.gisfpp.servicios.persona;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.dao.persona.IDaoPersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;

@Service("servPersonaFisica")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServiciosPersonaFisica implements IServiciosPersonaFisica {

	private IDaoPersonaFisica dao;

	@Override
	@Transactional(readOnly = false)
	public Integer nuevaPersonaFisica(PersonaFisica persona) throws Exception {
		try {
			dao.crear(persona);
			return persona.getId();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void actualizarPersonaFisica(PersonaFisica persona) throws Exception {
		try {
			dao.actualizar(persona);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void eliminarPersonaFisica(PersonaFisica persona) throws Exception {
		try {
			dao.eliminar(persona);
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<PersonaFisica> recuperarTodo() throws Exception {
		try {
			return dao.recuperarTodo();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PersonaFisica recuperarxId(Integer id) throws Exception {
		try {
			return dao.recuperarxId(id);
		} catch (Exception e) {
			throw e;
		}
	}

	@Autowired(required = true)
	public void setDao(IDaoPersonaFisica dao) {
		this.dao = dao;
	}

}
