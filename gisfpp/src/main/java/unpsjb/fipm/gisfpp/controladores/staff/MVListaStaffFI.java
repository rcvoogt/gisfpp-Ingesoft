package unpsjb.fipm.gisfpp.controladores.staff;

import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Include;
import org.zkoss.zul.Panel;

import unpsjb.fipm.gisfpp.entidades.staff.StaffFI;
import unpsjb.fipm.gisfpp.servicios.staff.IServiosStaff;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class MVListaStaffFI {

	private List<StaffFI> lista;
	private IServiosStaff servicios;

	@Init
	public void init() throws Exception {
		servicios = (IServiosStaff) SpringUtil.getBean("servStaffFI");
		recuperarTodo();
	}

	@Command("recuperarTodo")
	@NotifyChange("lista")
	public void recuperarTodo() throws Exception {
		lista = servicios.recuperarTodoStaff();
	}

	@Command("asociarPersona")
	public void asociarPersona() {
		final HashMap<String, Object> map = new HashMap<>();
		map.put("modo", UtilGisfpp.MOD_NUEVO);
		Sessions.getCurrent().setAttribute("opcCrudStaff", map);
		Panel panel = (Panel) Path.getComponent("/panelCentro/pnlListaStaffFI");
		Include include = (Include) Path.getComponent("/panelCentro");
		if (panel != null) {
			panel.onClose();
			include.setSrc(null);
			include.setSrc("vistas/staff/crudStaff.zul");
		}
	}

	@Command("salir")
	public void salir() {
		Panel panel = (Panel) Path.getComponent("/panelCentro/pnlListaStaffFI");
		Include include = (Include) Path.getComponent("/panelCentro");
		if (panel != null) {
			panel.onClose();
			include.setSrc(null);
		}
	}

	public List<StaffFI> getLista() {
		return lista;
	}

}// Fin de la clase MVListaStaffFI
