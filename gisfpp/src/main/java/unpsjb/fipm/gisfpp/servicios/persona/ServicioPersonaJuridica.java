package unpsjb.fipm.gisfpp.servicios.persona;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.dao.persona.IDaoPersonaJuridica;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;

@Service("servPersonaJuridica")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServicioPersonaJuridica implements IServicioPJ {

	private IDaoPersonaJuridica dao;

	@Autowired(required = true)
	public void setDao(IDaoPersonaJuridica dao) {
		this.dao = dao;
	}

	@Override
	@Transactional(readOnly=false)
	public Integer persistir(PersonaJuridica instancia) throws Exception {
		return dao.crear(instancia);
	}

	@Override
	@Transactional(readOnly=false)
	public void editar(PersonaJuridica instancia) throws Exception {
		dao.actualizar(instancia);		
	}

	@Override
	@Transactional(readOnly=false)
	public void eliminar(PersonaJuridica instancia) throws Exception {
		dao.eliminar(instancia);
	}

	@Override
	@Transactional(readOnly=true)
	public PersonaJuridica getInstancia(Integer id) throws Exception {
		return dao.recuperarxId(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<PersonaJuridica> getListado() throws Exception {
		return dao.recuperarTodo();
	}

}// fin de la clase
