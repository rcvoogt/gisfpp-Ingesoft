package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.util.HashMap;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
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
			editando = false;
			ver = false;
			titulo = "Nuevo Sub-Proyecto / Proyecto: (" + item.getPerteneceA().getCodigo() + ") "
					+ item.getPerteneceA().getTitulo();
			break;
		}
		case UtilGisfpp.MOD_EDICION: {
			Integer id = (Integer) map.get("idItem");
			item = servicio.getInstancia(id);
			item.setPerteneceA(perteneceA);
			creando = false;
			editando = true;
			ver = false;
			titulo = "Editando Sub-Proyecto: " + item.getTitulo() + " / Proyecto: (" + perteneceA.getCodigo() + ") "
					+ perteneceA.getTitulo();
			break;
		}
		case UtilGisfpp.MOD_VER: {
			Integer id = (Integer) map.get("idItem");
			item = servicio.getInstancia(id);
			item.setPerteneceA(perteneceA);
			creando = false;
			editando = false;
			ver = true;
			titulo = "Ver Sub-Proyecto: " + item.getTitulo() + " / Proyecto: (" + perteneceA.getCodigo() + ") "
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
		editando = false;
		ver = false;
	}

	@Command("editar")
	@NotifyChange({ "editando", "creando", "ver" })
	public void reEditar() {
		editando = true;
		creando = false;
		ver = false;
	}

	@Command("guardar")
	@NotifyChange({ "creando", "editando", "ver" })
	public void guardar() throws Exception {
		try {
			if (creando) {
				int id = servicio.persistir(item);
				Clients.showNotification("Nuevo Sub-Proyecto registrado. Id: ( " + id + ")",
						Clients.NOTIFICATION_TYPE_INFO, null, "top_right", 3500);
				creando = false;
				editando = false;
				ver = true;
			} else if (editando) {
				servicio.editar(item);
				Clients.showNotification("Se guardaron los cambios efectuados.", Clients.NOTIFICATION_TYPE_INFO, null,
						"top_right", 3500);
				creando = false;
				editando = false;
				ver = true;
			}
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
		creando = false;
		editando = false;
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

	public List<Isfpp> getInstanciasIsfpp() {
		return item.getInstanciasIsfpp();
	}

	@Command("nuevaIsfpp")
	public void nuevaIsfpp() {
		crearTab(UtilGisfpp.MOD_NUEVO, "Nueva Isfpp", null);
	}

	@Command("editarIsfpp")
	public void editarIsfpp(@BindingParam("idItem") Integer id) {
		crearTab(UtilGisfpp.MOD_EDICION, "Editar Isfpp", id);
	}

	@Command("verIsfpp")
	public void verIsfpp(@BindingParam("idItem") Integer id) {
		crearTab(UtilGisfpp.MOD_VER, "Ver Isfpp", id);
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

}// fin de la clase
