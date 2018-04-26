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

import unpsjb.fipm.gisfpp.entidades.persona.Persona;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;
import unpsjb.fipm.gisfpp.entidades.proyecto.Convocado;
import unpsjb.fipm.gisfpp.entidades.proyecto.Convocatoria;
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
		String query = "from Isfpp as isffpp inner join fecth isfpp.perteneceA";
		try {
			return (List<Convocatoria>) getHibernateTemplate().find(query, null);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	public Convocatoria recuperarxId(Integer id) throws DataAccessException {
		String query = "select convocatoria from Convocatoria as convocatoria left join fetch convocatoria.convocados where convocatoria.id = ?";
		List<Convocatoria> result;
		try {
			result = (List<Convocatoria>) getHibernateTemplate().find(query, id);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
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
		Convocatoria conv = this.recuperarxId(idConvocatoria);
		return (List<Convocado>) conv.getConvocados();
	}

	@Override
	public int getCantidadConvocados(Integer idConvocatoria) throws Exception {
		List<Convocado> convocados = this.getConvocados(idConvocatoria);
		return convocados.size();
	}

	

			
}// fin de la clase
