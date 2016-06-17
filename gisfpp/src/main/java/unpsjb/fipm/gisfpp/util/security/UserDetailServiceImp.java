package unpsjb.fipm.gisfpp.util.security;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import unpsjb.fipm.gisfpp.entidades.Operaciones;
import unpsjb.fipm.gisfpp.entidades.Roles;
import unpsjb.fipm.gisfpp.entidades.persona.DatoDeContacto;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.TDatosContacto;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.servicios.IServiciosPermisos;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioUsuario;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class UserDetailServiceImp implements UserDetailsService {

	private IServicioUsuario servUsuario;
	private IServiciosPermisos servPermisos;
	private Usuario usuario;
	private List<RolUsuario> roles;
	private EnumSet<Operaciones> operacionesAutorizadas;
	
	private Logger log;
	
	public UserDetailServiceImp() {
		log = UtilGisfpp.getLogger();
	}

	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		try {
			if(getAdministrador(arg0)){
				return new DetalleUsuario(usuario, roles, operacionesAutorizadas);
			}else if(getUsuario(arg0)){
				return new DetalleUsuario(usuario, roles, operacionesAutorizadas);
			}else{
				throw new UsernameNotFoundException("Usuario inexistente.");
			}
		} catch (Exception exc1) {
			log.error("Clase: "+this.getClass().getName()+" - Método: loadUserByUsername(String)", exc1);
			throw new UsernameNotFoundException("Se ha generado un error al intentar recuperar los datos de identificación de usuario.", exc1);
		}
	}
	
	private boolean getAdministrador(String nickname) throws Exception{
		Usuario auxUsuario = new Usuario();
		PersonaFisica auxPersona = new PersonaFisica();
		auxUsuario.setPersona(auxPersona);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		ClassPathResource pathDocument = new ClassPathResource("gisfpp-admin.xml");
		Document documento = builder.parse(pathDocument.getFile());
			
		NodeList administradores = documento.getElementsByTagName("usuario");
		for (int i = 0; i < administradores.getLength(); i++) {
			Element tagUsuario = (Element) administradores.item(i);
			if (tagUsuario.getAttribute("nombre_usuario").equals(nickname)) {
				auxUsuario.setNickname(nickname);
				auxUsuario.setPassword(tagUsuario.getAttribute("password"));
				auxUsuario.setActivo(true);
				auxPersona.setNombre(tagUsuario.getAttribute("nombre"));
				auxPersona.getDatosDeContacto().add(new DatoDeContacto(TDatosContacto.EMAIL, tagUsuario.getAttribute("mail")));
				usuario = auxUsuario;
				
				RolUsuario rol = new RolUsuario(0, Roles.ADMINISTRADOR.name(), null, null, 0);
				roles = new ArrayList<RolUsuario>();
				roles.add(rol);
				
				List<Operaciones> listResultado = servPermisos.getOperacionesxRol(Roles.ADMINISTRADOR);
				//TODO 
				if (!listResultado.isEmpty()){
					operacionesAutorizadas = EnumSet.copyOf(listResultado);
				}
				return true;
			}
		}
		return false;
	}
	
	private boolean getUsuario(String nickname) throws Exception{
		usuario = servUsuario.getUsuario(nickname);
		roles = servUsuario.getRoles(usuario);
		if(usuario==null){
			return false;
		}
		if(roles.isEmpty()){
			RolUsuario rol = new RolUsuario(0, Roles.VISITANTE.name(),null,null,0);
			roles.add(rol);
			List<Operaciones> listResultado = servPermisos.getOperacionesxRol(Roles.VISITANTE);
			operacionesAutorizadas = (listResultado.isEmpty())? null: EnumSet.copyOf(listResultado);
		}else{
			operacionesAutorizadas = EnumSet.noneOf(Operaciones.class);
			for (RolUsuario auxRolUsuario : roles) {
				Roles rol = Roles.valueOf(auxRolUsuario.getRol());
				List<Operaciones> listResultado = servPermisos.getOperacionesxRol(rol);
				operacionesAutorizadas.addAll(listResultado);
			}
		}
		return true;
	}

	@Autowired(required = true)
	public void setServUsuario(IServicioUsuario servicio) {
		this.servUsuario = servicio;
	}

	@Autowired(required=true)
	public void setServPermisos(IServiciosPermisos servPermisos) {
		this.servPermisos = servPermisos;
	}
	
}// fin de la clase
