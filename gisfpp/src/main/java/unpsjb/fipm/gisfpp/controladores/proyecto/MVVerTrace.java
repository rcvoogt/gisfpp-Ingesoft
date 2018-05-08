package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.entidades.workflow.BusinessKey;
import unpsjb.fipm.gisfpp.entidades.workflow.DefinicionProceso;
import unpsjb.fipm.gisfpp.entidades.workflow.InstanciaActividad;
import unpsjb.fipm.gisfpp.entidades.workflow.InstanciaProceso;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorWorkflow;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

/**
 * 
 * @author isaias
 * 
 *
 */
public class MVVerTrace {

	private Isfpp item;
	private IServiciosIsfpp servicio;
	private SubProyecto perteneceA;
	private String modo;
	private boolean creando;
	private boolean editando;
	private boolean ver;
	private String titulo = " ";
	private String workflow = " ";
	private List<InstanciaProceso> tareas;
	private List<InstanciaActividad> actividades;


	private GestorWorkflow servGTareas;
	private HashMap<String, Object> args;

	@SuppressWarnings("unchecked")
	@Init
	@NotifyChange({ "modo", "item", "creando", "editando", "ver", "titulo", "tareas" })
	public void init() throws Exception {
		ver = true;
		modo = UtilGisfpp.MOD_VER;
		servicio = (IServiciosIsfpp) SpringUtil.getBean("servIsfpp");
		args = (HashMap<String, Object>) Executions.getCurrent().getAttribute("argsCrudIsfpp");
		servGTareas = (GestorWorkflow) SpringUtil.getBean("servGestionWorkflow");
		item = servicio.getInstancia((Integer) args.get("idItem"));
		item.setPerteneceA(perteneceA);
		creando = (editando = false);
		setTitulo("Trace de ISFPP: " + item.getTitulo());
		setWorkflow("Workflow Actual: " + "Solicitud Nueva ISFPP");
		actividades = servGTareas.getInstanciasActividades(servGTareas.getProcessDefinition(BusinessKey.solicitudNuevaIsfpp.getKeyBusiness()), item.getTitulo());
		
		
	}
	

	public Isfpp getItem() {
		return item;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getModo() {
		return modo;
	}
	
	public List<InstanciaProceso> getTareas() {
		return tareas;
	}
	
	
	public List<InstanciaActividad> getActividades() {
		return actividades;
	}

	public boolean isCreando() {
		return creando;
	}

	public boolean isEditando() {
		return editando;
	}

	public boolean isVer() {
		return ver;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getWorkflow() {
		return workflow;
	}


	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}
	
	@Command("dlgSelectorWorkflow")
	public void verDlgSelectorWorkflow() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("listSinFiltro", actividades);
		Window dlg = (Window) Executions.createComponents("vistas/proyecto/dlgSelectorWorkflow.zul", null, map);
		dlg.doModal();
	}

}