package unpsjb.fipm.gisfpp.dao.persona;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import unpsjb.fipm.gisfpp.entidades.Operaciones;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.security.RolUsuario;

public class DaoUsuario extends HibernateDaoSupport implements IDaoUsuario {

	private Logger log = UtilGisfpp.getLogger();

	@Override
	public Integer crear(Usuario instancia) throws DataAccessException {
		return null;
	}

	@Override
	public void actualizar(Usuario instancia) throws DataAccessException {
		
	}

	@Override
	public void eliminar(Usuario instancia) throws DataAccessException {
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> recuperarTodo() throws DataAccessException {
		String query = "select u from Usuario as u inner join fetch u.persona";
		try {
			return (List<Usuario>) getHibernateTemplate().find(query, null);
		} catch (DataIntegrityViolationException | ConstraintViolationException
				| javax.validation.ConstraintViolationException integridadExc) {
			throw integridadExc;
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	public Usuario recuperarxId(Integer id) throws DataAccessException {
		Usuario result;
		try {
			result = getHibernateTemplate().get(Usuario.class, id);
			getHibernateTemplate().initialize(result.getPersona());
			 return result;
		} catch (DataIntegrityViolationException | ConstraintViolationException
				| javax.validation.ConstraintViolationException integridadExc) {
			throw integridadExc;
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Usuario getxPersona(PersonaFisica persona) throws DataAccessException {
		List<Usuario> resultQuery = null;
		String query = "select u from Usuario as u inner join fetch u.persona where u.persona.id = ?";
		try {
			resultQuery = (List<Usuario>) getHibernateTemplate().find(query, persona.getId());
		} catch (Exception exc1) {
			log.error("Clase: "+this.getClass().getName()+"- Metodo: getxPersona(persona)", exc1);
			throw exc1;
		}
		if ((resultQuery != null) && (!resultQuery.isEmpty())) {
			return resultQuery.get(0);
		}
		return new Usuario();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Usuario getxNombreUsuario(String nickname) throws DataAccessException {
		List<Usuario> resultQuery = null;
		String query = "select u from Usuario as u inner join fetch u.persona where u.nickname = ?";
		Usuario usuario = null;
		try {
			resultQuery = (List<Usuario>) getHibernateTemplate().find(query, nickname);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
		if ((resultQuery != null) && (!resultQuery.isEmpty())) {
			getHibernateTemplate().initialize(resultQuery.get(0).getPersona().getDatosDeContacto());
			getHibernateTemplate().initialize(resultQuery.get(0).getPersona().getIdentificadores());
			usuario = resultQuery.get(0);
		}
		return usuario;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RolUsuario> getRoles(Usuario usuario) throws Exception {
		String queryNativo = "select * from roles_usuario as ru where ru.PersonaId = " + usuario.getPersona().getId();
		List<Object[]> resultQuery;
		List<RolUsuario> result = new ArrayList<>();
		try {
			resultQuery = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryNativo)
					.list();
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
		if (resultQuery == null || resultQuery.isEmpty()) {
			return result;
		}
		for (Object[] row : resultQuery) {
			result.add(new RolUsuario((Integer) row[0], (String) row[1], (String) row[2], (String) row[3],
					(Integer) row[4]));
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> getUsuariosAptos(String operacion) throws Exception {
		int idOperacion = Operaciones.valueOf(operacion).ordinal();
		List<Usuario> result = new ArrayList<Usuario>();
		//Todos los usuarios que tengan los roles
		//	que tengan permitida esta operacion
		String queryNativo = "select u.id, u.nickname from roles_usuario as ru "
				+ 				"inner join permisos as perm on perm.rol = ru.rol "
				+ 				"inner join persona as p on p.idPersona = ru.personaId "
				+ 				"inner join usuario as u on u.personaId = p.idPersona "
				+ 			"where perm.operacion = " + idOperacion;
		List<Object[]> resultQuery;
		
		
		try {
			resultQuery = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryNativo)
				.list();
		}catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
		
		if (resultQuery != null && !resultQuery.isEmpty()) {
			
			for(Object[] resultadoQuery : resultQuery) {
				result.add(new Usuario((Integer)resultadoQuery[0],(String) resultadoQuery[1]));
				
			}
		}
		
		return result;
	}
	

}// fin de la clase
