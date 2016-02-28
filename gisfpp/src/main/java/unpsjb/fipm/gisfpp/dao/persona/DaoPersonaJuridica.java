package unpsjb.fipm.gisfpp.dao.persona;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class DaoPersonaJuridica extends HibernateDaoSupport implements IDaoPersonaJuridica {
	
	private Logger log = UtilGisfpp.getLogger();

	@Override
	public Integer crear(PersonaJuridica instancia) throws DataAccessException {
		try{
			getHibernateTemplate().save(instancia);
			return instancia.getId();
		}catch(Exception e){
			log.debug(this.getClass().getName(), e);
			throw e;
		}
				
	}

	@Override
	public void actualizar(PersonaJuridica instancia) throws DataAccessException {
		try {
			getHibernateTemplate().update(instancia);
		} catch (Exception e) {
			log.debug(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	public void eliminar(PersonaJuridica instancia) throws DataAccessException {
		try {
			getHibernateTemplate().delete(instancia);
		} catch (Exception e) {
			log.debug(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	public List<PersonaJuridica> recuperarTodo() throws DataAccessException {
		String query ="select pj from PersonaJuridica pj left join fetch pj.identificadores";
		try {
			return (List<PersonaJuridica>) getHibernateTemplate().find(query, null);
		} catch (Exception e) {
			log.debug(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	public PersonaJuridica recuperarxId(Integer id) throws DataAccessException {
		String query ="select pj from PersonaJuridica pj left join fetch pj.identificadores where pj.id=?";
		try {
			getHibernateTemplate().setCacheQueries(true);
			List<PersonaJuridica> result = (List<PersonaJuridica>) getHibernateTemplate().find(query, id);
			if(result==null || result.isEmpty()){
				return null;
			}else{
				getHibernateTemplate().initialize(result.get(0).getContactos());
				for (PersonaFisica persona : result.get(0).getContactos()) {
					getHibernateTemplate().initialize(persona.getIdentificadores());
				}
				getHibernateTemplate().initialize(result.get(0).getDatosDeContacto());
				getHibernateTemplate().initialize(result.get(0).getDomicilios());
				return result.get(0);
			}
		} catch (Exception e) {
			log.debug(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	public List<PersonaJuridica> getxNombre(String patron) throws Exception {
		String query ="select pj from PersonaJuridica as pj left join fetch pj.identificadores where pj.nombre like concat('%', ?, '%') ";
		try {
			return (List<PersonaJuridica>) getHibernateTemplate().find(query, patron);
		} catch (Exception e) {
			log.debug(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	public List<PersonaJuridica> getxCuit(String patron) throws Exception {
		String query ="select pj from PersonaJuridica as pj inner join pj.identificadores as id where id.tipo='CUIT' and id.valor like concat('%',?,'%')";
		try {
			List<PersonaJuridica> result = (List<PersonaJuridica>) getHibernateTemplate().find(query, patron);
			if(result ==null || result.isEmpty()){
				return null;
			}else{
				for (PersonaJuridica item : result) {
					getHibernateTemplate().initialize(item.getIdentificadores());
				}
				return result;
			}
		} catch (Exception e) {
			log.debug(this.getClass().getName(), e);
			throw e;
		}
	}

}// fin de la clase
