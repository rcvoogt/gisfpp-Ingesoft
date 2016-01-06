package unpsjb.fipm.gisfpp.controladores.staff;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zkplus.spring.SpringUtil;

import unpsjb.fipm.gisfpp.entidades.staff.StaffFI;
import unpsjb.fipm.gisfpp.servicios.staff.IServiciosStaffFI;
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

	@Command("salir")
	public void salir() {
		UtilGuiGisfpp.quitarPnlCentral("/panelCentro/pnlListaStaffFI");
	}

	public List<StaffFI> getLista() {
		return lista;
	}

}// Fin de la clase MVListaStaffFI
