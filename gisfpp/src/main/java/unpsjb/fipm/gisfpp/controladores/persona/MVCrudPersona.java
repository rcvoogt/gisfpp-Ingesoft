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
import unpsjb.fipm.gisfpp.entidades.persona.Identificador;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPF;
import unpsjb.fipm.gisfpp.util.GisfppException;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;

public class MVCrudPersona {

	private IServicioPF servPF;
	private PersonaFisica item;
	private Logger log;
	private boolean creando = false;
	private boolean editando = false;
	private boolean ver = false;
	private String modo;
	private String titulo;
	private boolean contraseniaModificada;
	private String repeticionContrasenia;

	@SuppressWarnings("unchecked")
	@Init
	@NotifyChange({ "creando", "editando", "ver", "modo", "item", "titulo" })
	public void init() throws Exception {
		final HashMap<String, Object> opciones = (HashMap<String, Object>) Sessions.getCurrent()
				.getAttribute(UtilGuiGisfpp.PRM_PNL_CENTRAL);
		modo = (String) opciones.get("modo");
		servPF = (IServicioPF) SpringUtil.getBean("servPersonaFisica");
		log = UtilGisfpp.getLogger();
		switch (modo) {
		case UtilGisfpp.MOD_NUEVO: {
			item = new PersonaFisica();
			creando = true;
			titulo = "Nueva Persona";
			break;
		}
		case UtilGisfpp.MOD_EDICION: {
			int id = (Integer)opciones.get("idItem");
			item = servPF.getInstancia(id);
			editando = true;
			titulo = "Editando Persona: " + item.getNombre();
			break;
		}
		case UtilGisfpp.MOD_VER: {
			int id = (Integer) opciones.get("idItem");
			item = servPF.getInstancia(id);
			ver = true;
			titulo = "Ver Persona: " + item.getNombre();
			break;
		}
		}
	}// fin del método init

	@Command("volver")
	public void volver() {
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlCrudPersona", "vistas/persona/listadoPersonas.zul");
		
	}
	
	@Command("salir")
	public void salir() {
		UtilGuiGisfpp.quitarPnlCentral("/panelCentro/pnlCrudPersona");
	}

	@Command("guardar")
	@NotifyChange({ "item","creando", "editando", "ver", "contraseniaModificada" })
	public void guardar() throws Exception {
		if(contraseniaModificada){
			if(!item.getUsuario().getPassword().equals(repeticionContrasenia)){
				Messagebox.show("Las contraseñas ingresadas no coinciden. Vuelva a ingresarlas por favor", "Validacion de contraseñas", Messagebox.OK, 
						Messagebox.ERROR);
				return;
			}
		}
		try {
			if(creando){
				servPF.persistir(item);
				Clients.showNotification("Nueva Persona guardada", Clients.NOTIFICATION_TYPE_INFO, null,
						"top_right", 3500);
			}else if(editando){
				servPF.editar(item);
				Clients.showNotification("Se han guardado los cambios efectuados.", Clients.NOTIFICATION_TYPE_INFO, null,
						"top_right", 3500);
			}
			creando = editando = false;
			ver = true;
			contraseniaModificada=false;
		}catch(ConstraintViolationException cve){
			Messagebox.show(UtilGisfpp.getMensajeValidations(cve), "Error: Validación de datos.", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	@Command("nuevaPersona")
	@NotifyChange({ "item", "creando", "editando", "ver","repeticionContrasenia" })
	public void nuevaPersona() {
		item = new PersonaFisica();
		repeticionContrasenia ="";
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
	@NotifyChange({ "creando", "editando", "ver", "item","contraseniaModificada"})
	public void cancelar() {
			creando = false;
			editando = false;
			ver = true;
			contraseniaModificada=false;
	}

	//Dialogo para agregar o editar un Numero de Identificacion 
	@Command("verDlgIdentificacion")
	public void verDlgIdentificacion(@BindingParam("modo") String arg1, @BindingParam("valor") Identificador arg2) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("modo", arg1);
		map.put("valor", arg2);
		Window dlg = (Window) Executions.createComponents("vistas/persona/dlgIdentificacion.zul", null, map);
		dlg.doModal();
	}

	@GlobalCommand("retornoDlgIdentificacion")
	@NotifyChange("item")
	public void retornoDlgIdentificacion(@BindingParam("modo") String arg1, @BindingParam("itemNew") Identificador arg2) {
		try{
			if(arg1.equals(UtilGisfpp.MOD_NUEVO)){
				item.addIdentificador(arg2);//Este metodo lanza una ConstraintViolationException si la Persona ya posee este identificador. 
			}
			Clients.showNotification("Guarde cambios para confirmar la operacion.", 
					Clients.NOTIFICATION_TYPE_WARNING,	null, "top_right", 4000);
			
		}catch(ConstraintViolationException cve){
			Messagebox.show(cve.getMessage(), "Error: validacion de datos", Messagebox.OK, Messagebox.ERROR);
		}
	}
	//Dialogo numero de identificacion

	//Dialogo para agregar o editar un Dato de Contacto
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
	public void retornoDlgDatosContacto(@BindingParam("modo") String arg1, @BindingParam("newItem") DatoDeContacto arg2) {
		if(arg1.equals(UtilGisfpp.MOD_NUEVO)){
			item.getDatosDeContacto().add(arg2);
		}
		Clients.showNotification("Guarde cambios para confirmar la operación.", 
				Clients.NOTIFICATION_TYPE_WARNING,	null, "top_right", 4000);
			
	}
	//Dialogo Dato de Contacto
	
	//Dialogo para agregar o editar un Domicilio
	@Command("verDlgDomicilios")
	public void verDlgDomicilios(@BindingParam("modo") String arg1, @BindingParam("item") Domicilio arg2) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("modo", arg1);
		map.put("domicilio", arg2);
		Window dlg = (Window) Executions.createComponents("vistas/persona/dlgDomicilios.zul", null, map);
		dlg.doModal();
	}

	
	@GlobalCommand("retornoDlgDomicilios")
	@NotifyChange("item")
	public void retornoDlgDomicilios(@BindingParam("modo") String arg1, @BindingParam("newItem") Domicilio arg2) {
		if(arg1.equals(UtilGisfpp.MOD_NUEVO)){
			item.addDomicilio(arg2);
		}
		Clients.showNotification("Guarde cambios para confirmar la operación.", 
				Clients.NOTIFICATION_TYPE_WARNING,	null, "top_right", 4000);
	}
	//Dialogo Domicilio
	
	@Command("quitarDomicilio")
	@NotifyChange("item")
	public void quitarDomicilio(@BindingParam("valor") Domicilio valor) {
		item.removerDomicilio(valor);
		Clients.showNotification("Guarde cambios para confirmar eliminacion del Domicilio", Clients.NOTIFICATION_TYPE_WARNING, 
				null, "top_right", 4000);
	}
	
	
	@Command("quitarDatosContacto")
	@NotifyChange("item")
	public void quitarDatosContacto(@BindingParam("item") DatoDeContacto dato) {
		item.getDatosDeContacto().remove(dato);
		Clients.showNotification("Guarde cambios para confirmar eliminacion del Dato de Contacto", Clients.NOTIFICATION_TYPE_WARNING, 
				null, "top_right", 4000);
	}

	@Command("quitarIdentificador")
	@NotifyChange("item")
	public void quitarIdentificacion(@BindingParam("valor") Identificador valor) {
		item.removerIdentificador(valor);
		Clients.showNotification("Guarde cambios para confirmar eliminacion del N° de Identificacion", Clients.NOTIFICATION_TYPE_WARNING, 
				null, "top_right", 4000);
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

	//Metodo llamado desde la vista "pnlDatosUsuario.zul"
	@Command("setContraseniaModificada")
	public void setContraseniaModificada(@BindingParam("opcion") boolean arg1) {
		this.contraseniaModificada = arg1;
	}

	public String getRepeticionContrasenia() {
		return repeticionContrasenia;
	}

	public void setRepeticionContrasenia(String repeticionContrasenia) {
		this.repeticionContrasenia = repeticionContrasenia;
	}
	
}// Fin de la clase MVCrudPersona
