package unpsjb.fipm.gisfpp.dao.persona;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import unpsjb.fipm.gisfpp.entidades.persona.Persona;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;

public class DaoPersona extends HibernateDaoSupport implements IDaoPersona {

	@Override
	public Integer crear(Persona instancia) throws DataAccessException {
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
