package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Messagebox;

import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.staff.ECargosStaffFi;
import unpsjb.fipm.gisfpp.servicios.proyecto.ServiciosProyecto;
import unpsjb.fipm.gisfpp.util.GisfppException;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;

public class MVListarProyectos {

	private List<Proyecto> listaProyectos;
	private ServiciosProyecto servicio;
	private Logger log = UtilGisfpp.getLogger();
	private boolean autorizadoNuevo = false;
	private boolean autorizadoEliminar = false;

	@Init
	public void init() throws Exception {
		try {
			servicio = (ServiciosProyecto) SpringUtil.getBean("servProyecto");
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
		checkAutorizadoNuevo();
	}

	@Command("recuperarTodo")
	@NotifyChange({ "listaProyectos" })
	public void recuperarTodo() throws Exception {
		try {
			listaProyectos = servicio.getListado();
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
		map.put("idItem", item.getId());
		map.put("modo", UtilGisfpp.MOD_EDICION);
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/panelListarProyectos", "vistas/proyecto/crudProyecto.zul", map);

	}

	@Command("verProyecto")
	public void verProyecto(@BindingParam("item") Proyecto item) {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("idItem", item.getId());
		map.put("modo", UtilGisfpp.MOD_VER);
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/panelListarProyectos", "vistas/proyecto/crudProyecto.zul", map);
	}

	@Command("eliminarProyecto")
	@NotifyChange("listaProyectos")
	public void eliminarProyecto(@BindingParam("item") Proyecto item) throws Exception {
		if (isAutorizadoEliminar()) {
			Messagebox.show("Desea realmente eliminar este Proyecto?", "Gisfpp: Eliminando Proyecto",
					Messagebox.YES + Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {

						public void onEvent(Event event) throws Exception {
							if (event.getName().equals(Messagebox.ON_YES)) {
								try {
									servicio.eliminar(item);
									Clients.showNotification("Proyecto eliminado.", Clients.NOTIFICATION_TYPE_INFO,
											null, "top_right", 3500);
									listaProyectos.remove(item);
								} catch (GisfppException exc) {
									Messagebox.show(exc.getMessage(), "Gisfpp: Eliminando Proyecto", Messagebox.OK,
											Messagebox.ERROR);
								} catch (Exception e) {
									log.error(this.getClass().getName(), e);
									throw e;
								}
							}
						}
					});
		} else {
			Messagebox.show("No posee los permisos suficientes para eliminar este Proyecto.",
					"Gisfpp: Eliminando Proyecto", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@NotifyChange("autorizadoNuevo")
	private void checkAutorizadoNuevo() {
		if (UtilGisfpp.rolStaffFi(ECargosStaffFi.COORDINADOR.toString())
				|| UtilGisfpp.rolStaffFi(ECargosStaffFi.DELEGADO.toString())
				|| UtilGisfpp.rolStaffFi(ECargosStaffFi.PROFESOR.toString())) {
			autorizadoNuevo = true;
		} else {
			autorizadoNuevo = false;
		}
	}

	public boolean isAutorizadoNuevo() {
		return autorizadoNuevo;
	}

	private boolean isAutorizadoEliminar() {
		if (UtilGisfpp.rolStaffFi(ECargosStaffFi.COORDINADOR.toString())
				|| UtilGisfpp.rolStaffFi(ECargosStaffFi.DELEGADO.toString())
				|| UtilGisfpp.rolStaffFi(ECargosStaffFi.PROFESOR.toString())) {
			return true;
		} else {
			return false;
		}
	}

}// Fin de la clase MVListarProyectos
