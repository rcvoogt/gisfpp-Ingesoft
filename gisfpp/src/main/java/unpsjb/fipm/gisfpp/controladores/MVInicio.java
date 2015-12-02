package unpsjb.fipm.gisfpp.controladores;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.spring.SpringUtil;
import org.zkoss.spring.security.SecurityUtil;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Include;

import unpsjb.fipm.gisfpp.entidades.persona.Permiso;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioUsuario;

public class MVInicio {

	private Usuario usuario;
	private String nameUser;
	private List<Permiso> permisos;
	private boolean logeado = false;
	private IServicioUsuario servicio;

	@Init
	public void init() throws Exception {
		nameUser = SecurityUtil.getAuthentication().getName();
		if (!nameUser.equals("anonymousUser")) {
			servicio = (IServicioUsuario) SpringUtil.getBean("servUsuario");
			usuario = (Usuario) servicio.recupararxNombreUsuario(nameUser);
			logeado = true;
		}
	}

	@Command("verListaPersonas")
	public void verListaPersonas() {
		Include include = (Include) Path.getComponent("/panelCentro");
		include.setSrc(null);
		include.setSrc("vistas/persona/listarPersonas.zul");
	}

	@Command("verListaProyectos")
	public void verListaProyectos() {
		Include include = (Include) Path.getComponent("/panelCentro");
		include.setSrc(null);
		include.setSrc("vistas/proyecto/listarProyectos.zul");
	}

	@Command("verListaStaff")
	public void verListaStaff() {
		Include include = (Include) Path.getComponent("/panelCentro");
		include.setSrc(null);
		include.setSrc("vistas/staff/listaStaffFI.zul");
	}

	@Command("verListaOrganizaciones")
	public void verListaOrganizaciones() {
		Include include = (Include) Path.getComponent("/panelCentro");
		include.setSrc(null);
		include.setSrc("vistas/persona/listaOrganizaciones.zul");
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public boolean isLogeado() {
		return logeado;
	}

}// fin de la clase
