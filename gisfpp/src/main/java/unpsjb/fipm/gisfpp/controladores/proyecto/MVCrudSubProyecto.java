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
	private boolean toolbar_disabled=false;
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
	
	public boolean isToolbar_disabled() {
		return toolbar_disabled;
	}

	public List<Isfpp> getInstanciasIsfpp() {
		return item.getInstanciasIsfpp();
	}

	@Command("nuevaIsfpp")
	@NotifyChange("toolbar_disabled")
	public void nuevaIsfpp() {
		crearTab(UtilGisfpp.MOD_NUEVO, "Nueva Isfpp", null);
		toolbar_disabled=true;
	}

	@Command("editarIsfpp")
	@NotifyChange("toolbar_disabled")
	public void editarIsfpp(@BindingParam("idItem") Integer id) {
		crearTab(UtilGisfpp.MOD_EDICION, "Editar Isfpp", id);
		toolbar_disabled = true;
	}

	@Command("verIsfpp")
	@NotifyChange("toolbar_disabled")
	public void verIsfpp(@BindingParam("idItem") Integer id) {
		crearTab(UtilGisfpp.MOD_VER, "Ver Isfpp", id);
		toolbar_disabled = true;
	}
	
	@GlobalCommand("cerrandoTab")
	@NotifyChange({"toolbar_disabled","item","instanciasIsfpp"})
	public void cerrandoTab(@BindingParam ("actualizar") boolean actualizar) throws Exception{
		toolbar_disabled = false;
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
