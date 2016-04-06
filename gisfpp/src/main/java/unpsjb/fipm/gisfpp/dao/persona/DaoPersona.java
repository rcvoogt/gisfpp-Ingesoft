package unpsjb.fipm.gisfpp.dao.persona;

import java.util.List;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import unpsjb.fipm.gisfpp.entidades.persona.Persona;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class DaoPersona extends HibernateDaoSupport implements IDaoPersona {

	private Logger log = UtilGisfpp.getLogger();
	
	@Override
	public Integer crear(Persona instancia) throws DataAccessException {
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
	public void actualizar(Persona instancia) throws DataAccessException {
		getHibernateTemplate().update(instancia);
	}

	@Override
	public void eliminar(Persona instancia) throws DataAccessException {
		getHibernateTemplate().delete(instancia);
	}

	@Override
	public List<Persona> recuperarTodo() throws DataAccessException {
		List<Persona> lista = getHibernateTemplate().loadAll(Persona.class);
		for (Persona persona : lista) {
			Hibernate.initialize(persona.getIdentificadores());
			Hibernate.initialize(persona.getDatosDeContacto());
			Hibernate.initialize(persona.getDomicilios());
		}
		return lista;
	}

	@Override
	public Persona recuperarxId(Integer id) throws DataAccessException {
		return getHibernateTemplate().get(Persona.class, id);
	}

	@Override
	public List<PersonaFisica> getPersonasFisicas() throws DataAccessException {
		List<PersonaFisica> lista = getHibernateTemplate().loadAll(PersonaFisica.class);
		for (PersonaFisica pf : lista) {
			Hibernate.initialize(pf.getIdentificadores());
			Hibernate.initialize(pf.getDatosDeContacto());
			Hibernate.initialize(pf.getDomicilios());
		}
		return lista;
	}

	@Override
	public List<PersonaJuridica> getPersonasJuridicas() throws DataAccessException {
		List<PersonaJuridica> lista = getHibernateTemplate().loadAll(PersonaJuridica.class);
		for (PersonaJuridica pj : lista) {
			Hibernate.initialize(pj.getIdentificadores());
			Hibernate.initialize(pj.getDatosDeContacto());
			Hibernate.initialize(pj.getDomicilios());
		}
		return lista;
	}

}
