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
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Messagebox;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPJ;
import unpsjb.fipm.gisfpp.util.GisfppException;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;

public class MVListarOrganizaciones {

	private List<PersonaJuridica> lista;
	private IServicioPJ servicio;

	@Init
	public void init() throws Exception {
		servicio = (IServicioPJ) SpringUtil.getBean("servPersonaJuridica");
		recuperarTodo();
	}

	@Command("recuperarTodo")
	@NotifyChange("lista")
	public void recuperarTodo() throws Exception {
		lista = servicio.getListado();
	}

	@Command("salir")
	public void salir() {
		UtilGuiGisfpp.quitarPnlCentral("/panelCentro/pnlListaOrganizaciones");
	}

	@Command("nueva")
	public void nueva() {
		final HashMap<String, Object> map = new HashMap<>();
		map.put("modo", UtilGisfpp.MOD_NUEVO);
		
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlListaOrganizaciones", "vistas/persona/crudOrganizacion.zul", map);
	}

	@Command("editar")
	public void editar(@BindingParam("item") PersonaJuridica arg1) {
		final HashMap<String, Object> map = new HashMap<>();
		map.put("modo", UtilGisfpp.MOD_EDICION);
		map.put("idItem", arg1.getId());
		
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlListaOrganizaciones", "vistas/persona/crudOrganizacion.zul", map);
	}

	@Command("ver")
	public void ver(@BindingParam("item") PersonaJuridica arg1) {
		final HashMap<String, Object> map = new HashMap<>();
		map.put("modo", UtilGisfpp.MOD_VER);
		map.put("idItem", arg1.getId());
		
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlListaOrganizaciones", "vistas/persona/crudOrganizacion.zul",map);
	}
	
	@Command("eliminar")
	public void eliminar(@BindingParam("item")PersonaJuridica arg1){
		Messagebox.show("Desea realmente eliminar el registro de esta Organización?", "Gisfpp: Eliminando Organización", 
				Messagebox.YES+Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {
					
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals(Messagebox.ON_YES)) {
							try {
								servicio.eliminar(arg1);
								Clients.showNotification("Organización eliminada.", Clients.NOTIFICATION_TYPE_INFO,
										null, "top_right", 3500);
								lista.remove(arg1);
								BindUtils.postNotifyChange(null, null, getAutoReferencia(), "lista");
							} catch (DataIntegrityViolationException exc) {
								int codError = ((ConstraintViolationException)exc.getCause()).getErrorCode();
								if(codError == 1451){
									throw new GisfppException("Error de integridad referencial. No se puede eliminar "
											+ "el registro de esta \"Organización\" debido a que esta asociada "
											+ "con alguna otra entidad del sistema.");
								}
							}
							
						}
					}		
				});
	}

	public List<PersonaJuridica> getLista() {
		return lista;
	}
	
	public MVListarOrganizaciones getAutoReferencia(){
		return this;
	}

}// fin de la clase
