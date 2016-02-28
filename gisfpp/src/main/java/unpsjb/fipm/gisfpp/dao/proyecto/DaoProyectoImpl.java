package unpsjb.fipm.gisfpp.dao.proyecto;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.persona.Persona;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class DaoProyectoImpl extends HibernateDaoSupport implements DaoProyecto {

	private Logger log = UtilGisfpp.getLogger();

	public Integer crear(Proyecto instancia) throws DataAccessException {
		try {
			getHibernateTemplate().save(instancia);
			return instancia.getId();
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	public void actualizar(Proyecto instancia) throws DataAccessException {
		try {
			getHibernateTemplate().update(instancia);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	public void eliminar(Proyecto instancia) throws DataAccessException {
		try {
			getHibernateTemplate().delete(instancia);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Proyecto> recuperarTodo() throws DataAccessException {
		String query = "select p from Proyecto as p";
		try {
			return (List<Proyecto>) getHibernateTemplate().find(query, null);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public Proyecto recuperarxId(Integer id) throws DataAccessException {
		String query = "select p from Proyecto as p left join fetch p.subProyectos left join fetch p.demandantes"
				+ " left join fetch p.staff where p.id=?";
		List<Proyecto> result;
		try {
			result = (List<Proyecto>) getHibernateTemplate().find(query, id);
			if ((result.isEmpty()) || (result == null)) {
				return null;
			} else {
				for (Persona demandante : result.get(0).getDemandantes()) {
					getHibernateTemplate().initialize(demandante.getIdentificadores());
				}
				for(MiembroStaffProyecto miembroStaff: result.get(0).getStaff()){
					getHibernateTemplate().initialize(miembroStaff.getMiembro().getIdentificadores());
				}
				return result.get(0);
			}
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

}// fin de la clase
