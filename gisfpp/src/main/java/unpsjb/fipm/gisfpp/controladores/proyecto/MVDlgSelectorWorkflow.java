package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.activiti.engine.repository.ProcessDefinition;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.proyecto.EEstadosIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.entidades.workflow.InstanciaActividad;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServicioSubProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosProyecto;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorWorkflow;

public class MVDlgSelectorWorkflow {

	private List<InstanciaActividad> listSinFiltro;
	private String proceso;
	private ProcessDefinition definicionProceso;
	private List<ProcessDefinition> definicionProcesos;
	private SubProyecto subProyecto;
	private EEstadosIsfpp estado;
	private GestorWorkflow srvGestorWorkflow;
	private IServicioSubProyecto srvSubProyecto;
	private IServiciosProyecto srvProyecto;
	private Isfpp isfpp;
	private List<String> procesos;

	@Init
	public void init() {
		HashMap<String, Object> map = (HashMap<String, Object>) Executions.getCurrent().getArg();
		listSinFiltro = (List<InstanciaActividad>) map.get("listSinFiltro");
		isfpp = (Isfpp) map.get("isfpp");
		srvGestorWorkflow = (GestorWorkflow) SpringUtil.getBean("servGestionWorkflow");
		srvSubProyecto = (IServicioSubProyecto) SpringUtil.getBean("servSubProyecto");
		srvProyecto = (IServiciosProyecto) SpringUtil.getBean("servProyecto");
		definicionProcesos = srvGestorWorkflow.getDefinicionProcesos();
		// HardCode para prueba de pantalla
//		procesos = new ArrayList<String>();
//		procesos.add("Solicitud de ISFPP");
//		procesos.add("Convocatoria");
		recuperarArgUltFiltro();

	}
	@Command("seleccionar")
	public void seleccionar() {
		List<InstanciaActividad> resultado = srvGestorWorkflow.getInstanciasActividades(definicionProceso, isfpp.getTitulo());
		HashMap<String, Object> prm = new HashMap<>();
		prm.put("listConFiltro", resultado);
		prm.put("definicionProceso",definicionProceso);
		BindUtils.postGlobalCommand(null, null, "retornoDlgSelectorWorkflow", prm);
		cerrar();
	}
	@Command("filtrar")
	public void filtrar() {
		List<InstanciaActividad> resultado = listSinFiltro.stream().filter(getPredicadoFiltro())
				.collect(Collectors.toList());
		HashMap<String, Object> prm = new HashMap<>();
		prm.put("listConFiltro", resultado);
		BindUtils.postGlobalCommand(null, null, "retornoDlgSelectorWorkflow", prm);
		guardarArgUltFiltro();
		cerrar();
	}

	private Predicate<InstanciaActividad> getPredicadoFiltro() {
		Predicate<InstanciaActividad> filtro = Item -> true;
		/*
		 * if (codigo != null && !codigo.isEmpty()) { filtro = filtro.or(item ->
		 * item.getCodigo().toLowerCase().contains(codigo.toLowerCase())); } if
		 * (resolucion != null && !resolucion.isEmpty()) { filtro = filtro.or(item ->
		 * item.getResolucion().toLowerCase().contains(resolucion.toLowerCase())); }
		 */
		if (definicionProceso != null) {
			// filtro = filtro.and(item ->
			// item.getPerteneceA().getPerteneceA().equals(proceso));
		}
		return filtro;
	}

	private void cerrar() {
		Window thisDlg = (Window) Path.getComponent("/dlgSelectorWorkflow");
		thisDlg.detach();
	}

	/**
	 * Mediante este método se guarda en la session web los criterios (argumentos
	 * )de busqueda establecidos en el último filtro aplicado al listado de
	 * Proyectos.
	 */
	private void guardarArgUltFiltro() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("proceso", proceso);

		Sessions.getCurrent().setAttribute("argUltFiltroIsfpps", map);
	}

	/**
	 * Recuperamos de la session web, los criterios (argumentos) si los hubiera, del
	 * último filtro aplicado al listado de Proyectos.
	 */
	@NotifyChange({ "subProyecto", "estado" })
	private void recuperarArgUltFiltro() {
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions.getCurrent()
				.getAttribute("argUltFiltroProyectos");
		if (map != null) {
			proceso = (String) map.get("proceso");
		}
	}

	public List<String> getProcesos() {

		return procesos;
	}

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public ProcessDefinition getDefinicionProceso() {
		return definicionProceso;
	}

	public List<ProcessDefinition> getDefinicionProcesos() {
		return definicionProcesos;
	}
	public void setDefinicionProceso(ProcessDefinition definicionProceso) {
		this.definicionProceso = definicionProceso;
	}
	public void setDefinicionProcesos(List<ProcessDefinition> definicionProcesos) {
		this.definicionProcesos = definicionProcesos;
	}
	
	
	
	

}// fin de la clase
