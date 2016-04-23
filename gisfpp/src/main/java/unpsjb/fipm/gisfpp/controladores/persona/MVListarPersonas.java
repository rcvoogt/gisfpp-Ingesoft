package unpsjb.fipm.gisfpp.controladores.persona;

import java.util.HashMap;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPF;
import unpsjb.fipm.gisfpp.util.GisfppException;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;

public class MVListarPersonas {

	private List<PersonaFisica> personas;
	private IServicioPF servPersona;

	@Init
	public void init() throws Exception {
		servPersona = (IServicioPF) SpringUtil.getBean("servPersonaFisica");
		recuperarTodo();
	}

	@Command("recuperarTodo")
	@NotifyChange("fisicas")
	public void recuperarTodo() throws Exception {
		personas = servPersona.getListado();
	}

	@Command("salir")
	public void salir() {
		UtilGuiGisfpp.quitarPnlCentral("/panelCentro/pnlListaPersonas");
	}

	@Command("nuevaPersona")
	public void nuevaPersona() {
		final HashMap<String, Object> map = new HashMap<>();
		map.put("modo", UtilGisfpp.MOD_NUEVO);
		map.put("btnVolverVisible", true);
		
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlListaPersonas", "vistas/persona/crudPersona.zul", map);
	}

	@Command("editarPersona")
	public void editarPersona(@BindingParam("item") PersonaFisica arg1) {
		final HashMap<String, Object> map = new HashMap<>();
		map.put("modo", UtilGisfpp.MOD_EDICION);
		map.put("idItem", arg1.getId());
		map.put("btnVolverVisible", true);
		
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlListaPersonas", "vistas/persona/crudPersona.zul", map);
	}

	@Command("verPersona")
	public void verPersona(@BindingParam("item") PersonaFisica arg1) {
		final HashMap<String, Object> map = new HashMap<>();
		map.put("modo", UtilGisfpp.MOD_VER);
		map.put("idItem", arg1.getId());
		map.put("btnVolverVisible", true);
		
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlListaPersonas", "vistas/persona/crudPersona.zul", map);
	}
	
	@Command("eliminarPersona")
	public void eliminarPersona(@BindingParam ("item") PersonaFisica arg1){
		Messagebox.show("Desea realmente eliminar el registro de esta Persona?", "Gisfpp: Eliminando Persona", 
				Messagebox.YES+Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {
					
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals(Messagebox.ON_YES)) {
							try {
								servPersona.eliminar(arg1);
								Clients.showNotification("Persona eliminada.", Clients.NOTIFICATION_TYPE_INFO,
										null, "top_right", 3500);
								personas.remove(arg1);
								BindUtils.postNotifyChange(null, null, getAutoReferencia(), "personas");
							} catch (DataIntegrityViolationException exc) {
								int codError = ((ConstraintViolationException)exc.getCause()).getErrorCode();
								if(codError == 1451){
									throw new GisfppException("Error de integridad referencial. No se puede eliminar "
											+ "el registro de esta \"Persona\" debido a que esta asociada "
											+ "con alguna otra entidad del sistema.");
								}
							}
							
						}
					}		
				});
	}
	
	private MVListarPersonas getAutoReferencia(){
		return this;
	}

	public List<PersonaFisica> getPersonas() {
		return personas;
	}

}// Fin de la clase MVListarPersonas
