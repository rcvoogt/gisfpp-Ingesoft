package unpsjb.fipm.gisfpp.controladores.staff;

import java.util.HashMap;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
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

import unpsjb.fipm.gisfpp.entidades.staff.StaffFI;
import unpsjb.fipm.gisfpp.servicios.staff.IServiciosStaffFI;
import unpsjb.fipm.gisfpp.util.GisfppException;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;

public class MVListaStaffFI {

	private List<StaffFI> lista;
	private IServiciosStaffFI servicios;
	private Logger log;

	@Init
	public void init() throws Exception {
		servicios = (IServiciosStaffFI) SpringUtil.getBean("servStaffFI");
		recuperarTodo();
		log = UtilGisfpp.getLogger();
	}

	@Command("recuperarTodo")
	@NotifyChange("lista")
	public void recuperarTodo() throws Exception {
		try {
			lista = servicios.getListado();
		} catch (Exception e) {
			log.debug(this.getClass().getName(), e);
			throw e;
		}
	}

	@Command("asociarPersona")
	public void asociarPersona() {
		final HashMap<String, Object> map = new HashMap<>();
		map.put("modo", UtilGisfpp.MOD_NUEVO);
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlListaStaffFI", "vistas/staff/crudStaff.zul", map);
	}
	
	@Command("editarAsociacion")
	public void editar(@BindingParam("item")StaffFI item){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("modo", UtilGisfpp.MOD_EDICION);
		map.put("idItem", item.getId());
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlListaStaffFI", "vistas/staff/crudStaff.zul", map);
	}
	
	@Command("verAsociacion")
	public void ver(@BindingParam("item")StaffFI item){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("modo", UtilGisfpp.MOD_VER);
		map.put("idItem", item.getId());
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlListaStaffFI", "vistas/staff/crudStaff.zul", map);
	}
	
	@Command("eliminarAsociacion")
	public void eliminarAsociacion(@BindingParam("item")StaffFI arg1){
		Messagebox.show("Desea realmente dar de baja este Miembro del Staff-FI?", "Gisfpp: Eliminando Miembro Staff-FI", 
				Messagebox.YES+Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {
					
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals(Messagebox.ON_YES)) {
							try {
								servicios.eliminar(arg1);
								Clients.showNotification("Miembro Staff-FI eliminado.", Clients.NOTIFICATION_TYPE_INFO,
										null, "top_right", 3500);
								lista.remove(arg1);
								BindUtils.postNotifyChange(null, null, getAutoReferencia(), "lista");
							} catch (DataIntegrityViolationException exc) {
								int codError = ((ConstraintViolationException)exc.getCause()).getErrorCode();
								if(codError == 1451){
									throw new GisfppException("Error de integridad referencial. No se puede dar de baja "
											+ "a este \"Miembro del Staff-FI\" debido a que esta asociado "
											+ "con alguna otra entidad del sistema.");
								}
							}
							
						}
					}		
				});
	}

	@Command("salir")
	public void salir() {
		UtilGuiGisfpp.quitarPnlCentral("/panelCentro/pnlListaStaffFI");
	}

	public List<StaffFI> getLista() {
		return lista;
	}
	
	private MVListaStaffFI getAutoReferencia(){
		return this;
	}

}// Fin de la clase MVListaStaffFI
