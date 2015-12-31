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
public class ServiciosPersonaFisica implements IServicioPF {

	private IDaoPersonaFisica dao;

	
	@Autowired(required = false)
	public void setDao(IDaoPersonaFisica dao) {
		this.dao = dao;
	}


	@Override
	@Transactional(readOnly=false)
	public Integer persistir(PersonaFisica instancia) throws Exception {
		return dao.crear(instancia);
	}


	@Override
	@Transactional(readOnly=false)
	public void editar(PersonaFisica instancia) throws Exception {
		dao.actualizar(instancia);		
	}


	@Override
	@Transactional(readOnly=false)
	public void eliminar(PersonaFisica instancia) throws Exception {
		dao.eliminar(instancia);
		
	}


	@Override
	public PersonaFisica getInstancia(Integer id) throws Exception {
		return dao.recuperarxId(id);
	}


	@Override
	public List<PersonaFisica> getListado() throws Exception {
		return dao.recuperarTodo();
	}

}//fin de la clase
