package unpsjb.fipm.gisfpp.controladores.persona;

import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zkplus.spring.SpringUtil;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPJ;
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

	public List<PersonaJuridica> getLista() {
		return lista;
	}

}// fin de la clase
