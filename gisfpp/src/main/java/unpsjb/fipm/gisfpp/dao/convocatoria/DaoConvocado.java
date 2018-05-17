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
import unpsjb.fipm.gisfpp.entidades.convocatoria.ERespuestaConvocado;
import unpsjb.fipm.gisfpp.entidades.persona.DatoDeContacto;
import unpsjb.fipm.gisfpp.entidades.persona.Persona;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;
import unpsjb.fipm.gisfpp.entidades.persona.TDatosContacto;
import unpsjb.fipm.gisfpp.entidades.proyecto.EEstadosIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class DaoConvocado extends HibernateDaoSupport implements IDaoConvocado {

	private Logger log = UtilGisfpp.getLogger();

	@Override
	public Integer crear(Convocado instancia) throws DataAccessException {
		try {
			getHibernateTemplate().save(instancia);
			return instancia.getId();
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	public void actualizar(Convocado instancia) throws DataAccessException {
		try {
			getHibernateTemplate().update(instancia);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	public void eliminar(Convocado instancia) throws DataAccessException {
		try {
			getHibernateTemplate().delete(instancia);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}

	}

	@Override
	public List<Convocado> recuperarTodo() throws DataAccessException {
		String query = "from Isfpp as isffpp inner join fecth isfpp.perteneceA";
		try {
			return (List<Convocado>) getHibernateTemplate().find(query, null);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	public Convocado recuperarxId(Integer id) throws DataAccessException {
		String query = "select conv from Convocado as conv left join fetch conv.convocatoria  left join fetch conv.persona  where conv.id = ?";
		List<Convocado> result;
		try {
			result = (List<Convocado>) getHibernateTemplate().find(query, id);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
		
			return null;
	}

	@Override
	public ERespuestaConvocado getRespuesta(Convocado conv) throws Exception {
		
		return conv.getRespuesta();
	}
	
	public void setRespuesta(Convocado conv, ERespuestaConvocado respuesta) {
		conv.setRespuesta(respuesta);
		this.actualizar(conv);
	}

	@Override
	public PersonaFisica getPersonaFisica(Convocado conv) throws Exception {
		return conv.getPersona();
	}

	@Override
	public Convocatoria getConvocatoria(Convocado conv) throws Exception {
		return conv.getConvocatoria();
	}

	@Override
	public List<DatoDeContacto> getDatosContacto(Convocado conv) throws Exception {
		return conv.getPersona().getDatosDeContacto();
	}

	@Override
	public String getMail(Convocado conv) throws Exception {
		List<DatoDeContacto> datosContacto = conv.getPersona().getDatosDeContacto();
		
		for (DatoDeContacto dato : datosContacto) {
			if(dato.getTipo() == TDatosContacto.EMAIL) {
				return dato.getValor();
			}
		}
		
		return null;
	}

	@Override
	public List<Convocado> recuperarConvocados(Integer id) throws Exception {
		String query = "select c "
					 + "from Convocado as c"
					 + "where c.convocatoriaId = ?";
		try {
			return (List<Convocado>) getHibernateTemplate().find(query, id);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	

			
}// fin de la clase
