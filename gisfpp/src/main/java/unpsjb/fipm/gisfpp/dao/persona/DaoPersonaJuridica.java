package unpsjb.fipm.gisfpp.dao.persona;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;

@Transactional
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
	@Transactional(readOnly = true)
	public List<PersonaJuridica> recuperarTodo() throws DataAccessException {
		List<PersonaJuridica> lista = getHibernateTemplate().loadAll(PersonaJuridica.class);
		return lista;
	}

	@Override
	@Transactional(readOnly = true)
	public PersonaJuridica recuperarxId(Integer id) throws DataAccessException {
		return getHibernateTemplate().get(PersonaJuridica.class, id);
	}

}// fin de la clase
