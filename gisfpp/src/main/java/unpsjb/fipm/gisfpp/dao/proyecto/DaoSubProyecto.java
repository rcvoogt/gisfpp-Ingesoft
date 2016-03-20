package unpsjb.fipm.gisfpp.dao.proyecto;

import java.util.List;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class DaoSubProyecto extends HibernateDaoSupport implements IDaoSubProyecto {

	private Logger log = UtilGisfpp.getLogger();

	@Override
	public Integer crear(SubProyecto instancia) throws DataAccessException {
		try {
			getHibernateTemplate().save(instancia);
			return instancia.getId();
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	public void actualizar(SubProyecto instancia) throws DataAccessException {
		try {
			getHibernateTemplate().saveOrUpdate(instancia);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}

	}

	@Override
	public void eliminar(SubProyecto instancia) throws DataAccessException {
		try {
			getHibernateTemplate().delete(instancia);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubProyecto> recuperarTodo() throws DataAccessException {
		String query = "form SubProyecto as sp inner join fetch sp.perteneceA";
		try {
			return (List<SubProyecto>) getHibernateTemplate().find(query, null);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	public SubProyecto recuperarxId(Integer id) throws DataAccessException {
		String query = "select sp from SubProyecto as sp left join fetch sp.instanciasIsfpp where sp.id=?";
		List<SubProyecto> result;
		try {
			result = (List<SubProyecto>) getHibernateTemplate().find(query, id);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<SubProyecto> listadoSubProyectos(Proyecto proyecto) throws DataAccessException {
		String query = "from SubProyecto as sp where sp.perteneceA = ?";
		try {
			return (List<SubProyecto>) getHibernateTemplate().find(query, proyecto);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}
	
	@Override
	public List <SubProyecto> listadoOfertasActividades() throws DataAccessException{
		String query = "select sp from SubProyecto as sp inner join fetch sp.perteneceA as p where p.estado = 'ACTIVO'";
		List<SubProyecto> resultado;
		try {
			resultado = (List<SubProyecto>) getHibernateTemplate().find(query, null);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
		return resultado;
	}

}// fin de la clase
