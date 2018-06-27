package unpsjb.fipm.gisfpp.integracion.dao;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import unpsjb.fipm.gisfpp.integracion.entidades.PersonaAdapter;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class DaoPersonaAdapter extends HibernateDaoSupport implements IDaoPersonaAdapter{

	private Logger log = UtilGisfpp.getLogger();

	@Override
	public Integer crear(PersonaAdapter instancia) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(instancia);
		return instancia.getId();
	}

	@Override
	public void actualizar(PersonaAdapter instancia) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminar(PersonaAdapter instancia) throws DataAccessException {
		getHibernateTemplate().delete(instancia);
		
	}

	@Override
	public List<PersonaAdapter> recuperarTodo() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersonaAdapter recuperarxId(Integer id) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}