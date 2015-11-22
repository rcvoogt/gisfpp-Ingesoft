package unpsjb.fipm.gisfpp.controladores.persona;

import java.util.ArrayList;
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

import unpsjb.fipm.gisfpp.entidades.persona.DatoDeContacto;
import unpsjb.fipm.gisfpp.entidades.persona.Domicilio;
import unpsjb.fipm.gisfpp.entidades.persona.Identificador;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaDeContacto;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPersonaJuridica;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class MVCrudOrganizacion {

	private IServicioPersonaJuridica servicio;
	private PersonaJuridica item;
	private List<PersonaDeContacto> contactos;
	private List<PersonaDeContacto> agregarContactos;
	private List<PersonaDeContacto> quitarContactos;
	private boolean creando = false;
	private boolean editando = false;
	private boolean ver = false;
	private String modo;
	private String titulo;

	@Init
	@NotifyChange({ "modo", "item", "creando", "editando", "ver", "titulo" })
	public void init() throws Exception {
		@SuppressWarnings("unchecked")
		final HashMap<String, Object> opciones = (HashMap<String, Object>) Sessions.getCurrent()
				.getAttribute("opcCrudOrganizacion");
		modo = (String) opciones.get("modo");
		servicio = (IServicioPersonaJuridica) SpringUtil.getBean("servPersonaJuridica");
		switch (modo) {
		case UtilGisfpp.MOD_NUEVO: {
			item = new PersonaJuridica();
			contactos = new ArrayList<PersonaDeContacto>();
			creando = true;
			titulo = "Nueva Organizacion";
			break;
		}
		case UtilGisfpp.MOD_EDICION: {
			item = (PersonaJuridica) opciones.get("item");
			contactos = servicio.recupararContactos(item);
			editando = true;
			titulo = "Editando Organización ( " + item.getNombre() + " )";
			break;
		}
		case UtilGisfpp.MOD_VER: {
			item = (PersonaJuridica) opciones.get("item");
			contactos = servicio.recupararContactos(item);
			ver = true;
			titulo = "Ver detalle de Organizacion (" + item.getNombre() + ")";
			break;
		}
		}
		Sessions.getCurrent().removeAttribute("opcCrudOrganizacion");
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
				int id = servicio.nuevaPersonaJuridica(item);
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
				servicio.actualizarPersonaJuridica(item);
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

	@Command("verDlgLkpPersona")
	public void verDldLkpPersona() {
		Window dlg = (Window) Executions.createComponents("/vistas/persona/dlgLookupPersona.zul", null, null);
		dlg.doModal();
	}

	@GlobalCommand("obtenerLkpPersona")
	@NotifyChange("contactos")
	public void obtPersonaLookup(@BindingParam("seleccion") PersonaFisica arg1) throws Exception {
		PersonaDeContacto contacto = new PersonaDeContacto();
		contacto.setOrganizacion(item);
		contacto.setPersona(arg1);
		try {
			servicio.agregarContacto(contacto);
			Clients.showNotification("Se registro una nueva Persona de Contacto para esta Organización.",
					Clients.NOTIFICATION_TYPE_INFO, null, "top_right", 4000);
		} catch (ConstraintViolationException cve) {
			Messagebox.show(UtilGisfpp.getMensajeValidations(cve), "Error de Validacion de Datos", Messagebox.OK,
					Messagebox.ERROR);
		} catch (Exception e) {
			throw e;
		}
		contactos.add(contacto);
	}

	@Command("quitarPersonaContacto")
	@NotifyChange("contactos")
	public void quitarPersonaContacto(@BindingParam("index") int arg1) throws Exception {
		try {
			servicio.quitarContacto(contactos.get(arg1));
			Clients.showNotification("", Clients.NOTIFICATION_TYPE_INFO, null, "top_right", 4000);
		} catch (Exception e) {
			throw e;
		}
		contactos.remove(arg1);
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
	public void verDlgDatosContacto(@BindingParam("modo") String arg1,
			@BindingParam("datosContacto") DatoDeContacto arg2) {
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
	public void verDlgDomicilios(@BindingParam("modo") String arg1, @BindingParam("domicilio") Domicilio arg2) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("modo", arg1);
		map.put("valor", arg2);
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

	public List<PersonaDeContacto> getContactos() {
		return contactos;
	}

}// fin de la clase
