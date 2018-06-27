package unpsjb.fipm.gisfpp.dao.cursada;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import unpsjb.fipm.gisfpp.entidades.cursada.Materia;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class DaoMateria extends HibernateDaoSupport implements IDaoMateria {

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

	@SuppressWarnings("unchecked")
	@Override
	public Materia recuperarxId(Integer id) throws DataAccessException {
		String query = "select materia " 
					 + "from Materia materia " 
					 + "where materia.id = ?";
		List<Materia> result = (List<Materia>) getHibernateTemplate().find(query, id);
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}

	@Override
	public int actualizarOguardar(Materia instancia) throws Exception {
		getHibernateTemplate().saveOrUpdate(instancia);
		return instancia.getId();
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public boolean existe(String codigoMateria) {
		String query = "select materia " 
					 + "from Materia as materia " 
					 + "where materia.codigoMateria = ?";
		List<Materia> result;
		Materia materiaAux;
		try {
			result = (List<Materia>) getHibernateTemplate().find(query, codigoMateria);
			materiaAux = result.get(0);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

}
