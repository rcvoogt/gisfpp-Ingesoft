package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.util.HashMap;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;

import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServicioSubProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.util.GisfppException;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;

public class MVCrudSubProyecto {

	private IServicioSubProyecto servicio;
	private SubProyecto item;
	private Proyecto perteneceA;
	private Logger log;
	private boolean creando;
	private boolean editando;
	private boolean ver;
	private boolean tabIsfppCreado=false;
	private String modo;
	private String titulo;
	private HashMap<String, Object> map;

	@SuppressWarnings("unchecked")
	@Init
	@NotifyChange({ "modo", "titulo", "creando", "editando", "ver" })
	public void init() throws Exception {
		log = UtilGisfpp.getLogger();
		servicio = (IServicioSubProyecto) SpringUtil.getBean("servSubProyecto");
		map = (HashMap<String, Object>) Sessions.getCurrent().getAttribute(UtilGuiGisfpp.PRM_PNL_CENTRAL);
		modo = (String) map.get("modo");
		perteneceA = (Proyecto) map.get("perteneceA");
		switch (modo) {
		case UtilGisfpp.MOD_NUEVO: {
			item = new SubProyecto(perteneceA, "", "");
			creando = true;
			editando = ver =false;
			titulo = "Nuevo Sub-Proyecto / Proyecto: (Cod.: " + item.getPerteneceA().getCodigo() + ") "
					+ item.getPerteneceA().getTitulo();
			break;
		}
		case UtilGisfpp.MOD_EDICION: {
			item = servicio.getInstancia((Integer) map.get("idItem"));
			item.setPerteneceA(perteneceA);
			creando = ver=false;
			editando = true;
			titulo = "Editando Sub-Proyecto: " + item.getTitulo() + " / Proyecto: (Cod.: " + perteneceA.getCodigo() + ") "
					+ perteneceA.getTitulo();
			break;
		}
		case UtilGisfpp.MOD_VER: {
			item = servicio.getInstancia((Integer) map.get("idItem"));
			item.setPerteneceA(perteneceA);
			creando = editando =false;
			ver = true;
			titulo = "Ver Sub-Proyecto: " + item.getTitulo() + " / Proyecto: (Cod.: " + perteneceA.getCodigo() + ") "
					+ perteneceA.getTitulo();
			break;
		}
		}
	}

	@Command("nuevoSP")
	@NotifyChange({ "item", "creando", "editando", "ver" })
	public void nuevo() {
		item = new SubProyecto(perteneceA, null, null);
		creando = true;
		editando = ver =false;
	}

	@Command("editar")
	@NotifyChange({ "editando", "creando", "ver" })
	public void reEditar() {
		editando = true;
		creando = ver=false;
	}

	@Command("guardar")
	@NotifyChange({ "creando", "editando", "ver" })
	public void guardar() throws Exception {
		try {
			if (creando) {
				servicio.persistir(item);
				Clients.showNotification("Nuevo Sub-Proyecto guardado.",
						Clients.NOTIFICATION_TYPE_INFO, null, "top_right", 3500);
			} else if (editando) {
				servicio.editar(item);
				Clients.showNotification("Sub-Proyecto actualizado.", Clients.NOTIFICATION_TYPE_INFO, null,
						"top_right", 3500);
			}
			creando=editando=false;
			ver = true;
		} catch (ConstraintViolationException cve) {
			Messagebox.show(UtilGisfpp.getMensajeValidations(cve), "Error: Validación de datos.", Messagebox.OK,
					Messagebox.ERROR);
		} catch (DataIntegrityViolationException | org.hibernate.exception.ConstraintViolationException dive) {
			Messagebox.show(dive.getMessage(), "Error: Violacion Restricciones de Integridad BD.", Messagebox.OK,
					Messagebox.ERROR);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Command("cancelar")
	@NotifyChange({ "creando", "editando", "ver" })
	public void cancelar() {
		creando = editando =false;
		ver = true;
	}

	public SubProyecto getItem() {
		return item;
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

	public String getModo() {
		return modo;
	}

	public String getTitulo() {
		return titulo;
	}
	
	public boolean isTabIsfppCreado() {
		return tabIsfppCreado;
	}

	
	@Command("nuevaIsfpp")
	@NotifyChange("tabIsfppCreado")
	public void nuevaIsfpp() {
		crearTab(UtilGisfpp.MOD_NUEVO, "Nueva Isfpp", null);
		tabIsfppCreado=true;
	}

	@Command("editarIsfpp")
	@NotifyChange("tabIsfppCreado")
	public void editarIsfpp(@BindingParam("idItem") Integer id) {
		crearTab(UtilGisfpp.MOD_EDICION, "Editar Isfpp", id);
		tabIsfppCreado = true;
	}

	@Command("verIsfpp")
	@NotifyChange("tabIsfppCreado")
	public void verIsfpp(@BindingParam("idItem") Integer id) {
		crearTab(UtilGisfpp.MOD_VER, "Ver Isfpp", id);
		tabIsfppCreado = true;
	}
	
	@Command("eliminarIsfpp")
	@NotifyChange({"item"})
	public void eliminarIsfpp(@BindingParam("item") Isfpp arg1) throws Exception{
		Messagebox.show("Desea realmente eliminar esta Isfpp?", "Gisfpp: Eliminando Isfpp", 
				Messagebox.YES+Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						if(event.getName().equals(Messagebox.ON_YES)){
							try {
								IServiciosIsfpp servicio = (IServiciosIsfpp) SpringUtil.getBean("servIsfpp");
								servicio.eliminar(arg1);
								Clients.showNotification("Isfpp eliminada.", Clients.NOTIFICATION_TYPE_INFO, null, 
										"top_right", 3500);
							}
							catch (GisfppException excpt){
								Messagebox.show(excpt.getMessage(), "Gisfpp: Eliminando Isfpp", 
										Messagebox.OK, Messagebox.ERROR);
							}
							catch (Exception e) {
								log.error(this.getClass().getName(), e);
								throw e;
							}
							
						}
					}
				});
	}
	
	@GlobalCommand("cerrandoTab")
	@NotifyChange({"tabIsfppCreado","item"})
	public void cerrandoTab(@BindingParam ("actualizar") boolean actualizar) throws Exception{
		tabIsfppCreado = false;
		if (actualizar){
			item = servicio.getInstancia(item.getId());
		}
	}

	private void crearTab(String modo, String titulo, Integer id) {
		Tabbox tabBox = (Tabbox) Path.getComponent("/panelCentro/tbbxSP");
		Tab tab = new Tab(titulo);
		Tabpanel tabPanel = new Tabpanel();
		Include include = new Include("vistas/proyecto/crudIsfpp.zul");
		HashMap<String, Object> args = new HashMap<>();
		args.put("perteneceA", item);
		args.put("modo", modo);
		args.put("idItem", id);
		args.put("tab", tab);
		include.setDynamicProperty("argsCrudIsfpp", args);
		tabBox.getTabs().appendChild(tab);
		tabPanel.getChildren().add(include);
		tabBox.getTabpanels().getChildren().add(tabPanel);
		tabBox.setSelectedTab(tab);
	}

	@Command("volver")
	public void volver() {
		HashMap<String, Object> mapAux = new HashMap<>();
		mapAux.put("idItem", perteneceA.getId());
		mapAux.put("modo", UtilGisfpp.MOD_VER);
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlCrudSP", (String) map.get("volverA"), mapAux);
	}
	
	@Command("salir")
	public void salir (){
		UtilGuiGisfpp.quitarPnlCentral("/panelCentro/pnlCrudSP");
	}

}// fin de la clase
