package unpsjb.fipm.gisfpp.dao.proyecto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.persona.Persona;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;
import unpsjb.fipm.gisfpp.entidades.proyecto.EEstadosIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
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
		String query = "from Isfpp";

		//String query = "from Isfpp as isffpp inner join fecth isfpp.perteneceA";
		try {
			return (List<Isfpp>) getHibernateTemplate().find(query, null);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	public Isfpp recuperarxId(Integer id) throws DataAccessException {
		String query = "select isfpp from Isfpp as isfpp left join fetch isfpp.staff  left join fetch isfpp.practicantes left join fetch isfpp.convocatorias  where isfpp.id = ?";
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
				getHibernateTemplate().initialize(miembroStaff.getMiembro().getDatosDeContacto());
			}
			for (PersonaFisica persona : result.get(0).getPracticantes()) {
				getHibernateTemplate().initialize(persona.getIdentificadores());
				getHibernateTemplate().initialize(persona.getDatosDeContacto());
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
	public SubProyecto getPerteneceA(Integer idIsfpp) throws Exception {
		String query = "select sp from SubProyecto as sp inner join sp.instanciasIsfpp as ins where ins.id = ?";
		List<SubProyecto> resulQuery;
		try {
			resulQuery= (List<SubProyecto>) getHibernateTemplate().find(query, idIsfpp);
		} catch (Exception exc1) {
			log.error("Clase: "+this.getClass().getName()+"- Método: getPerteneceA(isfpp)", exc1);
			throw exc1;
		}
		if(resulQuery!=null && !resulQuery.isEmpty()){
			return resulQuery.get(0);
		}
		return null;
	}
	
	@Override
	public Proyecto getPerteneceAProyecto(Integer idIsfpp) throws Exception {
		String stringQuery = "select p from Proyecto as p inner join p.subProyectos as sp inner join sp.instanciasIsfpp as isfpp"
				+ " where isfpp.id = :id";
		Proyecto proyecto;
		
		Session session = this.currentSession();
		Query query = session.createQuery(stringQuery);
		proyecto = (Proyecto) query.setInteger("id", idIsfpp).uniqueResult();
		
		if (proyecto!=null) {
			Hibernate.initialize(proyecto.getDemandantes());
			Hibernate.initialize(proyecto.getStaff());
			
			for (Persona item : proyecto.getDemandantes()) {
				Hibernate.initialize(item.getIdentificadores());
				if (item instanceof PersonaJuridica) {
					Hibernate.initialize(((PersonaJuridica) item).getContactos());
					for (PersonaFisica persona : ((PersonaJuridica) item).getContactos()) {
						Hibernate.initialize(persona.getIdentificadores());
					}
				}
			}
			for (MiembroStaffProyecto miembroStaff : proyecto.getStaff()) {
				Hibernate.initialize(miembroStaff.getMiembro().getIdentificadores());
			}
			return proyecto;
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<PersonaFisica> getPracticantes(Integer idIsfpp)
			throws Exception {
		String query ="select p from Isfpp as i join i.practicantes as p where i.id = ?";
		List<PersonaFisica> resultado;
		try {
			resultado = (List<PersonaFisica>) getHibernateTemplate().find(query, idIsfpp);
		} catch (Exception exc) {
			log.error("Clase: "+ this.getClass().getName() +"- Metodo: List<PersonaFisica> getPracticantes(Integer idIsfpp)", exc);
			throw exc;
		}
		if (resultado!=null) {
			for (PersonaFisica persona : resultado) {
				getHibernateTemplate().initialize(persona.getDatosDeContacto());
				getHibernateTemplate().initialize(persona.getIdentificadores());
			}
			return resultado;
		}
		return new ArrayList<PersonaFisica>();
	}

	@Override
	public int getCantidadPracticantes(Integer idIsfpp) throws Exception {
		String queryNativo = "SELECT count(*) FROM gisfpp.practicantes where isfppId = " + idIsfpp;
		try {
			BigInteger resultado = (BigInteger) getHibernateTemplate().getSessionFactory().getCurrentSession()
					.createSQLQuery(queryNativo).uniqueResult();
			return resultado.intValueExact();
		} catch (Exception exc) {
			log.error("Clase: "+ this.getClass().getName() +"- Metodo: int getCantidadPracticantes(Integer idIsfpp)", exc);
			throw exc;
		}
	}

	@Override
	public void actualizarEstado(Integer idIsfpp, EEstadosIsfpp estado)
			throws Exception {
		String queryUpdate = "update Isfpp i set i.estado = :estado where i.id = :id";
		try {
			Session sesion = getHibernateTemplate().getSessionFactory().getCurrentSession();
			sesion.createQuery(queryUpdate)
				.setInteger("id", idIsfpp)
				.setParameter("estado", estado)
				.executeUpdate();
		} catch (Exception exc) {
			log.error("Clase: "+ this.getClass().getName() +"- Metodo: actualizarEstado(Integer idIsfpp, EEstadosIsfpp estado)", exc);
			throw exc;
		}
		
	}
	
	@Override
	public List<Convocatoria> getConvocatorias(Integer idIsfpp) throws Exception {
		String query ="select p from Isfpp as i join i.convocatorias as p where i.id = ?";
		List<Convocatoria> resultado;
		try {
			resultado = (List<Convocatoria>) getHibernateTemplate().find(query, idIsfpp);
		} catch (Exception exc) {
			log.error("Clase: "+ this.getClass().getName() +"- Metodo: List<Convocatoria> getConvocatoria(Integer idIsfpp)", exc);
			throw exc;
		}
		if (resultado!=null) {
			for (Convocatoria convocatoria : resultado) {
				getHibernateTemplate().initialize(convocatoria.getConvocados());
				
			}
			return resultado;
		}
		return new ArrayList<Convocatoria>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MiembroStaffIsfpp> getMiembros(Isfpp isfpp) throws Exception, NullPointerException {
		String query = "select m "
					+ "from MiembroStaffIsfpp m "
					+ "where m.isfpp.id = ?";
		List<MiembroStaffIsfpp> resultado;
		try {
			resultado = (List<MiembroStaffIsfpp>) getHibernateTemplate().find(query, isfpp.getId());
		} catch (Exception exc) {
			log.error("Clase: "+ this.getClass().getName() +"- Metodo: List<MiembroStaffIsfpp> getMiembros(Isfpp isfpp) ", exc);
			throw exc;
		}
		return resultado;
	}

			
}// fin de la clase
