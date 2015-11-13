package unpsjb.fipm.gisfpp.dao.persona;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;

public class DaoPersonaJuridicaImp extends HibernateDaoSupport implements DaoPersonaJuridica {

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
		return getHibernateTemplate().loadAll(PersonaJuridica.class);
	}

	@Override
	public PersonaJuridica recuperarxId(Integer id) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PersonaJuridica> filtrarxCuit(String patron) {
		// TODO Auto-generated method stub
		return null;
	}

}
