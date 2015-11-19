package unpsjb.fipm.gisfpp.controladores.persona;

import java.util.HashMap;

import javax.validation.ConstraintViolationException;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPersona;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class MVCrudOrganizacion {

	private IServicioPersona servicio;
	private PersonaJuridica item;
	private boolean creando = false;
	private boolean editando = false;
	private boolean ver = false;
	private String modo;
	private String titulo;

	@Init
	@NotifyChange({ "modo", "item", "creando", "editando", "ver", "titulo" })
	public void init() {
		@SuppressWarnings("unchecked")
		final HashMap<String, Object> opciones = (HashMap<String, Object>) Sessions.getCurrent()
				.getAttribute("paramCrudOrganizacion");
		modo = (String) opciones.get("modo");
		servicio = (IServicioPersona) SpringUtil.getBean("serPersona");
		switch (modo) {
		case UtilGisfpp.MOD_NUEVO: {
			item = new PersonaJuridica();
			creando = true;
			editando = false;
			ver = false;
			titulo = "Nueva Organizacion";
			break;
		}
		case UtilGisfpp.MOD_EDICION: {
			item = (PersonaJuridica) opciones.get("item");
			creando = false;
			editando = true;
			ver = false;
			titulo = "Editando Organización: " + item.getNombre();
			break;
		}
		case UtilGisfpp.MOD_VER: {
			item = (PersonaJuridica) opciones.get("item");
			creando = false;
			editando = false;
			ver = true;
			titulo = "Ver detalle de Organizacion: " + item.getNombre();
			break;
		}
		default:
			break;
		}
	}

	@Command("nuevaOrganizacion")
	@NotifyChange({ "item", "creando", "editando", "ver" })
	public void nuevaOrganizacion() {
		item = new PersonaJuridica();
		creando = true;
		editando = false;
		ver = false;
	}

	@Command("guardar")
	@NotifyChange({ "creando", "editando", "ver" })
	public void guardar() throws Exception {
		if (creando) {
			try {
				int id = servicio.nuevaPersona(item);
				Clients.showNotification("Nueva Organizacion registrada con Id: " + id, Clients.NOTIFICATION_TYPE_INFO,
						null, "top_right", 4000);
				creando = false;
				editando = false;
				ver = true;
			} catch (ConstraintViolationException cve) {
				Messagebox.show(UtilGisfpp.getMensajeValidations(cve), "Error de Validacion de Datos", Messagebox.OK,
						Messagebox.ERROR);
			} catch (Exception e) {
				throw e;
			}
		}
		if (editando) {
			try {
				servicio.actualizarPersona(item);
				Clients.showNotification("Los cambios efectuados han sido registrados.", Clients.NOTIFICATION_TYPE_INFO,
						null, "top_right", 4000);
				creando = false;
				editando = false;
				ver = true;
			} catch (ConstraintViolationException cve) {
				Messagebox.show(UtilGisfpp.getMensajeValidations(cve), "Error de Validacion de Datos", Messagebox.OK,
						Messagebox.ERROR);
			} catch (Exception e) {
				throw e;
			}
		}
	}

	@Command("reEditar")
	@NotifyChange({ "creando", "editando", "ver" })
	public void reEditar() {
		creando = false;
		editando = true;
		ver = false;
	}

	@Command("cancelar")
	@NotifyChange({ "item", "creando", "editando", "ver" })
	public void cancelar() {
		if (modo.equals(UtilGisfpp.MOD_EDICION)) {
			volver();
		} else {
			item = null;
			creando = false;
			editando = false;
			ver = true;
		}
	}

	@Command("volver")
	public void volver() {
		Panel panel = (Panel) Path.getComponent("/panelCentro/pnlCrudOrganizacion");
		Include include = (Include) Path.getComponent("/panelCentro");
		if (panel != null) {
			panel.onClose();
			include.setSrc(null);
			include.setSrc("vistas/persona/listaOrganizaciones.zul");
		}
	}

	public PersonaJuridica getItem() {
		return item;
	}

	public void setItem(PersonaJuridica item) {
		this.item = item;
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

}// fin de la clase
