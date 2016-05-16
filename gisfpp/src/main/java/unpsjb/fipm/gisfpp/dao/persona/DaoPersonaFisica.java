package unpsjb.fipm.gisfpp.dao.persona;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import unpsjb.fipm.gisfpp.entidades.persona.DatoDeContacto;
import unpsjb.fipm.gisfpp.entidades.persona.Domicilio;
import unpsjb.fipm.gisfpp.entidades.persona.Identificador;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.TIdentificador;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class DaoPersonaFisica extends HibernateDaoSupport implements IDaoPersonaFisica {

	private Logger log = UtilGisfpp.getLogger();

	@Override
	public Integer crear(PersonaFisica instancia) throws DataAccessException {
		try {
			getHibernateTemplate().save(instancia);
			return instancia.getId();
		} catch (DataIntegrityViolationException exc) {
			instancia.setId(null);
			for (Domicilio domicilio : instancia.getDomicilios()) {
				domicilio.setId(null);
			}
			for (DatoDeContacto datoContacto : instancia.getDatosDeContacto()) {
				datoContacto.setId(null);
			}
			for (Identificador identificador : instancia.getIdentificadores()) {
				identificador.setId(null);
			}
			instancia.getUsuario().setId(null);
			throw exc;
		}
	}

	@Override
	public void actualizar(PersonaFisica instancia) throws DataAccessException {
		getHibernateTemplate().update(instancia);
	}

	@Override
	public void eliminar(PersonaFisica instancia) throws DataAccessException{
		getHibernateTemplate().delete(instancia);
		
	}

	@Override
	public List<PersonaFisica> recuperarTodo() throws DataAccessException {
		String query = "select pf from PersonaFisica pf left join fetch pf.identificadores";
		List<PersonaFisica> lista = (List<PersonaFisica>) getHibernateTemplate().find(query, null);
		//La consulta devuelve Personas duplicadas segun la cantidad de identificadores
		//registrados que tenga. Por eso se utiliza este truco de pasar la lista resultado a un Set que 
		//por definicion no permite duplicados, eliminando dichos elementos duplicados de la lista
		Set<PersonaFisica> listaSinDuplicados = new LinkedHashSet<PersonaFisica>(lista);
		lista.clear();
		lista.addAll(listaSinDuplicados);
 		return lista;
	}

	@Override
	public PersonaFisica recuperarxId(Integer id) throws DataAccessException {
		String query ="select pf from PersonaFisica pf left join fetch pf.identificadores left join fetch pf.usuario where pf.id = ?";
		List<PersonaFisica> result = (List<PersonaFisica>) getHibernateTemplate().find(query, id);
		if(result ==null || result.isEmpty()){
			return null;
		}else{
			getHibernateTemplate().initialize(result.get(0).getDomicilios());
			getHibernateTemplate().initialize(result.get(0).getDatosDeContacto());
			return result.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PersonaFisica> getxNombre(String patronNombre) throws Exception {
		String query ="select pf from PersonaFisica pf left join fetch pf.identificadores where pf.nombre like concat('%',?,'%')";
		List<PersonaFisica> result = (List<PersonaFisica>) getHibernateTemplate().find(query, patronNombre);
		//La consulta devuelve Personas duplicadas segun la cantidad de identificadores
		//registrados que tenga. Por eso se utiliza este truco de pasar la lista resultado a un Set que 
		//por definicion no permite duplicados, eliminando dichos elementos duplicados de la lista
		if(result != null && !result.isEmpty()){
			Set<PersonaFisica> listaSinDuplicados = new LinkedHashSet<PersonaFisica>(result);
			result.clear();
			result.addAll(listaSinDuplicados);
	 		return result;
		}else{
			return null;
		}
	}

	@Override
	public List<PersonaFisica> getxIdentificador(TIdentificador tipo, String patron) throws Exception {
		String query ="select pf from PersonaFisica pf inner join pf.identificadores id where id.tipo =? and id.valor like concat('%',?,'%')";
		List<PersonaFisica> result = (List<PersonaFisica>) getHibernateTemplate().find(query, tipo,patron);
		//Al no poder inicializar la coleccion de identificadores en la consulta anterior (no se pudo usar inner join fetch)
		//se inicializa los identificadores de las personas resultado de la consulta manualmente.
		if(result!=null && !result.isEmpty()){
			for (PersonaFisica persona : result) {
				getHibernateTemplate().initialize(persona.getIdentificadores());
				getHibernateTemplate().initialize(persona.getDatosDeContacto());
			}
			return result;
		}else{
			return null;
		}
	}

				
}// fin de la clase
