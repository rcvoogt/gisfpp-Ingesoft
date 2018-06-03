package unpsjb.fipm.gisfpp.dao.convocatoria;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocado;
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

public class DaoConvocatoria extends HibernateDaoSupport implements IDaoConvocatoria {

	private Logger log = UtilGisfpp.getLogger();

	@Override
	public Integer crear(Convocatoria instancia) throws DataAccessException {
		try {
			getHibernateTemplate().save(instancia);
			return instancia.getId();
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	public void actualizar(Convocatoria instancia) throws DataAccessException {
		try {
			getHibernateTemplate().update(instancia);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	public void eliminar(Convocatoria instancia) throws DataAccessException {
		try {
			getHibernateTemplate().delete(instancia);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}

	}

	@Override
	public List<Convocatoria> recuperarTodo() throws DataAccessException {
		String query = "from Convocatoria as conv";
		try {
			return (List<Convocatoria>) getHibernateTemplate().find(query, null);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	public Convocatoria recuperarxId(Integer id) throws DataAccessException {
		String query = "select convocatoria "
					 + "from Convocatoria as convocatoria "
					 + "left outer join fetch convocatoria.isfpp "
					 + "left outer join fetch convocatoria.sub_proyecto "
					 + "left outer join fetch convocatoria.proyecto "
					 + "where convocatoria.id = ?";
		List<Convocatoria> result;
		Convocatoria convocatoria;
		try {
			result = (List<Convocatoria>) getHibernateTemplate().find(query, id);
		} catch (Exception e) {
			//log.error(this.getClass().getName(), e);
			throw e;
		}
		getHibernateTemplate().initialize(result.get(0).getConvocados());
		if (result != null && !result.isEmpty()) {
			for (Convocado convocado : result.get(0).getConvocados()) {
				getHibernateTemplate().initialize(convocado.getPersona().getIdentificadores());
				getHibernateTemplate().initialize(convocado.getPersona().getDatosDeContacto());
			}
			return result.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public Isfpp getIsfpp(Integer idConvocatoria) throws Exception {
		String query = "select ins from Isfpp as ins inner join sp.instanciasIsfpp as ins where ins.id = ?";
		List<Isfpp> resulQuery;
		try {
			resulQuery= (List<Isfpp>) getHibernateTemplate().find(query, idConvocatoria);
		} catch (Exception exc1) {
			log.error("Clase: "+this.getClass().getName()+"- Método: getIsfpp(convocatoria)", exc1);
			throw exc1;
		}
		if(resulQuery!=null && !resulQuery.isEmpty()){
			return resulQuery.get(0);
		}
		return null;
	}

	@Override
	public List<Convocado> getConvocados(Integer idConvocatoria) throws Exception {
		String query ="select p from Convocatoria as i join i.convocados as p where i.id = ?";
		List<Convocado> resultado;
		try {
			resultado = (List<Convocado>) getHibernateTemplate().find(query,idConvocatoria);
		} catch (Exception exc) {
			log.error("Clase: "+ this.getClass().getName() +"- Metodo: List<Convocado> getConvocados(Integer idConvocatoria)", exc);
			throw exc;
		}
		if (resultado!=null) {
			for (Convocado convocado : resultado) {
				getHibernateTemplate().initialize(convocado.getPersona().getDatosDeContacto());
				getHibernateTemplate().initialize(convocado.getPersona().getIdentificadores());
			}
			return resultado;
		}
		return new ArrayList<Convocado>();
	}

	@Override
	public int getCantidadConvocados(Integer idConvocatoria) throws Exception {
		List<Convocado> convocados = this.getConvocados(idConvocatoria);
		return convocados.size();
	}


			
}// fin de la clase
