package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.proyecto.EstadoProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.TipoProyecto;

public class MVDlgFiltrosProyecto {

	private List<Proyecto> listSinFiltro;
	private String codigo;
	private String resolucion;
	private String titulo;
	private TipoProyecto tipo;
	private EstadoProyecto estado;

	@Init
	public void init() {
		HashMap<String, Object> map = (HashMap<String, Object>) Executions.getCurrent().getArg();
		listSinFiltro = (List<Proyecto>) map.get("listSinFiltro");
	}

	@Command("filtrar")
	public void filtrar() {
		List<Proyecto> resultado = listSinFiltro.stream().filter(getPredicadoFiltro()).collect(Collectors.toList());
		HashMap<String, Object> prm = new HashMap<>();
		prm.put("listConFiltro", resultado);
		BindUtils.postGlobalCommand(null, null, "retornoDlgFiltroProyecto", prm);
		cerrar();
	}

	private Predicate<Proyecto> getPredicadoFiltro() {
		Predicate<Proyecto> filtro = Item -> false;
		if (codigo != null && !codigo.isEmpty()) {
			filtro = filtro.or(item -> item.getCodigo().toLowerCase().contains(codigo.toLowerCase()));
		}
		if (resolucion != null && !resolucion.isEmpty()) {
			filtro = filtro.or(item -> item.getResolucion().toLowerCase().contains(resolucion.toLowerCase()));
		}
		if (titulo != null && !titulo.isEmpty()) {
			filtro = filtro.or(item -> item.getTitulo().toLowerCase().contains(titulo.toLowerCase()));
		}
		if (tipo != null) {
			filtro = filtro.or(item -> item.getTipo().equals(tipo));
		}
		if (estado != null) {
			filtro = filtro.or(item -> item.getEstado().equals(estado));
		}
		return filtro;
	}

	private void cerrar() {
		Window thisDlg = (Window) Path.getComponent("/dlgFiltroProyecto");
		thisDlg.detach();
	}

	public List<TipoProyecto> getTipos() {
		return Arrays.asList(TipoProyecto.values());
	}

	public List<EstadoProyecto> getEstados() {
		return Arrays.asList(EstadoProyecto.values());
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getResolucion() {
		return resolucion;
	}

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public TipoProyecto getTipo() {
		return tipo;
	}

	public void setTipo(TipoProyecto tipo) {
		this.tipo = tipo;
	}

	public EstadoProyecto getEstado() {
		return estado;
	}

	public void setEstado(EstadoProyecto estado) {
		this.estado = estado;
	}

}// fin de la clase
