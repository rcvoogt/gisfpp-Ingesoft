package unpsjb.fipm.gisfpp.dao.persona;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.hibernate.Session;
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
	@Transactional(readOnly=false)
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
	@Transactional(readOnly=false)
	public void actualizar(PersonaFisica instancia) throws DataAccessException {
		try {
			getHibernateTemplate().update(instancia);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	@Transactional(readOnly=false)
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
		String query = "select pf from PersonaFisica pf left join fetch pf.identificadores";
		try {
			List<PersonaFisica> lista = (List<PersonaFisica>) getHibernateTemplate().find(query, null);
			//La consulta devuelve Personas duplicadas segun la cantidad de identificadores
			//registrados que tenga. Por eso se utiliza este truco de pasar la lista resultado a un Set que 
			//por definicion no permite duplicados, eliminando dichos elementos duplicados de la lista
			Set<PersonaFisica> listaSinDuplicados = new LinkedHashSet<PersonaFisica>(lista);
			lista.clear();
			lista.addAll(listaSinDuplicados);
 			return lista;
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}

	}

	@Override
	@Transactional(readOnly = true)
	public PersonaFisica recuperarxId(Integer id) throws DataAccessException {
		String query ="select pf from PersonaFisica pf left join fetch pf.identificadores	where pf.id = ?";
		try {
			getHibernateTemplate().setCacheQueries(true);
			List<PersonaFisica> result = (List<PersonaFisica>) getHibernateTemplate().find(query, id);
			if(result ==null || result.isEmpty()){
				return null;
			}else{
				getHibernateTemplate().initialize(result.get(0).getDatosDeContacto());
				getHibernateTemplate().initialize(result.get(0).getDomicilios());
				return result.get(0);
			}
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	public void setDaoUsuario(IDaoUsuario daoUsuario) {
		this.daoUsuario = daoUsuario;
	}

}// fin de la clase
