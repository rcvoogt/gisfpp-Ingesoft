package unpsjb.fipm.gisfpp.dao.persona;

import java.util.List;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class DaoUsuario extends HibernateDaoSupport implements IDaoUsuario {

	Logger log = UtilGisfpp.getLogger();

	@Override
	public Integer crear(Usuario instancia) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(instancia);
		return instancia.getId();
	}

	@Override
	public void actualizar(Usuario instancia) throws DataAccessException {
		getHibernateTemplate().update(instancia);
	}

	@Override
	public void eliminar(Usuario instancia) throws DataAccessException {
		getHibernateTemplate().delete(instancia);
	}

	@Override
	public List<Usuario> recuperarTodo() throws DataAccessException {
		return getHibernateTemplate().loadAll(Usuario.class);
	}

	@Override
	public Usuario recuperarxId(Integer id) throws DataAccessException {
		return getHibernateTemplate().get(Usuario.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Usuario getxPersona(PersonaFisica persona) throws DataAccessException {
		List<Usuario> resultQuery = null;
		Usuario usuario = null;
		String query = "from Usuario as u inner join fetch u.permisos where u.persona.id = ?";
		try {
			resultQuery = (List<Usuario>) getHibernateTemplate().find(query, persona.getId());
		} catch (DataAccessException | HibernateException exc) {
			log.error(this.getClass().getName(), exc);
			throw exc;
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
		if ((resultQuery != null) && (!resultQuery.isEmpty())) {
			usuario = resultQuery.get(0);
		}
		return usuario;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Usuario getxNombreUsuario(String nickname) throws DataAccessException {
		List<Usuario> resultQuery = null;
		String query = "from Usuario as u inner join  fetch u.permisos inner join fetch u.persona where u.nickname = ?";
		Usuario usuario = null;
		try {
			resultQuery = (List<Usuario>) getHibernateTemplate().find(query, nickname);
		} catch (DataAccessException | HibernateException excDb) {
			log.error(this.getClass().getName(), excDb);
			throw excDb;
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
		if ((resultQuery != null) && (!resultQuery.isEmpty())) {
			usuario = resultQuery.get(0);
		}
		return usuario;
	}

}// fin de la clase
