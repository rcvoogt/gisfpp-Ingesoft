package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zkplus.spring.SpringUtil;

import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.ServiciosProyecto;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;

public class MVListarProyectos {

	private List<Proyecto> listaProyectos;
	private ServiciosProyecto servicio;
	private Logger log = UtilGisfpp.getLogger();

	@Init
	public void init() throws Exception {
		try {
			servicio = (ServiciosProyecto) SpringUtil.getBean("servProyecto");
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Command("recuperarTodo")
	@NotifyChange({ "listaProyectos" })
	public void recuperarTodo() throws Exception {
		try {
			listaProyectos = servicio.obtenerTodosProyectos();
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	public List<Proyecto> getListaProyectos() throws Exception {
		recuperarTodo();
		return listaProyectos;
	}

	@Command("salir")
	public void salir() {
		UtilGuiGisfpp.quitarPnlCentral("/panelCentro/panelListarProyectos");
	}

	@Command("nuevoProyecto")
	public void nuevoProyecto() {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("item", null);
		map.put("modo", UtilGisfpp.MOD_NUEVO);
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/panelListarProyectos", "vistas/proyecto/crudProyecto.zul", map);
	}

	@Command("editarProyecto")
	public void editarProyecto(@BindingParam("item") Proyecto item) {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("item", item);
		map.put("modo", UtilGisfpp.MOD_EDICION);
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/panelListarProyectos", "vistas/proyecto/crudProyecto.zul", map);

	}

	@Command("verProyecto")
	public void verProyecto(@BindingParam("item") Proyecto item) {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("item", item);
		map.put("modo", UtilGisfpp.MOD_VER);
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/panelListarProyectos", "vistas/proyecto/crudProyecto.zul", map);
	}

}// Fin de la clase MVListarProyectos
