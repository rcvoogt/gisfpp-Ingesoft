package unpsjb.fipm.gisfpp.controladores.persona;

import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPF;
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
		
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlListaPersonas", "vistas/persona/crudPersona.zul", map);
	}

	@Command("editarPersona")
	public void editarPersona(@BindingParam("item") PersonaFisica arg1) {
		final HashMap<String, Object> map = new HashMap<>();
		map.put("modo", UtilGisfpp.MOD_EDICION);
		map.put("idItem", arg1.getId());
		
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlListaPersonas", "vistas/persona/crudPersona.zul", map);
	}

	@Command("verPersona")
	public void verPersona(@BindingParam("item") PersonaFisica arg1) {
		final HashMap<String, Object> map = new HashMap<>();
		map.put("modo", UtilGisfpp.MOD_VER);
		map.put("idItem", arg1.getId());
		
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlListaPersonas", "vistas/persona/crudPersona.zul", map);
	}

	public List<PersonaFisica> getPersonas() {
		return personas;
	}

}// Fin de la clase MVListarPersonas
