package unpsjb.fipm.gisfpp.controladores.staff;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.staff.Rol;
import unpsjb.fipm.gisfpp.entidades.staff.StaffFI;
import unpsjb.fipm.gisfpp.servicios.staff.IServiosStaff;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class MVCrudStaff {

	private IServiosStaff servicio;
	private StaffFI item;
	private boolean creando = false;
	private boolean editando = false;
	private boolean ver = false;
	private String modo;
	private String titulo;

	@Init
	@NotifyChange({ "modo", "item", "titulo", "creando", "editando", "ver" })
	public void init() {
		@SuppressWarnings("unchecked")
		final HashMap<String, Object> opciones = (HashMap<String, Object>) Sessions.getCurrent()
				.getAttribute("opcCrudStaff");
		modo = (String) opciones.get("modo");
		servicio = (IServiosStaff) SpringUtil.getBean("servStaffFI");
		switch (modo) {
		case UtilGisfpp.MOD_NUEVO: {
			item = new StaffFI();
			titulo = "Nueva asociación Persona-StaffFI";
			creando = true;
			editando = false;
			ver = false;
			break;
		}
		default:
			break;
		}
	}

	@Command("nuevaAsociacion")
	@NotifyChange({ "item", "creando", "editando", "ver" })
	public void nuevaAsociacion() {
		item = new StaffFI();
		creando = true;
		editando = false;
		ver = false;
	}

	@Command("guardar")
	@NotifyChange({ "creando", "editando", "ver" })
	public void guardar() throws Exception {
		if (creando) {
			try {
				int id = servicio.nuevaAsociacionStaff(item);
				Clients.showNotification("Nueva asociación registrada. (Id: " + id + ")",
						Clients.NOTIFICATION_TYPE_INFO, null, "top_right", 4000);
				creando = false;
				editando = false;
				ver = true;
			} catch (ConstraintViolationException cve) {
				Messagebox.show(UtilGisfpp.getMensajeValidations(cve), "Error: Validación de Datos", Messagebox.OK,
						Messagebox.ERROR);
			} catch (Exception e) {
				throw e;
			}
		}
		if (editando) {

		}
	}

	@Command("cancelar")
	@NotifyChange({ "creando", "editando", "ver", "item" })
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
		Panel panel = (Panel) Path.getComponent("/panelCentro/pnlCrudStaff");
		Include include = (Include) Path.getComponent("/panelCentro");
		if (panel != null) {
			panel.onClose();
			include.setSrc(null);
			include.setSrc("vistas/staff/listaStaffFI.zul");
		}
	}

	@Command("verDlgLookupPersona")
	public void verDlgLookupPersona() {
		Window dlg = (Window) Executions.createComponents("vistas/persona/dlgLookupPersona.zul", null, null);
		dlg.doModal();
	}

	@GlobalCommand("obtenerLkpPersona")
	@NotifyChange("item")
	public void obtPersonaLookup(@BindingParam("seleccion") PersonaFisica arg1) {
		if (arg1 != null) {
			item.setMiembro(arg1);
		}
	}

	public StaffFI getItem() {
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

	public List<Rol> getCargos() {
		return Arrays.asList(Rol.values());
	}

}// fin de clase
