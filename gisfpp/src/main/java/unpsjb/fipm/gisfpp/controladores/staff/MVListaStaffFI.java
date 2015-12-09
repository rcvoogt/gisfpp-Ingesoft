package unpsjb.fipm.gisfpp.controladores.staff;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zkplus.spring.SpringUtil;

import unpsjb.fipm.gisfpp.entidades.staff.StaffFI;
import unpsjb.fipm.gisfpp.servicios.staff.IServiosStaff;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;

public class MVListaStaffFI {

	private List<StaffFI> lista;
	private IServiosStaff servicios;
	private Logger log;

	@Init
	public void init() throws Exception {
		servicios = (IServiosStaff) SpringUtil.getBean("servStaffFI");
		recuperarTodo();
		log = UtilGisfpp.getLogger();
	}

	@Command("recuperarTodo")
	@NotifyChange("lista")
	public void recuperarTodo() throws Exception {
		try {
			lista = servicios.recuperarTodoStaff();
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

	@Command("salir")
	public void salir() {
		UtilGuiGisfpp.quitarPnlCentral("/panelCentro/pnlListaStaffFI");
	}

	public List<StaffFI> getLista() {
		return lista;
	}

}// Fin de la clase MVListaStaffFI
