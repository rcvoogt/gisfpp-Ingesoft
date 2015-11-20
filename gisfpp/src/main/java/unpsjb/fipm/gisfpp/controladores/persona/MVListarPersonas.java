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

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.servicios.persona.IServiciosPersonaFisica;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class MVListarPersonas {

	private List<PersonaFisica> personas;
	private IServiciosPersonaFisica servPersona;

	@Init
	public void init() throws Exception {
		servPersona = (IServiciosPersonaFisica) SpringUtil.getBean("servPersonaFisica");
		recuperarTodo();
	}

	@Command("recuperarTodo")
	@NotifyChange("fisicas")
	public void recuperarTodo() throws Exception {
		personas = servPersona.recuperarTodo();
	}

	@Command("salir")
	public void salir() {
		Panel panel = (Panel) Path.getComponent("/panelCentro/pnlListarPersonas");
		Include include = (Include) Path.getComponent("/panelCentro");
		if (panel != null) {
			panel.onClose();
			include.setSrc(null);
		}
	}

	@Command("nuevaPersona")
	public void nuevaPersona() {
		final HashMap<String, Object> map = new HashMap<>();
		map.put("modo", UtilGisfpp.MOD_NUEVO);
		Sessions.getCurrent().setAttribute("opcCrudPersona", map);

		Panel panel = (Panel) Path.getComponent("/panelCentro/pnlListarPersonas");
		Include include = (Include) Path.getComponent("/panelCentro");
		if (panel != null) {
			panel.onClose();
			include.setSrc(null);
			include.setSrc("vistas/persona/crudPersona.zul");
		}
	}

	@Command("editarPersona")
	public void editarPersona(@BindingParam("item") PersonaFisica arg1) {
		final HashMap<String, Object> map = new HashMap<>();
		map.put("modo", UtilGisfpp.MOD_EDICION);
		map.put("item", arg1);
		Sessions.getCurrent().setAttribute("opcCrudPersona", map);

		Panel panel = (Panel) Path.getComponent("/panelCentro/pnlListarPersonas");
		Include include = (Include) Path.getComponent("/panelCentro");
		if (panel != null) {
			panel.onClose();
			include.setSrc(null);
			include.setSrc("vistas/persona/crudPersona.zul");
		}
	}

	@Command("verPersona")
	public void verPersona(@BindingParam("item") PersonaFisica arg1) {
		final HashMap<String, Object> map = new HashMap<>();
		map.put("modo", UtilGisfpp.MOD_VER);
		map.put("item", arg1);
		Sessions.getCurrent().setAttribute("opcCrudPersona", map);

		Panel panel = (Panel) Path.getComponent("/panelCentro/pnlListarPersonas");
		Include include = (Include) Path.getComponent("/panelCentro");
		if (panel != null) {
			panel.onClose();
			include.setSrc(null);
			include.setSrc("vistas/persona/crudPersona.zul");
		}
	}

	public List<PersonaFisica> getPersonas() {
		return personas;
	}

}// Fin de la clase MVListarPersonas
