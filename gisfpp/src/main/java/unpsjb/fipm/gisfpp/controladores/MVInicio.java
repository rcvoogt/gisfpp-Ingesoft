package unpsjb.fipm.gisfpp.controladores;

import java.util.HashMap;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;
import unpsjb.fipm.gisfpp.util.security.UtilSecurity;

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
	
	@Command("verListaOfertasActividades")
	public void verListaOfertasActividades(){
		UtilGuiGisfpp.loadPnlCentral("vistas/proyecto/listaOfertaActividades.zul");
	}

	@Command("verListaStaff")
	public void verListaStaff() {
		UtilGuiGisfpp.loadPnlCentral("vistas/staff/listadoStaffFI.zul");
	}

	@Command("verListaOrganizaciones")
	public void verListaOrganizaciones() {
		UtilGuiGisfpp.loadPnlCentral("vistas/persona/listadoOrganizaciones.zul");
	}
	
	@Command("verBandejaWorkflow")
	public void verBandejaActividades(){
		UtilGuiGisfpp.loadPnlCentral("vistas/workflow/bandejaWorkflow.zul");
	}
	
	@Command("verDlgPerfilUsuario")
	public void verDlgPerfilUsuario(){
		Window dlg = (Window) Executions.createComponents("vistas/persona/dlgPerfilUsuario.zul", null, null);
		dlg.doModal();
	}
	
	@Command("verPnlDatosPersonales")
	public void verPnlCrudPersona(){
		PersonaFisica usuarioConectado = UtilSecurity.getUsuarioConectado().getPersona();
		final HashMap<String, Object> argsLlamada = new HashMap<String, Object>();
		argsLlamada.put("modo", UtilGisfpp.MOD_VER);
		argsLlamada.put("idItem", usuarioConectado.getId());
		argsLlamada.put("btnVolverVisible", false);
		
		UtilGuiGisfpp.loadPnlCentral("vistas/persona/crudPersona.zul", argsLlamada);
	}
	
}// fin de la clase
