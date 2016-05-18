package unpsjb.fipm.gisfpp.dao.persona;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import unpsjb.fipm.gisfpp.entidades.persona.DatoDeContacto;
import unpsjb.fipm.gisfpp.entidades.persona.Domicilio;
import unpsjb.fipm.gisfpp.entidades.persona.Identificador;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;

public class DaoPersonaJuridica extends HibernateDaoSupport implements IDaoPersonaJuridica {
	
	@Override
	public Integer crear(PersonaJuridica instancia) throws DataAccessException {
		try{
			getHibernateTemplate().save(instancia);
			return instancia.getId();
		}catch(DataIntegrityViolationException exc){
			instancia.setId(null);
			for (DatoDeContacto dato : instancia.getDatosDeContacto()) {
				dato.setId(null);
			}
			for (Domicilio domicilio : instancia.getDomicilios()) {
				domicilio.setId(null);
			}
			for (Identificador identificador : instancia.getIdentificadores()) {
				identificador.setId(null);
			}
			throw exc;
		}
				
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
		String query ="select pj from PersonaJuridica pj left join fetch pj.identificadores";
		return (List<PersonaJuridica>) getHibernateTemplate().find(query, null);
	}

	@Override
	public PersonaJuridica recuperarxId(Integer id) throws DataAccessException {
		String query ="select pj from PersonaJuridica pj left join fetch pj.identificadores where pj.id=?";
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
	}

	@Override
	public List<PersonaJuridica> getxNombre(String patron) throws Exception {
		String query ="select pj from PersonaJuridica as pj left join fetch pj.identificadores where pj.nombre like concat('%', ?, '%') ";
		return (List<PersonaJuridica>) getHibernateTemplate().find(query, patron);
	}

	@Override
	public List<PersonaJuridica> getxCuit(String patron) throws Exception {
		String query ="select pj from PersonaJuridica as pj inner join pj.identificadores as id where id.tipo='CUIT' and id.valor like concat('%',?,'%')";
		List<PersonaJuridica> result = (List<PersonaJuridica>) getHibernateTemplate().find(query, patron);
		if(result ==null || result.isEmpty()){
			return null;
		}else{
			for (PersonaJuridica item : result) {
				getHibernateTemplate().initialize(item.getIdentificadores());
			}
			return result;
		}
	}

}// fin de la clase
