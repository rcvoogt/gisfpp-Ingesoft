package unpsjb.fipm.gisfpp.servicios.persona;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.dao.persona.DaoPersonaJuridica;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;

@Service("servPersonaJuridica")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServPersonaJuridicaImp implements ServicioPersonaJuridica, Serializable {

	private static final long serialVersionUID = 1L;

	private DaoPersonaJuridica dao;

	@Override
	public Integer nuevaPersonaJuridica(PersonaJuridica persona) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizarPersonaJuridica(PersonaJuridica persona) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminarPersonaJuridica(PersonaJuridica persona) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional(readOnly = false)
	public List<PersonaJuridica> todoPersonaJuridica() throws Exception {
		return dao.recuperarTodo();
	}

	@Autowired(required = true)
	protected void setDao(DaoPersonaJuridica dao) {
		this.dao = dao;
	}

}// Fin de la clase ServPersonaJuridicaImp
