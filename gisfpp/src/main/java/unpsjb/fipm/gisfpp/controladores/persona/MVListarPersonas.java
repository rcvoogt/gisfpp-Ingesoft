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
import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;
import unpsjb.fipm.gisfpp.servicios.persona.ServicioPersonaFisica;
import unpsjb.fipm.gisfpp.servicios.persona.ServicioPersonaJuridica;

public class MVListarPersonas {

	private List<PersonaFisica> fisicas;
	private List<PersonaJuridica> juridicas;
	private ServicioPersonaJuridica servJuridica;
	private ServicioPersonaFisica servFisica;

	@Init
	public void init() throws Exception {
		servJuridica = (ServicioPersonaJuridica) SpringUtil.getBean("servPersonaJuridica");
		servFisica = (ServicioPersonaFisica) SpringUtil.getBean("servPersonaFisica");
		todoPersonasFisica();
		todoPersonasJuridica();
	}

	@Command("todoPersonasFisica")
	@NotifyChange("fisicas")
	public void todoPersonasFisica() throws Exception {
		fisicas = servFisica.todoPersonaFisica();
	}

	@Command("todoPersonasJuridica")
	@NotifyChange("juridicas")
	public void todoPersonasJuridica() throws Exception {
		juridicas = servJuridica.todoPersonaJuridica();
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
	public void nuevaPersona(@BindingParam("tipo") String tipo) {
		final HashMap<String, Object> map = new HashMap<>();
		map.put("tipo", tipo);
		map.put("modo", "nuevo");
		Sessions.getCurrent().setAttribute("opcCrudPersona", map);

		Panel panel = (Panel) Path.getComponent("/panelCentro/pnlListarPersonas");
		Include include = (Include) Path.getComponent("/panelCentro");
		if (panel != null) {
			panel.onClose();
			include.setSrc(null);
			include.setSrc("vistas/persona/crudPersona.zul");
		}
	}

	public List<PersonaFisica> getFisicas() {
		return fisicas;
	}

	public List<PersonaJuridica> getJuridicas() {
		return juridicas;
	}

}// Fin de la clase MVListarPersonas
