package unpsjb.fipm.gisfpp.dao.proyecto;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffProyecto;
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
		String query = "from SubProyecto as sp inner join fetch sp.perteneceA";
		try {
			return (List<SubProyecto>) getHibernateTemplate().find(query, null);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<SubProyecto> recuperarTodos() throws DataAccessException {
		String query = "from SubProyecto";
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
		SubProyecto sp;
		List<SubProyecto> result;
		try {
			result = (List<SubProyecto>) getHibernateTemplate().find(query, id);
			sp = result.get(0);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
		if (result != null && !result.isEmpty()) {
			getHibernateTemplate().initialize(sp.getConvocatorias());
			return sp;
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

	@Override
	public Proyecto getPerteneceA(Integer idSP)
			throws DataAccessException {
		String query = "select p from Proyecto as p left join fetch p.staff inner join p.subProyectos as sp where sp.id = ?";
		List<Proyecto> resulQuery;
		try {
			resulQuery =  (List<Proyecto>) getHibernateTemplate().find(query, idSP);
			if (resulQuery!=null && !resulQuery.isEmpty()) {
				for (MiembroStaffProyecto miembro : resulQuery.get(0).getStaff()) {
					getHibernateTemplate().initialize(miembro.getMiembro().getDatosDeContacto());
					getHibernateTemplate().initialize(miembro.getMiembro().getIdentificadores());
				}
				return resulQuery.get(0);
			}
			return null;
		} catch (Exception exc1) {
			log.error("Clase: "+this.getClass().getName()+"- Metodo: getPerteneceA(subproyecto)", exc1);
			throw exc1;
		}
	}

	@Override
	public long cantIsfppAsociadas(Integer idSP) throws DataAccessException {
		String query = "select count(isfpp.id) from Isfpp as isfpp where isfpp.perteneceA.id = ?";
		try {
			return (Long) getHibernateTemplate().find(query, idSP).get(0);
		} catch (Exception exc1) {
			log.error("Clase: "+this.getClass().getName()+"- Metodo: cantIsfppAsociadas(Integer idSP)", exc1);
			throw exc1;
		}
	}

	
}// fin de la clase
