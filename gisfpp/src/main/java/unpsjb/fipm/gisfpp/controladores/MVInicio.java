package unpsjb.fipm.gisfpp.controladores;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

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
	
	@Command("verDlgPerfilUsuario")
	public void verDlgPerfilUsuario(){
		Window dlg = (Window) Executions.createComponents("vistas/persona/dlgPerfilUsuario.zul", null, null);
		dlg.doModal();
	}
	
}// fin de la clase
