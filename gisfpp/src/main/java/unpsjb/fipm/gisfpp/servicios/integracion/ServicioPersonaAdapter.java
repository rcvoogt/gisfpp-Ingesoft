package unpsjb.fipm.gisfpp.servicios.integracion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.integracion.dao.IDaoPersonaAdapter;
import unpsjb.fipm.gisfpp.integracion.entidades.PersonaAdapter;
	
@Service("servPersonaAdapter")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServicioPersonaAdapter implements IServicioPersonaAdapter{
	
	private IDaoPersonaAdapter dao;

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public Integer persistir(PersonaAdapter instancia) throws Exception {
		return dao.crear(instancia);
	}

	@Override
	public void editar(PersonaAdapter instancia) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void eliminar(PersonaAdapter instancia) throws Exception {
		dao.eliminar(instancia);
	}

	@Override
	public PersonaAdapter getInstancia(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PersonaAdapter> getListado() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Autowired
	public void setDao(IDaoPersonaAdapter dao) {
		this.dao = dao;
	}

	@Override
	public int existe(int legajo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
}
