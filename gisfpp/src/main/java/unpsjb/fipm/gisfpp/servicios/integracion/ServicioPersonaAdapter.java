package unpsjb.fipm.gisfpp.servicios.integracion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.integracion.dao.IDaoPersonaAdapter;
import unpsjb.fipm.gisfpp.integracion.entidades.PersonaAdapter;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPF;
	
@Service("servPersonaAdapter")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServicioPersonaAdapter implements IServicioPersonaAdapter{
	@Autowired
	private IServicioPF servPersonaFisica;
	private IDaoPersonaAdapter dao;

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public Integer persistir(PersonaAdapter instancia) throws Exception {
		return dao.crear(instancia);
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void editar(PersonaAdapter instancia) throws Exception {
		dao.actualizar(instancia);
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void eliminar(PersonaAdapter instancia) throws Exception {
		dao.eliminar(instancia);
	}

	@Override
	public PersonaAdapter getInstancia(Integer id) throws Exception {
		return dao.recuperarxId(id);
	}

	@Override
	public List<PersonaAdapter> getListado() throws Exception {
		return dao.recuperarTodo();
	}
	@Autowired
	public void setDao(IDaoPersonaAdapter dao) {
		this.dao = dao;
	}

	@Override
	@Transactional
	public int existe(String nroInscripcion) throws Exception {
		PersonaAdapter personaAdapter = dao.recuperarxNroInscripcion(nroInscripcion);
		if(personaAdapter == null)
			return -1;
		return personaAdapter.getIdPersona();
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public int actualizarOguardar(PersonaAdapter instancia) throws DataAccessException, Exception {
		PersonaAdapter aux = dao.recuperarxNroInscripcion(instancia.getNroInscripcion());
		if(aux == null) {
			dao.crear(instancia);
			return -1;
		}	
		if(aux.getIdPersona() == null)
			return -1;
		return aux.getIdPersona();
	}

	@Override
	@Transactional
	public PersonaFisica getPFxLegajo(String legajo) throws Exception {
		Integer idPersona;
		PersonaAdapter personaAdapter = dao.recuperarxLegajo(legajo);
		if(personaAdapter == null)
			return null;
		idPersona = personaAdapter.getIdPersona();
		if(idPersona == null)
			return null;
		PersonaFisica personaFisica = servPersonaFisica.getInstancia(idPersona);
		return personaFisica;
	}

	@Override
	public PersonaAdapter getPAxNroInscripcion(String nro_inscripcion) {
		return dao.recuperarxNroInscripcion(nro_inscripcion);
	}

	
}
