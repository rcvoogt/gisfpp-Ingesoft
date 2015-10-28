package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.util.Clients;

import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.TipoProyecto;;

public class VMProyectos {

	private Proyecto seleccion = null;
	private List<Proyecto> lista;

	@Init
	public void init() {
		lista = new ArrayList<Proyecto>();
		nuevoProyecto();
	}

	public List<TipoProyecto> getTipos() {
		return Arrays.asList(TipoProyecto.values());

	}

	public void nuevoProyecto() {
		seleccion = new Proyecto("", "", "", "", null, null, null, null);
	}

	@Command("guardar")
	@NotifyChange({ "seleccion", "lista" })
	public void guardarProyecto() {
		lista.add(seleccion);
		Clients.showNotification("Proyecto guardado en la BD.");
		for (Proyecto proyecto : lista) {
			System.out.println(proyecto.getTitulo());
		}
		nuevoProyecto();
	}

	public Proyecto getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Proyecto seleccion) {
		this.seleccion = seleccion;
	}

	public List<Proyecto> getListaProyectos() {
		return null;
	}

}// Fin de la clase
