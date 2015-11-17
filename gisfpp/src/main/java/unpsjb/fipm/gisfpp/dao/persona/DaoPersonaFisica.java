package unpsjb.fipm.gisfpp.dao.persona;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;

public class DaoPersonaFisica extends HibernateDaoSupport implements IDaoPersonaFisica {

	@Override
	public Integer crear(PersonaFisica instancia) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(instancia);
		return instancia.getId();
	}

	@Override
	public void actualizar(PersonaFisica instancia) throws DataAccessException {
		getHibernateTemplate().update(instancia);
	}

	@Override
	public void eliminar(PersonaFisica instancia) throws DataAccessException {
		getHibernateTemplate().delete(instancia);
	}

	@Override
	public List<PersonaFisica> recuperarTodo() throws DataAccessException {
		List<PersonaFisica> lista = getHibernateTemplate().loadAll(PersonaFisica.class);
		for (PersonaFisica persona : lista) {
			Hibernate.initialize(persona.getIdentificadores());
			Hibernate.initialize(persona.getDatosDeContacto());
			Hibernate.initialize(persona.getDomicilios());
		}
		return lista;
	}

	@Override
	public PersonaFisica recuperarxId(Integer id) throws DataAccessException {
		return getHibernateTemplate().get(PersonaFisica.class, id);
	}

}
