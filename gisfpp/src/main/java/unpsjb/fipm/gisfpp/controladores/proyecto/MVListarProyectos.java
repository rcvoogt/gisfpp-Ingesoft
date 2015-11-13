package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Include;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.ServiciosProyecto;

public class MVListarProyectos {

	private List<Proyecto> listaProyectos;
	private ServiciosProyecto servicio;
	private boolean filtrado = false;

	@Init
	public void init() throws Exception {
		servicio = (ServiciosProyecto) SpringUtil.getBean("servProyecto");
		recuperarTodo();
	}

	@Command("recuperarTodo")
	@NotifyChange({ "listaProyectos" })
	public void recuperarTodo() throws Exception {
		try {
			listaProyectos = servicio.obtenerTodosProyectos();
		} catch (Exception e) {
			System.err.println("Exception : " + e.toString());
			System.err.println("Exception original: " + e.getCause().toString());
			System.err.println("Causa: " + e.getMessage());
			throw e;
		}
	}

	public List<Proyecto> getListaProyectos() {
		return listaProyectos;
	}

	@Command("salir")
	public void salir() {
		Panel panel = (Panel) Path.getComponent("/panelCentro/panelListarProyectos");
		Include include = (Include) Path.getComponent("/panelCentro");
		if (panel != null) {
			panel.onClose();
			include.setSrc(null);
		}
	}

	@Command("nuevoProyecto")
	public void nuevoProyecto() {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("itemSeleccionado", null);
		map.put("modo", "nuevo");
		Sessions.getCurrent().setAttribute("modosProyecto", map);

		Panel panel = (Panel) Path.getComponent("/panelCentro/panelListarProyectos");
		Include include = (Include) Path.getComponent("/panelCentro");
		if (panel != null) {
			panel.onClose();
			include.setSrc(null);
			include.setSrc("vistas/proyecto/crudProyecto.zul");
		}

	}

	@Command("editarProyecto")
	public void editarProyecto(@BindingParam("item") Proyecto item) {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("itemSeleccionado", item);
		map.put("modo", "edicion");
		Sessions.getCurrent().setAttribute("modosProyecto", map);

		Panel panel = (Panel) Path.getComponent("/panelCentro/panelListarProyectos");
		Include include = (Include) Path.getComponent("/panelCentro");
		if (panel != null) {
			panel.onClose();
			include.setSrc(null);
			include.setSrc("vistas/proyecto/crudProyecto.zul");
		}
	}

	@Command("verProyecto")
	public void verProyecto(@BindingParam("item") Proyecto item) {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("itemSeleccionado", item);
		map.put("modo", "ver");
		Sessions.getCurrent().setAttribute("modosProyecto", map);

		Panel panel = (Panel) Path.getComponent("/panelCentro/panelListarProyectos");
		Include include = (Include) Path.getComponent("/panelCentro");
		if (panel != null) {
			panel.onClose();
			include.setSrc(null);
			include.setSrc("vistas/proyecto/crudProyecto.zul");
		}
	}

	@Command("busqueda")
	@NotifyChange({ "listaProyectos", "filtrado" })
	public void busquedaProyectos(@BindingParam("campo") String campoBusqueda,
			@BindingParam("patron") String patronBusqueda) {
		try {
			listaProyectos = servicio.filtrarProyectos(campoBusqueda, patronBusqueda);
			filtrado = true;
		} catch (Exception e) {
			System.err.println("Exception original: " + e.getCause().toString());
			System.err.println("Causa: " + e.getMessage());
		}
	}

	@Command("deshacer")
	@NotifyChange({ "filtrado", "listaProyectos" })
	public void deshacerBusqueda() throws Exception {
		recuperarTodo();
		filtrado = false;
	}

	@Command("dlgFiltros")
	public void mostrarDlgFiltros() {
		Window dlg = (Window) Executions.createComponents("/vistas/proyecto/dlgFiltros.zul", null, null);
		dlg.doModal();
	}

	public boolean isFiltrado() {
		return filtrado;
	}

}// Fin de la clase MVListarProyectos
