package unpsjb.fipm.gisfpp.util.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioUsuario;

public class UserDetailServiceImp implements UserDetailsService {

	IServicioUsuario servicio;
	Usuario usuario;
	List<RolUsuario> roles;

	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		try {
			usuario = servicio.getUsuario(arg0);
			roles = servicio.getRoles(usuario);
			if (usuario == null) {
				throw new UsernameNotFoundException("Usuario Inexistente.");
			}
		} catch (Exception e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
		return new DetalleUsuario(usuario, roles);
	}

	@Autowired(required = true)
	public void setServicio(IServicioUsuario servicio) {
		this.servicio = servicio;
	}

}// fin de la clase
