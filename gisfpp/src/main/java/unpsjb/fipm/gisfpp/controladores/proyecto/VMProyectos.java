package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.util.Arrays;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;

import unpsjb.fipm.gisfpp.entidades.proyecto.EstadoProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.TipoProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.ServiciosProyecto;

public class VMProyectos {

	private ServiciosProyecto servicios;
	private Proyecto seleccionado = null;
	private List<Proyecto> listaProyectos;

	private Proyecto nuevoProyecto = null;
	private boolean creando = false;
	private boolean editando = false;

	@Init
	public void init() {

		if (servicios == null) {
			servicios = (ServiciosProyecto) SpringUtil.getBean("servProyecto");
		}
		if (listaProyectos == null) {
			obtenerAllProyectos();
		}
	}

	public List<TipoProyecto> getTipos() {
		return Arrays.asList(TipoProyecto.values());
	}

	public List<EstadoProyecto> getEstados() {
		return Arrays.asList(EstadoProyecto.values());
	}

	@Command("nuevo")
	@NotifyChange({ "nuevoProyecto", "creando" })
	public void nuevoProyecto() {
		nuevoProyecto = new Proyecto("", "", "", "", null, null, null, null, null);
		creando = true;
	}

	@Command("guardar")
	@NotifyChange({ "nuevoProyecto", "creando" })
	public void guardarProyecto() {
		int id_proyecto = 0;
		try {
			id_proyecto = servicios.guardarProyecto(nuevoProyecto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Clients.showNotification("Proyecto guardado en la BD con Id: " + String.valueOf(id_proyecto), "info", null,
				"top_right", 4000);
		creando = false;
	}

	@Command
	@NotifyChange({ "creando", "nuevoProyecto" })
	public void cancelar() {
		creando = false;
		nuevoProyecto = null;
	}

	public Proyecto getNuevoProyecto() {
		return nuevoProyecto;
	}

	public void setNuevoProyecto(Proyecto seleccion) {
		this.nuevoProyecto = seleccion;
	}

	@Command("obtenerTodo")
	@NotifyChange("lista")
	public void obtenerAllProyectos() {
		try {
			listaProyectos = servicios.obtenerTodosProyectos();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<Proyecto> getListaProyectos() {
		return this.listaProyectos;
	}

	public Boolean getCreando() {
		return creando;
	}

	public Boolean getEditando() {
		return editando;
	}

	public Proyecto getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Proyecto seleccionado) {
		this.seleccionado = seleccionado;
	}

}// Fin de la clase
