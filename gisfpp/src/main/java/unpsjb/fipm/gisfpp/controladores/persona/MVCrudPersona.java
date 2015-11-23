package unpsjb.fipm.gisfpp.controladores.persona;

import java.util.HashMap;

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

import unpsjb.fipm.gisfpp.entidades.persona.DatoDeContacto;
import unpsjb.fipm.gisfpp.entidades.persona.Domicilio;
import unpsjb.fipm.gisfpp.entidades.persona.Identificador;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.servicios.persona.IServiciosPersonaFisica;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class MVCrudPersona {

	private IServiciosPersonaFisica servPF;
	private PersonaFisica item;
	private boolean creando = false;
	private boolean editando = false;
	private boolean ver = false;
	private String modo;
	private String titulo;

	@SuppressWarnings("unchecked")
	@Init
	@NotifyChange({ "creando", "editando", "ver", "modo", "item", "titulo" })
	public void init() {
		final HashMap<String, Object> opciones = (HashMap<String, Object>) Sessions.getCurrent()
				.getAttribute("opcCrudPersona");
		modo = (String) opciones.get("modo");
		servPF = (IServiciosPersonaFisica) SpringUtil.getBean("servPersonaFisica");
		switch (modo) {
		case UtilGisfpp.MOD_NUEVO: {
			item = new PersonaFisica();
			creando = true;
			titulo = "Nueva Persona";
			break;
		}
		case UtilGisfpp.MOD_EDICION: {
			item = (PersonaFisica) opciones.get("item");
			editando = true;
			titulo = "Editando Persona: " + item.getNombre();
			break;
		}
		case UtilGisfpp.MOD_VER: {
			item = (PersonaFisica) opciones.get("item");
			ver = true;
			titulo = "Ver Persona: " + item.getNombre();
			break;
		}
		}
		Sessions.getCurrent().removeAttribute("opcCrudPersona");
	}// fin del método init

	@Command("volver")
	public void volver() {
		Panel panel = (Panel) Path.getComponent("/panelCentro/pnlCrudPersona");
		Include include = (Include) Path.getComponent("/panelCentro");
		if (panel != null) {
			panel.onClose();
			include.setSrc(null);
			include.setSrc("vistas/persona/listarPersonas.zul");
		}
	}

	@Command("guardar")
	@NotifyChange({ "creando", "editando", "ver" })
	public void guardar() throws Exception {
		if (creando) {
			try {
				int id = servPF.nuevaPersonaFisica(item);
				Clients.showNotification("Nueva Persona Creada. Id: " + id, Clients.NOTIFICATION_TYPE_INFO, null,
						"top_right", 4000);
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
			try {
				servPF.actualizarPersonaFisica(item);
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

	@Command("nuevaPersona")
	@NotifyChange({ "item", "creando", "editando", "ver" })
	public void nuevaPersona() {
		item = new PersonaFisica();
		creando = true;
		editando = false;
		ver = false;
	}

	@Command("reEditar")
	@NotifyChange({ "creando", "editando", "ver" })
	public void reEditar() {
		creando = false;
		editando = true;
		ver = false;
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

	@Command("verDlgIdentificacion")
	public void verDlgIdentificacion(@BindingParam("modo") String arg1, @BindingParam("valor") Identificador arg2) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("modo", arg1);
		map.put("valor", arg2);
		Window dlg = (Window) Executions.createComponents("vistas/persona/dlgIdentificacion.zul", null, map);
		dlg.doModal();
	}

	@GlobalCommand("retornoDlgIdentificacion")
	@NotifyChange({ "item" })
	public void retornoDlgIdentificacion(@BindingParam("modo") String arg1, @BindingParam("opcion") int arg2,
			@BindingParam("valor") Identificador arg3) {
		if (arg2 == Messagebox.OK) {
			if (arg1.equals(UtilGisfpp.MOD_NUEVO)) {
				item.agregarIdentificador(arg3);
			}
			if (arg1.equals(UtilGisfpp.MOD_EDICION)) {
				item.getIdentificadores().indexOf(arg3);
			}
		}
	}

	@Command("verDlgDatosContacto")
	public void verDlgDatosContacto(@BindingParam("modo") String arg1, @BindingParam("item") DatoDeContacto arg2) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("modo", arg1);
		map.put("datosContacto", arg2);
		Window dlg = (Window) Executions.createComponents("vistas/persona/dlgDatosContacto.zul", null, map);
		dlg.doModal();
	}

	@GlobalCommand("retornoDlgDatosContacto")
	@NotifyChange("item")
	public void retornoDlgDatosContacto(@BindingParam("modo") String arg1, @BindingParam("opcion") int arg2,
			@BindingParam("datosContacto") DatoDeContacto arg3) {
		if (arg2 == Messagebox.OK) {
			if (arg1.equals(UtilGisfpp.MOD_NUEVO)) {
				item.getDatosDeContacto().add(arg3);
			}
			if (arg1.equals(UtilGisfpp.MOD_EDICION)) {
				item.getDatosDeContacto().indexOf(arg3);
			}
		}
	}

	@Command("verDlgDomicilios")
	public void verDlgDomicilios(@BindingParam("modo") String arg1, @BindingParam("valor") Domicilio arg2) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("modo", arg1);
		map.put("domicilio", arg2);
		Window dlg = (Window) Executions.createComponents("vistas/persona/dlgDomicilios.zul", null, map);
		dlg.doModal();
	}

	@GlobalCommand("retornoDlgDomicilios")
	@NotifyChange("item")
	public void retornoDlgDomicilios(@BindingParam("modo") String arg1, @BindingParam("opcion") int arg2,
			@BindingParam("domicilio") Domicilio arg3) {
		if (arg2 == Messagebox.OK) {
			if (arg1.equals(UtilGisfpp.MOD_NUEVO)) {
				item.getDomicilios().add(arg3);
			}
			if (arg1.equals(UtilGisfpp.MOD_EDICION)) {
				item.getDomicilios().indexOf(arg3);
			}
		}
	}

	@Command("quitarDomicilios")
	@NotifyChange("item")
	public void quitarDomicilios(@BindingParam("index") int index) {
		item.getDomicilios().remove(index);
	}

	@Command("quitarDatosContacto")
	@NotifyChange("item")
	public void quitarDatosContacto(@BindingParam("index") int index) {
		item.getDatosDeContacto().remove(index);
	}

	@Command("quitarIdentificador")
	@NotifyChange("item")
	public void quitarIdentificacion(@BindingParam("index") int index) {
		item.getIdentificadores().remove(index);
	}

	public String getTitulo() {
		return titulo;
	}

	public PersonaFisica getItem() {
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

}// Fin de la clase MVCrudPersona
