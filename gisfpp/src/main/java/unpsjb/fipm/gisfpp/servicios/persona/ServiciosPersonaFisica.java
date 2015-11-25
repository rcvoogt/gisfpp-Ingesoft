package unpsjb.fipm.gisfpp.servicios.persona;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import unpsjb.fipm.gisfpp.dao.persona.IDaoPersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;

@Service("servPersonaFisica")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServiciosPersonaFisica implements IServiciosPersonaFisica {

	private IDaoPersonaFisica dao;

	@Override
	public Integer nuevaPersonaFisica(PersonaFisica persona) throws Exception {
		try {
			dao.crear(persona);
			return persona.getId();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void actualizarPersonaFisica(PersonaFisica persona) throws Exception {
		try {
			dao.actualizar(persona);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void eliminarPersonaFisica(PersonaFisica persona) throws Exception {
		try {
			dao.eliminar(persona);
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public List<PersonaFisica> recuperarTodo() throws Exception {
		try {
			return dao.recuperarTodo();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public PersonaFisica recuperarxId(Integer id) throws Exception {
		try {
			return dao.recuperarxId(id);
		} catch (Exception e) {
			throw e;
		}
	}

	@Autowired(required = false)
	public void setDao(IDaoPersonaFisica dao) {
		this.dao = dao;
	}

}
