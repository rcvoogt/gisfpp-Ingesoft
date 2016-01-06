package unpsjb.fipm.gisfpp.dao.proyecto;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class DaoIsfpp extends HibernateDaoSupport implements IDaoIsfpp {

	private Logger log = UtilGisfpp.getLogger();

	@Override
	@Transactional(readOnly = false)
	public Integer crear(Isfpp instancia) throws DataAccessException {
		try {
			getHibernateTemplate().save(instancia);
			return instancia.getId();
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void actualizar(Isfpp instancia) throws DataAccessException {
		try {
			getHibernateTemplate().update(instancia);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void eliminar(Isfpp instancia) throws DataAccessException {
		try {
			getHibernateTemplate().delete(instancia);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<Isfpp> recuperarTodo() throws DataAccessException {
		String query = "from Isfpp as isffpp inner join fecth isfpp.perteneceA";
		try {
			return (List<Isfpp>) getHibernateTemplate().find(query, null);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Isfpp recuperarxId(Integer id) throws DataAccessException {
		String query = "select isfpp from Isfpp as isfpp inner join fetch isfpp.perteneceA sp inner join fetch sp.perteneceA where isfpp.id = ?";
		List<Isfpp> result;
		try {
			result = (List<Isfpp>) getHibernateTemplate().find(query, id);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
		if (result != null && !result.isEmpty()) {
			return result.get(0);
		} else {
			return null;
		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<Isfpp> getIsfpps(SubProyecto sp) throws Exception {
		String query = "from Isfpp as isfpp where isfpp.perteneceA=?";
		try {
			return (List<Isfpp>) getHibernateTemplate().find(query, sp);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

}// fin de la clase
