package unpsjb.fipm.gisfpp.integracion.dao;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import unpsjb.fipm.gisfpp.integracion.entidades.MateriaAdapter;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class DaoMateriaAdapter extends HibernateDaoSupport implements IDaoMateriaAdapter{

	private Logger log = UtilGisfpp.getLogger();

	
	@Override
	public Integer crear(MateriaAdapter instancia) throws DataAccessException {
		try {
			getHibernateTemplate().save(instancia);
			return instancia.getId();
		} catch (Exception e) {
			log.debug(this.getClass().getName(), e);
		}
		
		
		getHibernateTemplate().saveOrUpdate(instancia);
		return instancia.getId();	}

	@Override
	public void actualizar(MateriaAdapter instancia) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminar(MateriaAdapter instancia) throws DataAccessException {
		getHibernateTemplate().delete(instancia);
		
	}

	@Override
	public List<MateriaAdapter> recuperarTodo() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MateriaAdapter recuperarxId(Integer id) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int actualizarOguardar(MateriaAdapter instancia) throws Exception {
		getHibernateTemplate().saveOrUpdate(instancia);
		return instancia.getId();
	}

}
