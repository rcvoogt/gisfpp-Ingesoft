package unpsjb.fipm.gisfpp.servicios.persona;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.dao.persona.IDaoPersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.TIdentificador;

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
	@Transactional(readOnly=true)
	public PersonaFisica getInstancia(Integer id) throws Exception {
		return dao.recuperarxId(id);
	}


	@Override
	@Transactional(readOnly=true)
	public List<PersonaFisica> getListado() throws Exception {
		return dao.recuperarTodo();
	}


	@Override
	@Transactional(readOnly=true)
	public List<PersonaFisica> getxNombre(String patronNombre) throws Exception {
		return dao.getxNombre(patronNombre);
	}


	@Override
	@Transactional(readOnly=true)
	public List<PersonaFisica> getxIdentificador(TIdentificador campo, String patronValor) throws Exception {
		return dao.getxIdentificador(campo, patronValor);
	}

	
}//fin de la clase
