package unpsjb.fipm.gisfpp.controladores.workflow.tareas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffIsfpp;
import unpsjb.fipm.gisfpp.entidades.workflow.InfoTarea;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorTareas;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorWorkflow;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;

public class MVDlgConfeccionarAcuerdoConvenios {
	private GestorTareas servTareas;
	private InfoTarea tarea;
	private IServiciosIsfpp servIsfpp;
	private GestorWorkflow servWorkflow;
	private Isfpp isfpp;
	
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
		isfpp = servIsfpp.getInstancia(idIsfpp);
	}

	public InfoTarea getTarea() {
		return tarea;
	}
	
	public Set<PersonaFisica> getPracticantes(){
		return isfpp.getPracticantes();
	}
	
	public Set<MiembroStaffIsfpp> getTutores(){
		return isfpp.getStaff();
	}
	
	@Command("completarTarea")
	public void completarTarea() throws Exception{
		
		if (isfpp.getPracticantes().isEmpty()) {
			Clients.alert("No se puede completar la tarea. No existen \"Practicantes\" "
					+ "registrados en la Isfpp correspondiente", "Alerta: Worlflow", Messagebox.ERROR);
			return;
		}
		
		if (isfpp.getTutorExterno() == null) {
			Clients.alert("No se puede completar la tarea. No existe un \"Tutor Externo\" "
					+ "registrado en la Isfpp correspondiente.", "Alerta: Workflow", Messagebox.ERROR);
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
	
	@Command("registrarPracticante")
	@NotifyChange("*")
	public void registrarPracticantes(){
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("isfpp", isfpp);
		args.put("origen", this);
		UtilGuiGisfpp.mostrarDialogoBox("vistas/proyecto/dlgAsignarPracticantes.zul", args);
	}
	
	@Command("registrarTutor")
	@NotifyChange("*")
	public void registrarTutorExterno(){
		if (isfpp.getTutorExterno() != null) {
			Clients.alert("Ya existe un \"Tutor Externo\" registrado en la Isfpp."
					, "Alerta: Workflow", Messagebox.ERROR);
			return;
		}
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("isfpp", isfpp);
		args.put("origen", this);
		UtilGuiGisfpp.mostrarDialogoBox("vistas/proyecto/dlgAsignarTutorExterno.zul", args);
	}
	
	private void cerrar(){
		Window dlg = (Window) Path.getComponent("/dlgConfeccionarAcuerdoConvenios");
		dlg.detach();
	}
	
	private List<String> getListaPracticantes(){
		List<String> resultado = new ArrayList<String>();
		for (PersonaFisica persona : isfpp.getPracticantes()) {
			resultado.add(persona.getNombre() +" (DNI: "+persona.getDni()+")");
		}
		return resultado;
	}
	
}
