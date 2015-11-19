package unpsjb.fipm.gisfpp.controladores.persona;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Path;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.servicios.persona.IServiciosPersonaFisica;

public class MVDlgLkpPersona {

	private List<PersonaFisica> lista;
	private List<PersonaFisica> temporal;
	private List<PersonaFisica> listFiltrada;
	private IServiciosPersonaFisica servicio;
	private String campoLookup = "";
	private String valorLookup = "";
	private boolean filtrado = false;

	@Init
	@NotifyChange("lista")
	public void init() throws Exception {
		servicio = (IServiciosPersonaFisica) SpringUtil.getBean("servPersonaFisica");
		try {
			lista = servicio.recuperarTodo();
		} catch (Exception e) {
			throw e;
		}
	}

	@Command("filtrar")
	@NotifyChange({ "lista", "filtrado" })
	public void filtrar() {
		temporal = lista;
		String busqueda = valorLookup.toLowerCase().trim();
		listFiltrada = new ArrayList<>();
		switch (campoLookup) {
		case ("Nombre y Apellido"): {
			for (PersonaFisica persona : lista) {
				if (persona.getNombre().toLowerCase().contains(busqueda)) {
					listFiltrada.add(persona);
				}
			}
			lista = listFiltrada;
			filtrado = true;
			break;
		}
		case ("DNI"): {
			for (PersonaFisica persona : lista) {
				if ((persona.getDni() != null) && (persona.getDni().toLowerCase().contains(busqueda))) {
					listFiltrada.add(persona);
				}
			}
			lista = listFiltrada;
			filtrado = true;
			break;
		}
		case ("CUIL"): {
			for (PersonaFisica persona : lista) {
				if ((persona.getCuil() != null) && (persona.getCuil().toLowerCase().contains(busqueda))) {
					listFiltrada.add(persona);
				}
			}
			lista = listFiltrada;
			filtrado = true;
			break;
		}
		default:
			Messagebox.show("Debe seleccionar el atributo de la Persona por la cual desea realizar la busqueda.",
					"Error en la busqueda", Messagebox.OK, Messagebox.EXCLAMATION);
		}

	}

	@Command("quitarFiltro")
	@NotifyChange({ "lista", "filtrado", "campoLookup", "valorLookup" })
	public void quitarFiltro() {
		lista = temporal;
		listFiltrada = null;
		filtrado = false;
		campoLookup = "";
		valorLookup = "";
	}

	// En cada parte del sistema que se utilice este DlgLkpPersona se deberá
	// implementar un command global con nombre "obtenerLkpPersona" para recibir
	// la
	// persona seleccionada
	@Command("seleccion")
	public void devolverSeleccion(@BindingParam("item") PersonaFisica persona) {
		final HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("seleccion", persona);
		BindUtils.postGlobalCommand(null, null, "obtenerLkpPersona", parametros);
		cerrar();
	};

	private void cerrar() {
		Window dlg = (Window) Path.getComponent("/dlgLkpPersona");
		dlg.detach();
	}

	public String getCampoLookup() {
		return campoLookup;
	}

	public void setCampoLookup(String campoLookup) {
		this.campoLookup = campoLookup;
	}

	public String getValorLookup() {
		return valorLookup;
	}

	public void setValorLookup(String valorLookup) {
		this.valorLookup = valorLookup;
	}

	public List<PersonaFisica> getLista() {
		return lista;
	}

	public boolean isFiltrado() {
		return filtrado;
	}

}// fin de la clase
