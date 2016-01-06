package unpsjb.fipm.gisfpp.controladores.persona;

import java.util.HashMap;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.persona.DatoDeContacto;
import unpsjb.fipm.gisfpp.entidades.persona.Domicilio;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPJ;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;

public class MVCrudOrganizacion {

	private IServicioPJ servicio;
	private PersonaJuridica item;
	private boolean creando = false;
	private boolean editando = false;
	private boolean ver = false;
	private String modo;
	private String titulo;
	private Logger log = UtilGisfpp.getLogger();

	@Init
	@NotifyChange({ "modo", "item", "creando", "editando", "ver", "titulo" })
	public void init() throws Exception {
		@SuppressWarnings("unchecked")
		final HashMap<String, Object> opciones = (HashMap<String, Object>) Sessions.getCurrent()
				.getAttribute(UtilGuiGisfpp.PRM_PNL_CENTRAL);
		modo = (String) opciones.get("modo");
		servicio = (IServicioPJ) SpringUtil.getBean("servPersonaJuridica");
		switch (modo) {
		case UtilGisfpp.MOD_NUEVO: {
			item = new PersonaJuridica();
			creando = true;
			titulo = "Nueva Organizacion";
			break;
		}
		case UtilGisfpp.MOD_EDICION: {
			int id = (int) opciones.get("idItem");
			item = servicio.getInstancia(id);
			editando = true;
			titulo = "Editar Organizacion: " + item.getNombre();
			break;
		}
		case UtilGisfpp.MOD_VER: {
			int id = (int) opciones.get("idItem");
			item = servicio.getInstancia(id);
			ver = true;
			titulo = "Ver Organizacion: " + item.getNombre();
			break;
		}
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
	@NotifyChange({ "creando", "editando", "ver", "item" })
	public void guardar() throws Exception {
		try {
			if(creando){
				servicio.persistir(item);
				Clients.showNotification("Nueva Organizacion guardada", Clients.NOTIFICATION_TYPE_INFO,
						null, "top_right", 3500);
			}else if(editando){
				servicio.editar(item);
				Clients.showNotification("Se han guardado los cambios efectuados.", Clients.NOTIFICATION_TYPE_INFO,
						null, "top_right", 3500);
			}
			creando = editando = false;
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

	@Command("reEditar")
	@NotifyChange({ "creando", "editando", "ver" })
	public void reEditar() {
		creando = ver =false;
		editando = true;
	}

	@Command("cancelar")
	@NotifyChange({ "item", "creando", "editando", "ver" })
	public void cancelar() {
		creando = false;
		editando = false;
		ver = true;
	}

	@Command("volver")
	public void volver() {
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlCrudOrganizacion", "vistas/persona/listadoOrganizaciones.zul");		
	}
	
	@Command("salir")
	public void salir() {
		UtilGuiGisfpp.quitarPnlCentral("/panelCentro/pnlCrudOrganizacion");
	}

	//Dialogo de busqueda de una persona
	@Command("verDlgLkpPersona")
	public void verDldLkpPersona() {
		Window dlg = (Window) Executions.createComponents("/vistas/persona/dlgLookupPersona.zul", null, null);
		dlg.doModal();
	}

	@GlobalCommand("obtenerLkpPersona")
	@NotifyChange("item")
	public void obtPersonaLookup(@BindingParam("seleccion") PersonaFisica arg1) throws Exception {
		item.getContactos().add(arg1);
	}
	//Dialogo de busqueda de una persona
	
	@Command("quitarPersonaContacto")
	@NotifyChange("item")
	public void quitarPersonaContacto(@BindingParam("valor") PersonaFisica arg1) throws Exception {
		item.getContactos().remove(arg1);
	}
	
	//Dialogo para ver los datos de contacto de una persona
	//de contacto de la Organizacion
	@Command("dlgVerDatosContacto")
	public void verDlgVerDatosContacto(@BindingParam("item") PersonaFisica arg1) {
		final HashMap<String, Object> map = new HashMap<>();
		map.put("item", arg1);
		Window dlg = (Window) Executions.createComponents("vistas/persona/dlgVerDatosContacto.zul", null, map);
		dlg.doModal();
	}

	//Cuadro de dialogo para agregar o editar un dato de contacto para la organizacion
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
	//cuadro de dialogo dato de contacto
	
	//Dialogo para agregar o editar un domicilio de la organizacion.
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
	//Dialogo Domicilio

	@Command("quitarDomicilio")
	@NotifyChange("item")
	public void quitarDomicilios(@BindingParam("valor") Domicilio arg1) {
		item.getDomicilios().remove(arg1);
	}

	@Command("quitarDatosContacto")
	@NotifyChange("item")
	public void quitarDatosContacto(@BindingParam("index") int index) {
		item.getDatosDeContacto().remove(index);
	}

	public PersonaJuridica getItem() {
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

}// fin de la clase
