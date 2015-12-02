package unpsjb.fipm.gisfpp.controladores.persona;

import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Include;
import org.zkoss.zul.Panel;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPersona;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class MVListarOrganizaciones {

	private List<PersonaJuridica> lista;
	private IServicioPersona servicio;

	@Init
	public void init() throws Exception {
		servicio = (IServicioPersona) SpringUtil.getBean("servPersona");
		recuperarTodo();
	}

	@Command("recuperarTodo")
	@NotifyChange("lista")
	public void recuperarTodo() throws Exception {
		lista = servicio.recuperarSoloPJ();
		System.out.println("En recuperarTodo de MVListarOrganizaciones");
	}

	@Command("salir")
	public void salir() {
		Panel panel = (Panel) Path.getComponent("/panelCentro/pnlListaOrganizaciones");
		Include include = (Include) Path.getComponent("/panelCentro");
		if (panel != null) {
			panel.onClose();
			include.setSrc(null);
		}
	}

	@Command("nueva")
	public void nueva() {
		final HashMap<String, Object> map = new HashMap<>();
		map.put("modo", UtilGisfpp.MOD_NUEVO);
		Sessions.getCurrent().setAttribute("opcCrudOrganizacion", map);

		Panel panel = (Panel) Path.getComponent("/panelCentro/pnlListaOrganizaciones");
		Include include = (Include) Path.getComponent("/panelCentro");
		if (panel != null) {
			panel.onClose();
			include.setSrc(null);
			include.setSrc("vistas/persona/crudOrganizacion.zul");
		}
	}

	@Command("editar")
	public void editar(@BindingParam("item") PersonaJuridica arg1) {
		final HashMap<String, Object> map = new HashMap<>();
		map.put("modo", UtilGisfpp.MOD_EDICION);
		map.put("item", arg1);
		Sessions.getCurrent().setAttribute("opcCrudOrganizacion", map);

		Panel panel = (Panel) Path.getComponent("/panelCentro/pnlListaOrganizaciones");
		Include include = (Include) Path.getComponent("/panelCentro");
		if (panel != null) {
			panel.onClose();
			include.setSrc(null);
			include.setSrc("vistas/persona/crudOrganizacion.zul");
		}
	}

	@Command("ver")
	public void ver(@BindingParam("item") PersonaJuridica arg1) {
		final HashMap<String, Object> map = new HashMap<>();
		map.put("modo", UtilGisfpp.MOD_VER);
		map.put("item", arg1);
		Sessions.getCurrent().setAttribute("opcCrudOrganizacion", map);

		Panel panel = (Panel) Path.getComponent("/panelCentro/pnlListaOrganizaciones");
		Include include = (Include) Path.getComponent("/panelCentro");
		if (panel != null) {
			panel.onClose();
			include.setSrc(null);
			include.setSrc("vistas/persona/crudOrganizacion.zul");
		}

	}

	public List<PersonaJuridica> getLista() {
		return lista;
	}

}// fin de la clase
