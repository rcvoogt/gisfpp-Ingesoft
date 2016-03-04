package unpsjb.fipm.gisfpp.controladores.workflow;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.servicios.workflow.ProcesoGisfpp;
import unpsjb.fipm.gisfpp.servicios.workflow.SolicitudNuevaIsfpp;
import unpsjb.fipm.gisfpp.servicios.workflow.entidades.InfoTarea;
import unpsjb.fipm.gisfpp.util.GisfppWorkflowException;

public class VMDlgReuEstCondiciones {
	
	private ProcesoGisfpp servProceso;
	private InfoTarea tarea;
	
		
	@Init
	@NotifyChange("tarea")
	public void init(){
		@SuppressWarnings("unchecked")
		Map<String, Object> args = (Map<String, Object>) Executions.getCurrent().getArg();
		tarea = (InfoTarea) args.get("tarea");
		servProceso = (ProcesoGisfpp) SpringUtil.getBean("servSolicitudNuevaIsfpp");
	}

	public InfoTarea getTarea() {
		return tarea;
	}

	public void completarTarea(@BindingParam("aprobar") boolean arg1, 
			@BindingParam("motivo") String motivoRechazo) throws GisfppWorkflowException{
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(SolicitudNuevaIsfpp.VAR_ISFPP_APROBADA, arg1);
		args.put(SolicitudNuevaIsfpp.VAR_MOTIVO_RECHAZO, motivoRechazo);
		
		servProceso.completarTarea(tarea.getId(), args);
		
		//Refrescamos las lista de tareas tanto "asignadas" como "realizadas" en la vista "Bandeja de tareas"
		BindUtils.postGlobalCommand(null, null, "refrescarTareasAsignadas", null);
		BindUtils.postGlobalCommand(null, null, "refrescarTareasRealizadas", null);
		cerrar();
	}
	
	private void cerrar(){
		Window dlg = (Window) Path.getComponent("/dlgReunionEstCondiciones");
		dlg.detach();
	}
}
