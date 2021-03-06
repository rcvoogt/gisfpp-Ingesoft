package unpsjb.fipm.gisfpp.controladores.isfpp;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.proyecto.EEstadosIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.ServiciosIsfpp;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;

public class MVListarIsfpp {

	private List<Isfpp> listaIsfpps;
	private List<Isfpp> temp;
	private boolean listadoFiltrado = false;
	private ServiciosIsfpp servicio;
	private Logger log = UtilGisfpp.getLogger();
	private String titulo;
	private String filtro;
	private MVListarIsfpp autoRef;// autoreferencia utilizada en metodo
									// eliminarIsfpp
	private Proyecto proyecto;
	private SubProyecto subProyecto;
	private EEstadosIsfpp estado;

	@Init
	@NotifyChange({ "titulo", "listaIsfpps", "filtro" })
	public void init() throws Exception {
		try {
			servicio = (ServiciosIsfpp) SpringUtil.getBean("servIsfpp");
			recuperarTodo();
			titulo = "Listado de Isfpps";
			autoRef = this;
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Command("recuperarTodo")
	@NotifyChange({ "listaIsfpps" })
	public void recuperarTodo() throws Exception {
		try {
			listaIsfpps = servicio.getListado();
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	public List<Isfpp> getListaIsfpps() throws Exception {
		return listaIsfpps;
	}

	@Command("salir")
	public void salir() {
		UtilGuiGisfpp.quitarPnlCentral("/panelCentro/pnlListaIsfpps");
	}

	@Command("verIsfpp")
	public void verIsfpp(@BindingParam("item") Isfpp item) {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("idItem", item.getId());
		map.put("modo", UtilGisfpp.MOD_VER);
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlListaIsfpps", "vistas/isfpp/verIsfpp.zul", map);
	}

	// Inicio Bloque "Aplicacion Filtro de Listado"
	@Command("dlgFiltro")
	public void verDlgFiltro() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("listSinFiltro", listaIsfpps);
		Window dlg = (Window) Executions.createComponents("vistas/isfpp/dlgFiltrosIsfpp.zul", null, map);
		dlg.doModal();
	}

	@GlobalCommand("retornoDlgFiltroIsfpp")
	@NotifyChange({ "listaIsfpps", "filtrado", "titulo", "filtro" })
	public void retornoDlgFiltroIsfpp(@BindingParam("listConFiltro") List<Isfpp> arg1) {
		// si el listado no tiene un filtro aplicado, mantenemos una referencia
		// a dicho listado original, para luego recuperarlo
		if (!listadoFiltrado) {
			temp = listaIsfpps;
		}
		listaIsfpps = arg1;
		listadoFiltrado = true;
		titulo = "Listado Filtrado";
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions.getCurrent()
				.getAttribute("argUltFiltroIsfpps");
		if (map != null) {
			proyecto = (Proyecto) map.get("proyecto");
			subProyecto = (SubProyecto) map.get("subProyecto");
			estado = (EEstadosIsfpp) map.get("estado");
			filtro = getFiltro();
		}
		
		
	}

	@Command("quitarFiltro")
	@NotifyChange({ "listaIsfpps", "filtrado", "titulo", "filtro" })
	public void quitarFiltro() {
		listaIsfpps = temp;
		listadoFiltrado = false;
		titulo = "Listado de Isfpps";
		// Limpiamos los argumentos de listadoFiltrado de la sesion.
		Sessions.getCurrent().setAttribute("argUltFiltroIsfpps", null);
		filtro = "";
	}

	public boolean isFiltrado() {
		return listadoFiltrado;
	}

	public String getTitulo() {
		return titulo;
	}


	public String getFiltro() {
		String filtros= "";
		if (proyecto != null)
			filtros+= "Proyecto: "+proyecto.getTitulo()+"\n";
		if (subProyecto != null)
			filtros+="SubProyecto: "+subProyecto.getTitulo()+"\n";
		if (estado != null)
			filtros+="Estado: "+estado.getTitulo()+"\n";

		return filtros;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

}
