package unpsjb.fipm.gisfpp.dao.persona;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;

public class DaoUsuario extends HibernateDaoSupport implements IDaoUsuario {

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

	@Override
	public Usuario getxPersona(PersonaFisica persona) throws DataAccessException {
		String query = "select u from Usuario u where u.persona.id = ?";
		return (Usuario) getHibernateTemplate().find(query, persona.getId());
	}

	@Override
	public Usuario getxNombreUsuario(String nickname) throws DataAccessException {
		String query = "select u from Usuario u where u.nickname = ?";
		return (Usuario) getHibernateTemplate().find(query, nickname);
	}

}// fin de la clase
