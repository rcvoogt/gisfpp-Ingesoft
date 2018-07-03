package unpsjb.fipm.gisfpp.integracion.dao;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import unpsjb.fipm.gisfpp.integracion.entidades.PersonaAdapter;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class DaoPersonaAdapter extends HibernateDaoSupport implements IDaoPersonaAdapter {

	private Logger log = UtilGisfpp.getLogger();

	@Override
	public Integer crear(PersonaAdapter instancia) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(instancia);
		return instancia.getId();
	}

	@Override
	public void actualizar(PersonaAdapter instancia) throws DataAccessException {
		getHibernateTemplate().merge(instancia);
	}

	@Override
	public void eliminar(PersonaAdapter instancia) throws DataAccessException {
		getHibernateTemplate().delete(instancia);

	}

	@Override
	public List<PersonaAdapter> recuperarTodo() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PersonaAdapter recuperarxId(Integer id) throws DataAccessException {
		String query = "select persona " + "from PersonaAdapter persona " + "where persona.id = ?";
		List<PersonaAdapter> result = (List<PersonaAdapter>) getHibernateTemplate().find(query, id);
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int existe(String nroInscripcion) {
		String query = "select persona " + "from PersonaAdapter persona " + "where persona.nroInscripcion = ?";
		List<PersonaAdapter> result;
		PersonaAdapter materiaAux;
		try {
			result = (List<PersonaAdapter>) getHibernateTemplate().find(query, nroInscripcion);
			materiaAux = result.get(0);
			if (materiaAux == null)
				return -1;
		} catch (Exception e) {
			return -1;
		}
		return materiaAux.getId();
	}

	@Override
	public PersonaAdapter recuperarxNroInscripcion(String nroInscripcion) {
		String query = "select persona " + "from PersonaAdapter persona " + "where persona.nroInscripcion = ?";
		List<PersonaAdapter> result;
		PersonaAdapter materiaAux;
		try {
			result = (List<PersonaAdapter>) getHibernateTemplate().find(query, nroInscripcion);
			materiaAux = result.get(0);
			if (materiaAux == null)
				return null;
		} catch (Exception e) {
			return null;
		}
		return materiaAux;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PersonaAdapter recuperarxLegajo(String legajo) {
		String query = "select persona " 
					+ "from PersonaAdapter persona " 
					+ "where persona.legajo1 = ? or persona.legajo2 = ? ";
		List<PersonaAdapter> result;
		PersonaAdapter materiaAux;
		try {
			result = (List<PersonaAdapter>) getHibernateTemplate().find(query, legajo, legajo);
			materiaAux = result.get(0);
		} catch (Exception e) {
			return null;
		}
		return materiaAux;

	}

}
