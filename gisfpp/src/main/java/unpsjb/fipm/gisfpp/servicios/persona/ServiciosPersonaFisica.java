package unpsjb.fipm.gisfpp.servicios.persona;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import unpsjb.fipm.gisfpp.dao.persona.IDaoPersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.TIdentificador;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.util.security.UtilSecurity;

@Service("servPersonaFisica")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServiciosPersonaFisica implements IServicioPF {

	private IDaoPersonaFisica dao;

	
	@Autowired(required = true)
	public void setDao(IDaoPersonaFisica dao) {
		this.dao = dao;
	}


	@Override
	@Transactional(value="gisfpp", readOnly=false)
	public Integer persistir(PersonaFisica instancia) throws Exception {
		isPersonaNull(instancia);
		if ((instancia.getUsuario()==null) || (instancia.getUsuario().getNickname().isEmpty())){
			Usuario usuarioDefault = UtilSecurity.generarUsuario(instancia);
			instancia.setUsuario(usuarioDefault);
		}
		instancia.getUsuario().setPersona(instancia);
		return dao.crear(instancia);
	}


	@Override
	@Transactional(value="gisfpp", readOnly=false)
	public void editar(PersonaFisica instancia) throws Exception {
		isPersonaNull(instancia);
		dao.actualizar(instancia);		
	}


	@Override
	@Transactional(value="gisfpp", readOnly=false)
	public void eliminar(PersonaFisica instancia) throws Exception {
		Assert.notNull(instancia, "Persona: referencia null.");
		dao.eliminar(instancia);
	}


	@Override
	@Transactional(value="gisfpp", readOnly=true)
	public PersonaFisica getInstancia(Integer id) throws Exception {
		PersonaFisica pf = dao.recuperarxId(id);
		if(pf.getUsuario()==null){
			pf.setUsuario(new Usuario());
		}
		return pf;
	}


	@Override
	@Transactional(value="gisfpp", readOnly=true)
	public List<PersonaFisica> getListado() throws Exception {
		return dao.recuperarTodo();
	}
	
	@Override
	@Transactional(value="gisfpp", readOnly=true)
	public List<PersonaFisica> getAlumnosConMail() throws Exception {
		return dao.recuperarTodo();
	}


	@Override
	@Transactional(value="gisfpp", readOnly=true)
	public List<PersonaFisica> getxNombre(String patronNombre) throws Exception {
		return dao.getxNombre(patronNombre);
	}


	@Override
	@Transactional(value="gisfpp", readOnly=true)
	public List<PersonaFisica> getxIdentificador(TIdentificador campo, String patronValor) throws Exception {
		return dao.getxIdentificador(campo, patronValor);
	}

	private void isPersonaNull(PersonaFisica referencia) throws NullPointerException{
		if(referencia==null){
			throw new NullPointerException("Persona: referencia null");
		}
	}

	@Transactional
	public void persistir(List<PersonaFisica> personasFisicas) {
		
	}

	@Override
	@Transactional(value="gisfpp", readOnly=false)
	public int actualizarOguardar(PersonaFisica instancia) throws Exception {
		
		PersonaFisica persona;
		if(dao.existe(instancia.getLegajo())) {
			persona = getxIdentificador(TIdentificador.LEGAJO, instancia.getLegajo()).get(0);
			instancia.setId(persona.getId());
//			instancia.getUsuario().setNickname(persona.getUsuario().getNickname());
//			instancia.getUsuario().setPassword(persona.getUsuario().getPassword());
//			instancia.getUsuario().setActivo(true);
			dao.actualizar(instancia);
			return instancia.getId();
		}
		dao.crear(instancia);
		return instancia.getId();
	}

	@Override
	@Transactional(value="gisfpp", readOnly=true)
	public boolean existe(String legajo) {
		return dao.existe(legajo);
	}
	
}//fin de la clase
