package unpsjb.fipm.gisfpp.controladores.workflow.tareas;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.entidades.workflow.InfoTarea;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorTareas;
import unpsjb.fipm.gisfpp.util.GisfppWorkflowException;
import unpsjb.fipm.gisfpp.util.security.UtilSecurity;

public class VMDlgRevSolNuevaIsfpp {
	
	private GestorTareas servGTareas;
	private InfoTarea tarea;
	
	
	@Init
	@NotifyChange("tarea")
	public void init(){
		@SuppressWarnings("unchecked")
		Map<String, Object> parametros = (Map<String, Object>) Executions.getCurrent().getArg();
		tarea = (InfoTarea) parametros.get("tarea");
		servGTareas = (GestorTareas) SpringUtil.getBean("servGestionTareas");
	}

	public InfoTarea getTarea() {
		return tarea;
	}
	
		
	@Command("completar")
	public void completarTarea(@BindingParam ("continuar")boolean arg1,
			@BindingParam("motivo")String arg2) throws GisfppWorkflowException{
		Usuario usuarioConectado = UtilSecurity.getUsuarioConectado();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("continuar", arg1);
		variables.put("motivoRechazo", arg2);
		variables.put("usuarioResponsable", usuarioConectado.getNickname());
		variables.put("nombreResponsable", usuarioConectado.getPersona().getNombre());
		variables.put("mailResponsable", usuarioConectado.getPersona().getEmail());
		servGTareas.tratarTarea(tarea, variables);
		//Refrescamos las lista de tareas tanto "asignadas" como "realizadas" en la vista "Bandeja de tareas"
		BindUtils.postGlobalCommand(null, null, "refrescarTareasAsignadas", null);
		BindUtils.postGlobalCommand(null, null, "refrescarTareasRealizadas", null);
		BindUtils.postGlobalCommand(null, null, "refrescarTareasDelegadas", null);
		cerrar();
	}
	
	@Command("cancelar")
	public void cancelarTratamientoTarea(){
		cerrar();
	}
	
	private void cerrar(){
		Window dlg = (Window) Path.getComponent("/dlgRevSolNuevaIsfpp");
		dlg.detach();
	}
	
}
