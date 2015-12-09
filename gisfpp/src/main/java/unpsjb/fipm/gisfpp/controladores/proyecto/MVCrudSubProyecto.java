package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.util.HashMap;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServicioSubProyecto;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;

public class MVCrudSubProyecto {

	private IServicioSubProyecto servicio;
	private SubProyecto item;
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
	public void init() {
		log = UtilGisfpp.getLogger();
		servicio = (IServicioSubProyecto) SpringUtil.getBean("servSubProyecto");
		map = (HashMap<String, Object>) Sessions.getCurrent().getAttribute(UtilGuiGisfpp.PRM_PNL_CENTRAL);
		modo = (String) map.get("modo");
		item = (SubProyecto) map.get("item");
		switch (modo) {
		case UtilGisfpp.MOD_NUEVO: {
			creando = true;
			editando = false;
			ver = false;
			titulo = "Nuevo SubProyecto /Proyecto: (" + item.getProyecto().getCodigo() + ") "
					+ item.getProyecto().getTitulo();
			break;
		}
		case UtilGisfpp.MOD_EDICION: {
			creando = false;
			editando = true;
			ver = false;
			titulo = "Editando SubProyecto: " + item.getTitulo() + " /Proyecto: (" + item.getProyecto().getCodigo()
					+ ") " + item.getProyecto().getTitulo();
		}
		case UtilGisfpp.MOD_VER: {
			creando = false;
			editando = false;
			ver = true;
			titulo = "Ver SubProyecto: " + item.getTitulo() + " /Proyecto: (" + item.getProyecto().getCodigo() + ") "
					+ item.getProyecto().getTitulo();
		}
		default:
			break;
		}

	}

	@Command("nuevoSP")
	@NotifyChange({ "item", "creando", "editando", "ver" })
	public void nuevo() {
		Proyecto proyecto = item.getProyecto();
		item = new SubProyecto(proyecto, null, null, null, null, null);
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
				Clients.showNotification("Cambios efectuados guardados.", Clients.NOTIFICATION_TYPE_INFO, null,
						"top_right", 3500);
				creando = false;
				editando = false;
				ver = true;
			}
		} catch (ConstraintViolationException cve) {
			Messagebox.show(UtilGisfpp.getMensajeValidations(cve), "Error: Validación de datos.", Messagebox.OK,
					Messagebox.ERROR);
		} catch (DataIntegrityViolationException | org.hibernate.exception.ConstraintViolationException dive) {
			Messagebox.show(dive.getMessage(), "Error: Violacion Integridad de Datos en BD.", Messagebox.OK,
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

	@Command("volver")
	public void volver() {
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlCrudSP", (String) map.get("volverA"));
	}

}// fin de la clase
