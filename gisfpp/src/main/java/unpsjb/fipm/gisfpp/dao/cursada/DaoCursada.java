package unpsjb.fipm.gisfpp.dao.cursada;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import unpsjb.fipm.gisfpp.entidades.cursada.Materia;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class DaoCursada extends HibernateDaoSupport implements IDaoMateria{

	private Logger log = UtilGisfpp.getLogger();

	
	@Override
	public Integer crear(Materia instancia) throws DataAccessException {
		try {
			getHibernateTemplate().save(instancia);
			return instancia.getId();
		} catch (Exception e) {
			log.debug(this.getClass().getName(), e);
		}
		
		
		getHibernateTemplate().saveOrUpdate(instancia);
		return instancia.getId();

	}

	@Override
	public void actualizar(Materia instancia) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminar(Materia instancia) throws DataAccessException {
		getHibernateTemplate().delete(instancia);
	}

	@Override
	public List<Materia> recuperarTodo() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Materia recuperarxId(Integer id) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
