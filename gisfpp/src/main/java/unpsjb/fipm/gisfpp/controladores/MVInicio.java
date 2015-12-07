package unpsjb.fipm.gisfpp.controladores;

import java.util.HashMap;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.spring.security.SecurityUtil;
import org.zkoss.zk.ui.Sessions;

import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioUsuario;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;

public class MVInicio {

	private Usuario usuario;
	private String nameUser;
	private List<SimpleGrantedAuthority> permisos;
	private boolean logeado = false;
	private IServicioUsuario servicio;
	private StringBuilder toolTipRoles = new StringBuilder();

	@SuppressWarnings("unchecked")
	@Init
	@NotifyChange({ "logeado", "usuario", "toolTipRoles" })
	public void init() throws Exception {
		nameUser = SecurityUtil.getAuthentication().getName();
		if (!nameUser.equals("anonymousUser")) {
			servicio = (IServicioUsuario) SpringUtil.getBean("servUsuario");
			usuario = (Usuario) servicio.getUsuario(nameUser);
			permisos = (List<SimpleGrantedAuthority>) SecurityUtil.getAuthentication().getAuthorities();
			logeado = true;
			// Se establece en la sesion los datos del usuario actualmente
			// conectado
			HashMap<String, Object> login = new HashMap<>();
			login.put("usuario", usuario);
			login.put("permisos", permisos);
			Sessions.getCurrent().setAttribute("usuarioConectado", login);
			toolTipRoles.append("Rol: \n");
			for (SimpleGrantedAuthority permiso : permisos) {
				toolTipRoles.append("* " + permiso.getAuthority() + "\n");
			}
		}
	}

	@Command("verListaPersonas")
	public void verListaPersonas() {
		UtilGuiGisfpp.loadPnlCentral("vistas/persona/listarPersonas.zul");
	}

	@Command("verListaProyectos")
	public void verListaProyectos() {
		UtilGuiGisfpp.loadPnlCentral("vistas/proyecto/listarProyectos.zul");
	}

	@Command("verListaStaff")
	public void verListaStaff() {
		UtilGuiGisfpp.loadPnlCentral("vistas/staff/listaStaffFI.zul");
	}

	@Command("verListaOrganizaciones")
	public void verListaOrganizaciones() {
		UtilGuiGisfpp.loadPnlCentral("vistas/persona/listaOrganizaciones.zul");
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public boolean isLogeado() {
		return logeado;
	}

	public StringBuilder getToolTipRoles() {
		return toolTipRoles;
	}

}// fin de la clase
