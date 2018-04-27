package unpsjb.fipm.gisfpp.controladores.isfpp;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorWorkflow;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;

/**
 * 
 * @author isaias
 * 
 *
 */
public class MVCrudIsfpp {

	private Logger log;
	private Isfpp item;
	private IServiciosIsfpp servicio;
	private GestorWorkflow gestorWkFl;
	private SubProyecto perteneceA;
	private String modo;
	private boolean creando;
	private boolean editando;
	private boolean ver;
	private String titulo = " ";
	private HashMap<String, Object> args;

	@SuppressWarnings("unchecked")
	@Init
	@NotifyChange({ "modo", "item", "creando", "editando", "ver", "titulo" })
	public void init() throws Exception {
		log = UtilGisfpp.getLogger();
		gestorWkFl = (GestorWorkflow) SpringUtil.getBean("servGestionWorkflow");
		servicio = (IServiciosIsfpp) SpringUtil.getBean("servIsfpp");
		final HashMap<String, Object> args = (HashMap<String, Object>) Sessions.getCurrent()
				.getAttribute(UtilGuiGisfpp.PRM_PNL_CENTRAL);
		modo = (String) args.get("modo");

		// TODO Revisar pertenece a. Eliminar todo lo que no corresponde
		item = servicio.getInstancia((Integer) args.get("idItem"));
		item.setPerteneceA(perteneceA);
		creando = (editando = false);
		setTitulo("Ver Isfpp: " + item.getTitulo());
		ver = true;

	}

	public Isfpp getItem() {
		return item;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getModo() {
		return modo;
	}

	public boolean isCreando() {
		return creando;
	}

	public boolean isEditando() {
		return editando;
	}

	public boolean isVer() {
		return ver;
	}


	@Command("salir")
	public void salir() {
		Map<String, Object> map = new HashMap<>();
		if (modo.equals(UtilGisfpp.MOD_NUEVO) || modo.equals(UtilGisfpp.MOD_EDICION)) {
			map.put("actualizar", true);
		} else {
			map.put("actualizar", false);
		}
		BindUtils.postGlobalCommand(null, null, "cerrandoTab", map);
		Tab tab = (Tab) args.get("tab");
		tab.close();
	}


	// Dialogo para ver los datos de contacto de una persona
	@Command("dlgVerDatosContacto")
	public void verDlgVerDatosContacto(@BindingParam("itemPersona") PersonaFisica arg1) {
		final HashMap<String, Object> map = new HashMap<>();
		map.put("itemPersona", arg1);
		Window dlg = (Window) Executions.createComponents("vistas/persona/dlgVerDatosContacto.zul", null, map);
		dlg.doModal();
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}