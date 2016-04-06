package unpsjb.fipm.gisfpp.util.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import unpsjb.fipm.gisfpp.entidades.Operaciones;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;

@SuppressWarnings("serial")
public class DetalleUsuario implements UserDetails {

	private Usuario usuario;
	private List<RolUsuario> roles;
	private EnumSet<Operaciones> operacionesAutorizadas;

	public DetalleUsuario(Usuario usuario, List<RolUsuario> roles,
			EnumSet<Operaciones> operaciones) {
		super();
		this.usuario = usuario;
		this.roles = (roles==null)? new LinkedList<RolUsuario>():roles;
		this.operacionesAutorizadas = (operaciones==null)?EnumSet.noneOf(Operaciones.class):operaciones;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> autorizaciones = new ArrayList<SimpleGrantedAuthority>();
		for (Operaciones operacionAutorizada : operacionesAutorizadas) {
			autorizaciones.add(new SimpleGrantedAuthority(operacionAutorizada.name()));
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

	public EnumSet<Operaciones> getOperacionesAutorizadas() {
		return operacionesAutorizadas;
	}

	public void setOperacionesAutorizadas(EnumSet<Operaciones> operacionesAutorizadas) {
		this.operacionesAutorizadas = operacionesAutorizadas;
	}
	
}// fin de la clase
