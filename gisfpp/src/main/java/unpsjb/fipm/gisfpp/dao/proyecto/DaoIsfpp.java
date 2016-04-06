package unpsjb.fipm.gisfpp.dao.proyecto;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class DaoIsfpp extends HibernateDaoSupport implements IDaoIsfpp {

	private Logger log = UtilGisfpp.getLogger();

	@Override
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
	public void actualizar(Isfpp instancia) throws DataAccessException {
		try {
			getHibernateTemplate().update(instancia);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	public void eliminar(Isfpp instancia) throws DataAccessException {
		try {
			getHibernateTemplate().delete(instancia);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}

	}

	@Override
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
	public Isfpp recuperarxId(Integer id) throws DataAccessException {
		String query = "select isfpp from Isfpp as isfpp left join fetch isfpp.staff  left join fetch isfpp.practicantes  where isfpp.id = ?";
		List<Isfpp> result;
		try {
			result = (List<Isfpp>) getHibernateTemplate().find(query, id);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
		if (result != null && !result.isEmpty()) {
			for (MiembroStaffIsfpp miembroStaff : result.get(0).getStaff()) {
				getHibernateTemplate().initialize(miembroStaff.getMiembro().getIdentificadores());
			}
			for (PersonaFisica persona : result.get(0).getPracticantes()) {
				getHibernateTemplate().initialize(persona.getIdentificadores());
			}
			return result.get(0);
		} else {
			return null;
		}

	}

	@Override
	public List<Isfpp> getIsfpps(SubProyecto sp) throws Exception {
		String query = "select isfpp from Isfpp as isfpp left join fetch isfpp.staff where isfpp.perteneceA=?";
		try {
			return (List<Isfpp>) getHibernateTemplate().find(query, sp);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	public SubProyecto getPerteneceA(Isfpp isfpp) throws Exception {
		String query = "select sp from SubProyecto as sp inner join sp.instanciasIsfpp as ins where ins.id = ?";
		List<SubProyecto> resulQuery;
		try {
			resulQuery= (List<SubProyecto>) getHibernateTemplate().find(query, isfpp.getId());
		} catch (Exception exc1) {
			log.error("Clase: "+this.getClass().getName()+"- Método: getPerteneceA(isfpp)", exc1);
			throw exc1;
		}
		if(resulQuery!=null && !resulQuery.isEmpty()){
			return resulQuery.get(0);
		}
		return null;
	}
	
	

}// fin de la clase
