package unpsjb.fipm.gisfpp.controladores.persona;

import java.util.HashMap;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;

import unpsjb.fipm.gisfpp.entidades.persona.Persona;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;
import unpsjb.fipm.gisfpp.servicios.persona.ServicioPersonaFisica;
import unpsjb.fipm.gisfpp.servicios.persona.ServicioPersonaJuridica;

public class MVCrudPersona {

	private ServicioPersonaFisica servPF;
	private ServicioPersonaJuridica servPJ;
	private PersonaFisica seleccionPF;
	private PersonaJuridica seleccionPJ;
	private boolean creando = false;
	private boolean editando = false;
	private boolean ver = false;
	private String modo;
	private String tipo;
	private String titulo;

	@Init
	public void init() {
		final HashMap<String, Object> opciones = (HashMap<String, Object>) Sessions.getCurrent()
				.getAttribute("opcCrudPersona");
		tipo = (String) opciones.get("tipo");
		modo = (String) opciones.get("modo");
		if (tipo.equals("pf")) {
			switch (modo) {
			case "nuevo": {
				servPF = (ServicioPersonaFisica) SpringUtil.getBean("servPersonaFisica");
				seleccionPF = new PersonaFisica();
				creando = true;
				titulo = "Nueva Persona Física";
				break;
			}
			default:
				break;
			}
		} else {// tipo es pj (Persona Juridica)
			switch (modo) {
			case "nuevo": {
				servPJ = (ServicioPersonaJuridica) SpringUtil.getBean("servPersonaJuridica");
				seleccionPJ = new PersonaJuridica();
				creando = true;
				titulo = "Nueva Persona Juridica";
				break;
			}

			default:
				break;
			}
		}

	}

	@Command("volver")
	public void volver() {
		Panel panel = (Panel) Path.getComponent("/panelCentro/pnlCrudPersona");
		Include include = (Include) Path.getComponent("/panelCentro");
		if (panel != null) {
			panel.onClose();
			include.setSrc(null);
			include.setSrc("vistas/persona/listarPersonas.zul");
		}

	}

	@Command("menuDomicilios")
	public void crdDomicilio(@BindingParam("accion") String accion, @BindingParam("item") Persona item) {

		switch (accion) {
		case "agregar":
			Messagebox.show("Agregando un nuevo domicilio para esta persona!!!");
			break;
		case "editar":
			Messagebox.show("Editando el domicilio seleccionado de esta persona!!!!!");
			break;
		case "eliminar":
			Messagebox.show("Eliminando el domicilio seleccionado de esta persona!!!");

		}

	}

	@Command("cancelar")
	@NotifyChange({ "creando", "editando", "ver" })
	public void cancelar() {
		if (modo == "edicion") {
			volver();
		} else {
			seleccionPF = null;
			seleccionPJ = null;
			creando = false;
			editando = false;
			ver = true;
		}
	}

	public String getTipo() {
		return tipo;
	}

	public String getTitulo() {
		return titulo;
	}

	public PersonaFisica getSeleccionPF() {
		return seleccionPF;
	}

	public PersonaJuridica getSeleccionPJ() {
		return seleccionPJ;
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

}// Fin de la clase MVCrudPersona
