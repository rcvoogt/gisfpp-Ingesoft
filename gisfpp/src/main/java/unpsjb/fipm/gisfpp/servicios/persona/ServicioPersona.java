package unpsjb.fipm.gisfpp.servicios.persona;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.dao.persona.IDaoPersona;
import unpsjb.fipm.gisfpp.entidades.persona.Persona;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;

@Service("servPersona")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServicioPersona implements IServicioPersona {

	IDaoPersona dao;

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public Integer nuevaPersona(Persona persona) throws Exception {
		try {
			return dao.crear(persona);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void actualizarPersona(Persona persona) throws Exception {
		try {
			dao.actualizar(persona);
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void eliminarPersona(Persona persona) throws Exception {
		try {
			dao.eliminar(persona);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(value="gisfpp", readOnly = true)
	public List<Persona> recuperarTodo() throws Exception {
		try {
			return dao.recuperarTodo();
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	@Transactional(value="gisfpp", readOnly = true)
	public Persona recuperarxId(Integer id) throws Exception {
		try {
			return dao.recuperarxId(id);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(value="gisfpp",readOnly = true)
	public List<PersonaFisica> recuperarSoloPF() throws Exception {
		try {
			return dao.getPersonasFisicas();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(value="gisfpp", readOnly = true)
	public List<PersonaJuridica> recuperarSoloPJ() throws Exception {
		try {
			return dao.getPersonasJuridicas();
		} catch (Exception e) {
			throw e;
		}
	}

	@Autowired(required = true)
	public void setDao(IDaoPersona dao) {
		this.dao = dao;
	}

}// fin de la clase
