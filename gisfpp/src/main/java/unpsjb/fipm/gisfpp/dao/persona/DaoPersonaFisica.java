package unpsjb.fipm.gisfpp.dao.persona;

import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

@Transactional
public class DaoPersonaFisica extends HibernateDaoSupport implements IDaoPersonaFisica {

	private IDaoUsuario daoUsuario;
	private Logger log = UtilGisfpp.getLogger();

	@Override
	public Integer crear(PersonaFisica instancia) throws DataAccessException {
		Usuario usuario;
		// si la persona a crear no tiene un usuario establecido se le crea uno
		// por defecto con dni como nickname(si no posee dni, se crea un
		// nickname por defecto) y password por defecto "unpsjbfipm"
		if (instancia.getUsuario() == null) {
			usuario = new Usuario();
			String nickDefault;
			if (instancia.getDni() == null || instancia.getDni().isEmpty()) {
				int aleatorio = (int) (Math.random() * 100);
				StringTokenizer tokens = new StringTokenizer(instancia.getNombre());
				nickDefault = tokens.nextToken() + aleatorio;
			} else {
				nickDefault = instancia.getDni();
			}
			usuario.setNickname(nickDefault);
			usuario.setPassword("unpsjbfipm");
			usuario.setActivo(true);
			instancia.setUsuario(usuario);
			usuario.setPersona(instancia);
		} else {
			usuario = instancia.getUsuario();
			usuario.setPersona(instancia);
		}
		try {
			getHibernateTemplate().save(instancia);
			return instancia.getId();
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	public void actualizar(PersonaFisica instancia) throws DataAccessException {
		try {
			getHibernateTemplate().update(instancia);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	public void eliminar(PersonaFisica instancia) throws DataAccessException {
		try {
			getHibernateTemplate().delete(instancia);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<PersonaFisica> recuperarTodo() throws DataAccessException {
		String query = "from PersonaFisica as pf left join fetch pf.identificadores";
		try {
			List<PersonaFisica> lista = (List<PersonaFisica>) getHibernateTemplate().find(query, null);
			return lista;
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}

	}

	@Override
	@Transactional(readOnly = true)
	public PersonaFisica recuperarxId(Integer id) throws DataAccessException {
		try {
			return getHibernateTemplate().get(PersonaFisica.class, id);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	public void setDaoUsuario(IDaoUsuario daoUsuario) {
		this.daoUsuario = daoUsuario;
	}

}// fin de la clase
