package unpsjb.fipm.gisfpp.util.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import unpsjb.fipm.gisfpp.entidades.persona.Usuario;

@SuppressWarnings("serial")
public class DetalleUsuario implements UserDetails {

	private Usuario usuario;
	private List<RolUsuario> roles;

	public DetalleUsuario(Usuario usuario, List<RolUsuario> roles) {
		super();
		this.usuario = usuario;
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> autorizaciones = new ArrayList<SimpleGrantedAuthority>();
		if (roles == null || roles.isEmpty()) {
			return autorizaciones;
		}
		for (RolUsuario rol : roles) {
			autorizaciones.add(new SimpleGrantedAuthority(rol.getRol() + "-" + rol.getTabla()));
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<RolUsuario> getRoles() {
		return roles;
	}

	public void setRoles(List<RolUsuario> roles) {
		this.roles = roles;
	}

}// fin de la clase
