package unpsjb.fipm.gisfpp.controladores.isfpp;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zkplus.spring.SpringUtil;

import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
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
	private MVListarIsfpp autoRef;//autoreferencia utilizada en metodo eliminarIsfpp

	@Init
	@NotifyChange({"titulo","listaIsfpps"})
	public void init() throws Exception {
		try {
			servicio = (ServiciosIsfpp) SpringUtil.getBean("servIsfpp");
			recuperarTodo();
			titulo = "Listado de Isfpps";
			autoRef= this;
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
			System.out.println("PASE POR ACA");
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

	/*@Command("nuevoProyecto")
	public void nuevoProyecto() {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("idItem", 0);
		map.put("modo", UtilGisfpp.MOD_NUEVO);
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlListaProyectos", "vistas/proyecto/crudProyecto.zul", map);
	}
	
	@Command("editarProyecto")
	public void editarProyecto(@BindingParam("item") Proyecto item) {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("idItem", item.getId());
		map.put("modo", UtilGisfpp.MOD_EDICION);
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlListaProyectos", "vistas/proyecto/crudProyecto.zul", map);

	}*/

	@Command("verIsfpp")
	@NotifyChange("tabIsfppCreado")
	public void verIsfpp(@BindingParam("idItem") Integer id) {
		crearTab(UtilGisfpp.MOD_VER, "Ver Isfpp", id);
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlListaIsfpps", "vistas/isfpp/crudIsfpp.zul", map);
		//tabIsfppCreado = true;
	}
	
	
	/*@Command("verIsfpp")
	public void verIsfpp(@BindingParam("item") Isfpp item) {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("item", item.getId());
		map.put("modo", UtilGisfpp.MOD_VER);
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlListaIsfpps", "vistas/isfpp/crudIsfpp.zul", map);
	}*/

	/*@Command("eliminarProyecto")
	public void eliminarProyecto(@BindingParam("item") Proyecto item) throws Exception {
		Messagebox.show("Desea realmente eliminar este Proyecto?", "Gisfpp: Eliminando Proyecto",
					Messagebox.YES + Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {

						public void onEvent(Event event) throws Exception {
							if (event.getName().equals(Messagebox.ON_YES)) {
								try {
									servicio.eliminar(item);
									Clients.showNotification("Proyecto eliminado.", Clients.NOTIFICATION_TYPE_INFO,
											null, "top_right", 3500);
									listaIsfpps.remove(item);
									BindUtils.postNotifyChange(null, null, autoRef, "listaIsfpps");
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
		}


	//Inicio Bloque "Aplicacion Filtro de Listado"
	@Command("dlgFiltro")
	public void verDlgFiltro() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("listSinFiltro", listaIsfpps);
		Window dlg = (Window) Executions.createComponents("vistas/proyecto/dlgFiltrosProyecto.zul", null, map);
		dlg.doModal();
	}

	@GlobalCommand("retornoDlgFiltroProyecto")
	@NotifyChange({ "listaIsfpps", "filtrado", "titulo" })
	public void retornoDlgFiltroProyecto(@BindingParam("listConFiltro") List<Proyecto> arg1) {
		// si el listado no tiene un filtro aplicado, mantenemos una referencia
		// a dicho listado original, para luego recuperarlo
		if (!listadoFiltrado) {
			temp = listaIsfpps;
		}
		listaIsfpps = arg1;
		listadoFiltrado = true;
		titulo = "Listado Filtrado";
	}

	@Command("quitarFiltro")
	@NotifyChange({ "listaIsfpps", "filtrado", "titulo" })
	public void quitarFiltro() {
		listaIsfpps = temp;
		listadoFiltrado = false;
		titulo = "Listado de Proyectos";
		// Limpiamos los argumentos de listadoFiltrado de la sesion.
		Sessions.getCurrent().setAttribute("argUltFiltroProyectos", null);
	}
	public boolean isFiltrado() {
		return listadoFiltrado;
	}*/

	public String getTitulo() {
		return titulo;
	}

}// Fin de la clase MVListarProyectos
