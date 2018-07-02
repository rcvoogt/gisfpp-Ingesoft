package unpsjb.fipm.gisfpp.integracion.dao;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import unpsjb.fipm.gisfpp.integracion.entidades.MateriaAdapter;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class DaoMateriaAdapter extends HibernateDaoSupport implements IDaoMateriaAdapter {

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
		return instancia.getId();
	}

	@Override
	public void actualizar(MateriaAdapter instancia) throws DataAccessException {
		getHibernateTemplate().merge(instancia);
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

	@SuppressWarnings("unchecked")
	@Override
	public MateriaAdapter recuperarxId(Integer id) throws DataAccessException {
		String query ="select materia "
					+ "from MateriaAdapter materia "
					+ "where materia.id = ?";
		List<MateriaAdapter> result = (List<MateriaAdapter>) getHibernateTemplate().find(query, id);
		if(result == null || result.isEmpty()){
			return null;
		}
		return result.get(0);
	}

	@Override
	public int actualizarOguardar(MateriaAdapter instancia) throws Exception {
		getHibernateTemplate().saveOrUpdate(instancia);
		return instancia.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public int existe(String codigoMateria) {
		String query = "select materia " 
					 + "from MateriaAdapter as materia " 
					 + "where materia.materia = ?";
		List<MateriaAdapter> result;
		MateriaAdapter materiaAux;
		try {
			result = (List<MateriaAdapter>) getHibernateTemplate().find(query, codigoMateria);
			materiaAux = result.get(0);
			if(materiaAux == null)
				return -1;
		} catch (Exception e) {
			return -1;
		}
		return materiaAux.getId();
	}

}
