package unpsjb.fipm.gisfpp.servicios.persona;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.dao.persona.DaoPersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;

@Service("servPersonaFisica")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServPersonaFisicaImp implements ServicioPersonaFisica, Serializable {

	private static final long serialVersionUID = 1L;

	private DaoPersonaFisica dao;

	public ServPersonaFisicaImp() {
		super();
	}

	@Override
	public Integer nuevaPersonaFisica(PersonaFisica persona) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizarPersonaFisica(PersonaFisica persona) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminarPersonaFisica(PersonaFisica persona) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional(readOnly = true)
	public List<PersonaFisica> todoPersonaFisica() throws Exception {
		return dao.recuperarTodo();
	}

	@Autowired(required = true)
	protected void setDao(DaoPersonaFisica dao) {
		this.dao = dao;
	}

}// Fin de la Clase ServPersonaFisicaImp
