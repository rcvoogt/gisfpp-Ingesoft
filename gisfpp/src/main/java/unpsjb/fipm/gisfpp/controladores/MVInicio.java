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

	@Init
	public void init() throws Exception {
		
	}

	@Command("verListaPersonas")
	public void verListaPersonas() {
		UtilGuiGisfpp.loadPnlCentral("vistas/persona/listadoPersonas.zul");
	}

	@Command("verListaProyectos")
	public void verListaProyectos() {
		UtilGuiGisfpp.loadPnlCentral("vistas/proyecto/listadoProyectos.zul");
	}

	@Command("verListaStaff")
	public void verListaStaff() {
		UtilGuiGisfpp.loadPnlCentral("vistas/staff/listadoStaffFI.zul");
	}

	@Command("verListaOrganizaciones")
	public void verListaOrganizaciones() {
		UtilGuiGisfpp.loadPnlCentral("vistas/persona/listadoOrganizaciones.zul");
	}
	
}// fin de la clase
