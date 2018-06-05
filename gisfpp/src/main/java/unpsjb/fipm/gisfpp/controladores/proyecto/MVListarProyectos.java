package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioUsuario;
import unpsjb.fipm.gisfpp.servicios.persona.ServicioUsuario;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.ServiciosProyecto;
import unpsjb.fipm.gisfpp.util.GisfppException;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;
import unpsjb.fipm.gisfpp.util.security.UtilSecurity;

public class MVListarProyectos {

	private List<Proyecto> listaProyectos;
	private List<Proyecto> temp;
	private boolean listadoFiltrado = false;
	private IServiciosProyecto servicio;
	private IServicioUsuario servUsuario;
	private Logger log = UtilGisfpp.getLogger();
	private String titulo;
	private MVListarProyectos autoRef;// autoreferencia utilizada en metodo eliminarProyecto

	@Init
	@NotifyChange({ "titulo", "listaProyectos" })
	public void init() throws Exception {
		try {
			servicio = (IServiciosProyecto) SpringUtil.getBean("servProyecto");
			servUsuario = (IServicioUsuario) SpringUtil.getBean("servUsuario");
			recuperarTodo();
			titulo = "Listado de Proyectos";
			autoRef = this;
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
		System.out.println("Al iniciar proyecto! isAutorizado2:"+ UtilSecurity.isAutorizado2("MODIFICAR_PROYECTO", "proyecto", 1)+" isAutorizado:"+ 
				UtilSecurity.isAutorizado("MODIFICAR_PROYECTOS"));
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
		return listaProyectos;
	}

	@Command("salir")
	public void salir() {
		UtilGuiGisfpp.quitarPnlCentral("/panelCentro/pnlListaProyectos");
	}

	@Command("nuevoProyecto")
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

	}

	@Command("verProyecto")
	public void verProyecto(@BindingParam("item") Proyecto item) {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("idItem", item.getId());
		map.put("modo", UtilGisfpp.MOD_VER);
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlListaProyectos", "vistas/proyecto/crudProyecto.zul", map);
	}

	@Command("eliminarProyecto")
	public void eliminarProyecto(@BindingParam("item") Proyecto item) throws Exception {
		Messagebox.show("Desea realmente eliminar este Proyecto?", "Gisfpp: Eliminando Proyecto",
				Messagebox.YES + Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {

					public void onEvent(Event event) throws Exception {
						if (event.getName().equals(Messagebox.ON_YES)) {
							try {
								servicio.eliminar(item);
								Clients.showNotification("Proyecto eliminado.", Clients.NOTIFICATION_TYPE_INFO, null,
										"top_right", 3500);
								listaProyectos.remove(item);
								BindUtils.postNotifyChange(null, null, autoRef, "listaProyectos");
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

	// Inicio Bloque "Aplicacion Filtro de Listado"
	@Command("dlgFiltro")
	public void verDlgFiltro() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("listSinFiltro", listaProyectos);
		Window dlg = (Window) Executions.createComponents("vistas/proyecto/dlgFiltrosProyecto.zul", null, map);
		dlg.doModal();
	}

	@GlobalCommand("retornoDlgFiltroProyecto")
	@NotifyChange({ "listaProyectos", "filtrado", "titulo" })
	public void retornoDlgFiltroProyecto(@BindingParam("listConFiltro") List<Proyecto> arg1) {
		// si el listado no tiene un filtro aplicado, mantenemos una referencia
		// a dicho listado original, para luego recuperarlo
		if (!listadoFiltrado) {
			temp = listaProyectos;
		}
		listaProyectos = arg1;
		listadoFiltrado = true;
		titulo = "Listado Filtrado";
	}

	@Command("quitarFiltro")
	@NotifyChange({ "listaProyectos", "filtrado", "titulo" })
	public void quitarFiltro() {
		listaProyectos = temp;
		listadoFiltrado = false;
		titulo = "Listado de Proyectos";
		// Limpiamos los argumentos de listadoFiltrado de la sesion.
		Sessions.getCurrent().setAttribute("argUltFiltroProyectos", null);
	}

	public boolean isFiltrado() {
		return listadoFiltrado;
	}

	public String getTitulo() {
		return titulo;
	}

	public boolean isMember(@BindingParam("item") Proyecto item){
		System.out.println("Entro " + item.getId());
		PersonaFisica persona;
		try {
			persona = servUsuario.getUsuario(UtilSecurity.getNickName()).getPersona();
			//System.out.println("La persona: " + persona.getNombre());
			boolean value = servicio.isMiembroStaff(item.getId(), persona);
			//System.out.println("Es miembro: " + value);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean puedeEditarProyecto(@BindingParam("item") Proyecto item) {
		try {
			System.out.println("puede editar?"+"id: "+item.getId()+" is2:"+UtilSecurity.isAutorizado2("MODIFICAR_PROYECTO", "proyecto", item.getId())
						+		" is1:"+UtilSecurity.isAutorizado("MODIFICAR_PROYECTOS"));	
			if(UtilSecurity.isAutorizado2("MODIFICAR_PROYECTO", "proyecto", item.getId())|| 
					UtilSecurity.isAutorizado("MODIFICAR_PROYECTOS")) {
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}// Fin de la clase MVListarProyectos
