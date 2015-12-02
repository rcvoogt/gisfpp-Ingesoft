package unpsjb.fipm.gisfpp.util.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import unpsjb.fipm.gisfpp.entidades.persona.Permiso;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;

@SuppressWarnings("serial")
public class DetalleUsuario implements UserDetails {

	private Usuario usuario;

	public DetalleUsuario(Usuario usuario) {
		super();
		this.usuario = usuario;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> autorizaciones = new ArrayList<SimpleGrantedAuthority>();
		for (Permiso permiso : usuario.getPermisosVigentes()) {
			autorizaciones.add(new SimpleGrantedAuthority(permiso.getRol().name()));
		}
		return autorizaciones;
	}

	@Override
	public String getPassword() {
		return usuario.getPassword();
	}

	@Override
	public String getUsername() {
		return usuario.getNickname();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return usuario.getActivo();
	}

}
