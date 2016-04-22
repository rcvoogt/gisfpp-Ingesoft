package unpsjb.fipm.gisfpp.controladores.workflow.tareas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.workflow.InfoTarea;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorTareas;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorWorkflow;

public class MVDlgConfeccionarAcuerdoConvenios {
	private GestorTareas servTareas;
	private InfoTarea tarea;
	private IServiciosIsfpp servIsfpp;
	private GestorWorkflow servWorkflow;
	private List<PersonaFisica> practicantes;
	
	@Init
	@NotifyChange("tarea")
	public void init() throws Exception{
		@SuppressWarnings("unchecked")
		Map<String, Object> args = (Map<String, Object>) Executions.getCurrent().getArg();
		tarea = (InfoTarea) args.get("tarea");
		servTareas = (GestorTareas) SpringUtil.getBean("servGestionTareas");
		servIsfpp = (IServiciosIsfpp) SpringUtil.getBean("servIsfpp");
		servWorkflow = (GestorWorkflow) SpringUtil.getBean("servGestionWorkflow");
		
		Integer idIsfpp = Integer.valueOf(servWorkflow.getKeyBusiness(tarea.getIdInstanciaProceso()));
		practicantes = servIsfpp.getPracticantes(idIsfpp);
	}

	public InfoTarea getTarea() {
		return tarea;
	}
	
	public List<PersonaFisica> getPracticantes(){
		return practicantes;
	}
	
	@Command("completarTarea")
	public void completarTarea() throws Exception{
		
		if (practicantes.isEmpty()) {
			Clients.alert("No se puede completar la tarea. No existen \"Practicantes\" "
					+ "registrados en la Isfpp correspondiente", "Alerta: Worlflow", Clients.NOTIFICATION_TYPE_WARNING);
			return;
		}
		List<String> listaPracticantes = getListaPracticantes();
		Map <String, Object> variablesProceso = new HashMap<String, Object>();
		variablesProceso.put("listaPracticantes", listaPracticantes);
		servTareas.tratarTarea(tarea, variablesProceso);
		
		//Refrescamos las listas de tareas y procesos en la vista de la bandeja de actividades.
		BindUtils.postGlobalCommand(null, null, "refrescarTareasAsignadas", null);
		BindUtils.postGlobalCommand(null, null, "refrescarTareasRealizadas", null);
		
		cerrar();
	}
	
	@Command("cancelar")
	public void cancelarTratamientoTarea(){
		cerrar();
	}
	
	private void cerrar(){
		Window dlg = (Window) Path.getComponent("/dlgConfeccionarAcuerdoConvenios");
		dlg.detach();
	}
	
	private List<String> getListaPracticantes(){
		List<String> resultado = new ArrayList<String>();
		for (PersonaFisica persona : practicantes) {
			resultado.add(persona.getNombre() +" (DNI: "+persona.getDni()+")");
		}
		return resultado;
	}
	
}
