package unpsjb.fipm.gisfpp.dao.persona;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;

public class DaoPersonaFisicaImp extends HibernateDaoSupport implements DaoPersonaFisica {

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
		return getHibernateTemplate().loadAll(PersonaFisica.class);
	}

	@Override
	public PersonaFisica recuperarxId(Integer id) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PersonaFisica> recuperarxApellido(String patron) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PersonaFisica> filtrarxDni(String patron) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PersonaFisica> filtrarxCuil(String cuil) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
