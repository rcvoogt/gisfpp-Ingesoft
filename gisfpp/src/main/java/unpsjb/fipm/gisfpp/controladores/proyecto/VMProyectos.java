package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.util.Arrays;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;

import unpsjb.fipm.gisfpp.entidades.proyecto.EstadoProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.TipoProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.ProyectoProxy;
import unpsjb.fipm.gisfpp.servicios.proyecto.ServiciosProyecto;

public class VMProyectos {

	private ServiciosProyecto servicios;
	private ProyectoProxy nuevoProyecto = null;
	private List<ProyectoProxy> lista;
	private Boolean creando = false;

	@Init
	public void init() {
		servicios = (ServiciosProyecto) SpringUtil.getBean("servProyecto");
		/*
		 * try { lista = servicios.getAllProyectos(); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
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
		nuevoProyecto = new ProyectoProxy("", "", "", "", null, null, null, null, null);
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
		/*
		 * for (ProyectoProxy proyecto : getListaProyectos()) {
		 * System.out.println("-------------------------------------");
		 * System.out.println("Codigo: " + proyecto.getCodigo());
		 * System.out.println("N Resolucion: " + proyecto.getResolucion());
		 * System.out.println("Titulo: " + proyecto.getTitulo());
		 * System.out.println("Descripcion: " + proyecto.getDescripcion());
		 * System.out.println("Tipo: " + proyecto.getTipo().getDescripcion());
		 * System.out.println("Inicio: " + proyecto.getInicio());
		 * System.out.println("Fin: " + proyecto.getFin()); System.out.println(
		 * "Detalle: " + proyecto.getDetalle());
		 * System.out.println("-------------------------------------"); }
		 */
		creando = false;
	}

	@Command
	@NotifyChange({ "creando", "nuevoProyecto" })
	public void cancelar() {
		creando = false;
		nuevoProyecto = null;
	}

	public ProyectoProxy getNuevoProyecto() {
		return nuevoProyecto;
	}

	public void setNuevoProyecto(ProyectoProxy seleccion) {
		this.nuevoProyecto = seleccion;
	}

	@Command("listarProyectos")
	@NotifyChange("lista")
	public List<ProyectoProxy> getListaProyectos() {
		try {
			lista = servicios.getAllProyectos();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public Boolean getCreando() {
		return creando;
	}

}// Fin de la clase
