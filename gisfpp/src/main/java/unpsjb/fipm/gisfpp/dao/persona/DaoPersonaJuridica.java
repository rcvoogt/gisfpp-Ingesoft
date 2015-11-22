package unpsjb.fipm.gisfpp.dao.persona;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaDeContacto;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;

public class DaoPersonaJuridica extends HibernateDaoSupport implements IDaoPersonaJuridica {

	@Override
	public Integer crear(PersonaJuridica instancia) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(instancia);
		return instancia.getId();
	}

	@Override
	public void actualizar(PersonaJuridica instancia) throws DataAccessException {
		getHibernateTemplate().update(instancia);
	}

	@Override
	public void eliminar(PersonaJuridica instancia) throws DataAccessException {
		getHibernateTemplate().delete(instancia);
	}

	@Override
	public List<PersonaJuridica> recuperarTodo() throws DataAccessException {
		List<PersonaJuridica> lista = getHibernateTemplate().loadAll(PersonaJuridica.class);
		for (PersonaJuridica item : lista) {
			Hibernate.initialize(item.getDatosDeContacto());
			Hibernate.initialize(item.getDomicilios());
			Hibernate.initialize(item.getIdentificadores());
		}
		return lista;
	}

	@Override
	public PersonaJuridica recuperarxId(Integer id) throws DataAccessException {
		return getHibernateTemplate().get(PersonaJuridica.class, id);
	}

	@Override
	public void agregarPersonaContacto(PersonaDeContacto contacto) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(contacto);
	}

	@Override
	public void quitarPersonaContacto(PersonaDeContacto contacto) throws DataAccessException {
		getHibernateTemplate().delete(contacto);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PersonaDeContacto> getPersonasContacto(PersonaJuridica organizacion) throws DataAccessException {
		String query = "select pc from PersonaDeContacto pc where pc.organizacion.nombre = ?";
		return (List<PersonaDeContacto>) getHibernateTemplate().find(query, organizacion.getNombre());

	}

}// fin de la clase
