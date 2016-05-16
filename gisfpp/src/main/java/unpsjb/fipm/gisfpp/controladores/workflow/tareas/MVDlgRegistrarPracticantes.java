package unpsjb.fipm.gisfpp.controladores.workflow.tareas;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.workflow.InfoTarea;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorTareas;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorWorkflow;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;

public class MVDlgRegistrarPracticantes {
	
	private GestorTareas servTareas;
	private GestorWorkflow servWorkflow;
	private InfoTarea tarea;
	private IServiciosIsfpp servIsfpp;
	private Isfpp item;
		
	@Init
	@NotifyChange("tarea")
	public void init() throws Exception{
		@SuppressWarnings("unchecked")
		Map<String, Object> args = (Map<String, Object>) Executions.getCurrent().getArg();
		tarea = (InfoTarea) args.get("tarea");
		servTareas = (GestorTareas) SpringUtil.getBean("servGestionTareas");
		servWorkflow = (GestorWorkflow) SpringUtil.getBean("servGestionWorkflow");
		servIsfpp = (IServiciosIsfpp) SpringUtil.getBean("servIsfpp");
		Integer idIsfpp = Integer.valueOf(servWorkflow.getKeyBusiness(tarea.getIdInstanciaProceso()));
		item = servIsfpp.getInstancia(idIsfpp);
	}

	public InfoTarea getTarea() {
		return tarea;
	}
	
	@Command("completarTarea")
	public void completarTarea() throws Exception{
		
		if (item.getPracticantes()==null || item.getPracticantes().isEmpty()) {
			Clients.alert("Debe registrar los Practicantes correspondientes en la Isfpp asociada a esta tarea para poder dar por \"Concluida\""
					+ " la misma.", "Alerta: Worlflow", Messagebox.ERROR);
			return;
		}
		
		servTareas.tratarTarea(tarea);
		
		BindUtils.postGlobalCommand(null, null, "refrescarTareasAsignadas", null);
		BindUtils.postGlobalCommand(null, null, "refrescarTareasRealizadas", null);
		
		cerrar();
		
	}
	
	@Command("registrar")
	public void registrarPracticantes(){
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("isfpp", item);
		args.put("origen", null);
		UtilGuiGisfpp.mostrarDialogoBox("vistas/proyecto/dlgAsignarPracticantes.zul", args);
	}
	
	@Command("cancelar")
	public void cancelarTratamientoTarea(){
		cerrar();
	}
	
	private void cerrar(){
		Window dlg = (Window) Path.getComponent("/dlgRegistrarPracticantes");
		dlg.detach();
	}
	
}
