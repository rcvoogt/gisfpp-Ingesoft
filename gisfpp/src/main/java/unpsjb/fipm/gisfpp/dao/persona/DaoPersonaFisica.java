package unpsjb.fipm.gisfpp.dao.persona;

import java.util.List;
import java.util.StringTokenizer;

import org.hibernate.Hibernate;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;

public class DaoPersonaFisica extends HibernateDaoSupport implements IDaoPersonaFisica {

	@Override
	public Integer crear(PersonaFisica instancia) throws DataAccessException {
		// si la persona a crear no tiene un usuario establecido se le crea uno
		// por defecto con dni como nickname(si no posee dni, se crea un
		// nickname por defecto) y password por defecto "unpsjbfipm"
		if (instancia.getUsuario() == null) {
			Usuario usuario = new Usuario();
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
			usuario.setActivo(false);
			instancia.setUsuario(usuario);
		}
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

}// fin de la clase
