package unpsjb.fipm.gisfpp.util.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioUsuario;

public class UserDetailServiceImp implements UserDetailsService {

	IServicioUsuario servicio;
	Usuario usuario;

	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		try {
			usuario = servicio.recupararxNombreUsuario(arg0);
			if (usuario == null) {
				throw new UsernameNotFoundException("Usuario Inexistente.");
			}
		} catch (Exception e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
		/*
		 * if (usuario.getPermisosVigentes().isEmpty()) { throw new
		 * UsernameNotFoundException("Usuario sin permisos vigentes."); }
		 */
		return new DetalleUsuario(usuario);
	}

	@Autowired(required = true)
	public void setServicio(IServicioUsuario servicio) {
		this.servicio = servicio;
	}

}// fin de la clase
