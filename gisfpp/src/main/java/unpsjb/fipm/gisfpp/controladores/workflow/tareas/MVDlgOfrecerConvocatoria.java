package unpsjb.fipm.gisfpp.controladores.workflow.tareas;

import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.workflow.InfoTarea;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorTareas;

public class MVDlgOfrecerConvocatoria {
	
	private GestorTareas servTareas;
	private InfoTarea tarea;
	
	@Init
	@NotifyChange("tarea")
	public void init() throws Exception{
		@SuppressWarnings("unchecked")
		Map<String, Object> args = (Map<String, Object>) Executions.getCurrent().getArg();
		tarea = (InfoTarea) args.get("tarea");
		servTareas = (GestorTareas) SpringUtil.getBean("servGestionTareas");
	}

	public InfoTarea getTarea() {
		return tarea;
	}

	@Command("completarTarea")
	public void completarTarea() throws Exception{
		
		
		servTareas.tratarTarea(tarea);
		
		//Refrescamos las listas de tareas y procesos en la vista de la bandeja de actividades.
		BindUtils.postGlobalCommand(null, null, "refrescarTareasAsignadas", null);
		BindUtils.postGlobalCommand(null, null, "refrescarTareasRealizadas", null);
		//Ver como mandarle a quien llamar
		BindUtils.postGlobalCommand(null, null, "abrirPantallaAlCerrarDialogo", null);
		
		cerrar();
	}
	
	@Command("cancelar")
	public void cancelarTratamientoTarea(){
		cerrar();
	}
	
	private void cerrar(){
		Window dlg = (Window) Path.getComponent("/dlgOfrecerConvocatoria");
		dlg.detach();
	}
	
}
