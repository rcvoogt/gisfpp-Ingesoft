package unpsjb.fipm.gisfpp.controladores.workflow;

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

import unpsjb.fipm.gisfpp.entidades.workflow.InfoTarea;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorTareas;
import unpsjb.fipm.gisfpp.util.GisfppWorkflowException;

public class VMDlgReuEstCondiciones {
	
	private GestorTareas servGTareas;
	private InfoTarea tarea;
	
		
	@Init
	@NotifyChange("tarea")
	public void init(){
		@SuppressWarnings("unchecked")
		Map<String, Object> args = (Map<String, Object>) Executions.getCurrent().getArg();
		tarea = (InfoTarea) args.get("tarea");
		servGTareas = (GestorTareas) SpringUtil.getBean("servGestionTareas");
	}

	public InfoTarea getTarea() {
		return tarea;
	}

	@Command("completar")
	public void completarTarea(@BindingParam("aprobar") boolean arg1, 
			@BindingParam("motivo") String motivoRechazo) throws GisfppWorkflowException{
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("isfppAprobada", arg1);
		args.put("motivoRechazo", motivoRechazo);
		
		servGTareas.tratarTarea(tarea, args);
		
		//Refrescamos las listas de tareas y procesos en la vista de la bandeja de actividades.
		BindUtils.postGlobalCommand(null, null, "refrescarTareasAsignadas", null);
		BindUtils.postGlobalCommand(null, null, "refrescarTareasRealizadas", null);
		BindUtils.postGlobalCommand(null, null, "refrescarTareasDelegadas", null);
		BindUtils.postGlobalCommand(null, null, "refrescarProcesosActivos", null);
		BindUtils.postGlobalCommand(null, null, "refrescarProcesosFinalizados", null);
		cerrar();
	}
	
	@Command("cancelar")
	public void cancelarTratamientoTarea(){
		cerrar();
	}
	
	private void cerrar(){
		Window dlg = (Window) Path.getComponent("/dlgReunionEstCondiciones");
		dlg.detach();
	}
}
