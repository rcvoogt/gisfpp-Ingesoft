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
	
@Service("servPersonaAdapter")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServicioPersonaAdapter implements IServicioPersonaAdapter{
	
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
	public int existe(String legajo) throws Exception {
		return dao.existe(legajo);
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public int actualizarOguardar(PersonaAdapter instancia) throws DataAccessException, Exception {
		if(existe(instancia.getNro_inscripcion()) == -1) {
			dao.crear(instancia);
			return -1;
		}
		dao.actualizar(instancia);
		return instancia.getIdPersona();
	}

	@Override
	public PersonaFisica getPFxLegajo(String legajo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
}
