package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.entidades.workflow.InstanciaActividad;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServicioSubProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosProyecto;

public class MVDlgSelectorWorkflow {

	private List<InstanciaActividad> listSinFiltro;
	private String proceso;
	private SubProyecto subProyecto;
	private EEstadosIsfpp estado;
	private IServicioSubProyecto srvSubProyecto;
	private IServiciosProyecto srvProyecto;
	private List<String> procesos;

	@Init
	public void init() {
		HashMap<String, Object> map = (HashMap<String, Object>) Executions.getCurrent().getArg();
		listSinFiltro = (List<InstanciaActividad>) map.get("listSinFiltro");
		srvSubProyecto = (IServicioSubProyecto) SpringUtil.getBean("servSubProyecto");
		srvProyecto = (IServiciosProyecto) SpringUtil.getBean("servProyecto");
		// HardCode para prueba de pantalla
		procesos = new ArrayList<String>();
		procesos.add("Solicitud de ISFPP");
		procesos.add("Convocatoria");
		recuperarArgUltFiltro();

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
		if (proceso != null) {
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

}// fin de la clase
