package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;
import unpsjb.fipm.gisfpp.entidades.proyecto.EEstadosIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.OfertaActividad;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.TipoProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServicioSubProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosProyecto;
import unpsjb.fipm.gisfpp.servicios.staff.IServiciosStaffFI;

public class MVDlgFiltrosOferta {

	private List<OfertaActividad> listSinFiltro;
	private Proyecto proyecto;
	private SubProyecto subProyecto;
	private OfertaActividad oferta;
	private EEstadosIsfpp estado;
	private TipoProyecto tipo;
	private PersonaFisica responsable;
	private IServicioSubProyecto srvSubProyecto;
	private IServiciosProyecto srvProyecto;
	private IServiciosStaffFI srvStaff;

	@Init
	public void init() {
		HashMap<String, Object> map = (HashMap<String, Object>) Executions.getCurrent().getArg();
		listSinFiltro = (List<OfertaActividad>) map.get("listSinFiltro");
		System.out.println(listSinFiltro);
		srvSubProyecto = (IServicioSubProyecto) SpringUtil.getBean("servSubProyecto");
		srvProyecto = (IServiciosProyecto) SpringUtil.getBean("servProyecto");
		srvStaff = (IServiciosStaffFI) SpringUtil.getBean("servStaffFI");
		recuperarArgUltFiltro();
	}

	@Command("filtrar")
	public void filtrar() {
		List<OfertaActividad> resultado = listSinFiltro.stream().filter(getPredicadoFiltro()).collect(Collectors.toList());
		HashMap<String, Object> prm = new HashMap<>();
		prm.put("listConFiltro", resultado);
		BindUtils.postGlobalCommand(null, null, "retornoDlgFiltrosOferta", prm);
		guardarArgUltFiltro();
		cerrar();
	}

	private Predicate<OfertaActividad> getPredicadoFiltro() {
		Predicate<OfertaActividad> filtro = Item -> true;
		/*if (codigo != null && !codigo.isEmpty()) {
			filtro = filtro.or(item -> item.getCodigo().toLowerCase().contains(codigo.toLowerCase()));
		}
		if (resolucion != null && !resolucion.isEmpty()) {
			filtro = filtro.or(item -> item.getResolucion().toLowerCase().contains(resolucion.toLowerCase()));
		}
		if (proyecto != null) {
			filtro = filtro.and(item -> item.getPerteneceA().getPerteneceA().equals(proyecto));
		}
		if (subProyecto != null) {
			filtro = filtro.and(item -> item.getPerteneceA().equals(subProyecto));
		}
		if (estado != null) {
			filtro = filtro.and(item -> item.getEstado().equals(estado));
		}*/
		if (tipo != null) {
			filtro = filtro.and(item -> item.getProyecto().getTipo().equals(tipo));
		}
		if (responsable != null) {
			filtro = filtro.and(item -> item.getProyecto().getResponsables().contains(responsable));
		}
		return filtro;
	}

	private void cerrar() {
		Window thisDlg = (Window) Path.getComponent("/dlgFiltroOferta");
		thisDlg.detach();
	}

	/**
	 * Mediante este método se guarda en la session web los criterios
	 * (argumentos )de busqueda establecidos en el último filtro aplicado al
	 * listado de Proyectos.
	 */
	private void guardarArgUltFiltro() {
		HashMap<String, Object> map = new HashMap<>();
		/*map.put("codigo", codigo);
		map.put("resolucion", resolucion);
		map.put("titulo", titulo);
		map.put("tipo", tipo);*/
		map.put("proyecto", proyecto);
		map.put("subProyecto", subProyecto);
		map.put("oferta", oferta);
		map.put("tipo", tipo);
		map.put("responsable", responsable);
		Sessions.getCurrent().setAttribute("argUltFiltrosOferta", map);
	}

	/**
	 * Recuperamos de la session web, los criterios (argumentos) si los hubiera,
	 * del último filtro aplicado al listado de Proyectos.
	 */
	@NotifyChange({ "subProyecto", "estado" })
	private void recuperarArgUltFiltro() {
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions.getCurrent()
				.getAttribute("argUltFiltrosOferta");
		if (map != null) {
			/*codigo = (String) map.get("codigo");
			resolucion = (String) map.get("resolucion");
			titulo = (String) map.get("titulo");*/
			proyecto = (Proyecto) map.get("proyecto");
			subProyecto = (SubProyecto) map.get("subProyecto");
			tipo = (TipoProyecto) map.get("tipo");
			responsable = (PersonaFisica) map.get("responsable");
		}
	}

	public List<EEstadosIsfpp> getEstados() {
		return Arrays.asList(EEstadosIsfpp.values());
	}
	
	public List<SubProyecto> getSubProyectos() {
		try {
			return srvSubProyecto.getListado();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Proyecto> getProyectos() {
		try {
			return srvProyecto.getListado();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*public String getCodigo() {
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
	}*/

	public SubProyecto getSubProyecto() {
		return subProyecto;
	}

	public void setSubProyecto(SubProyecto subProyecto) {
		this.subProyecto = subProyecto;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	/*public EEstadosIsfpp getEstado() {
		return estado;
	}

	public void setEstado(EEstadosIsfpp estado) {
		this.estado = estado;
	}*/
	
	public OfertaActividad getOferta() {
		return oferta;
	}

	public TipoProyecto getTipo() {
		return tipo;
	}

	public void setTipo(TipoProyecto tipo) {
		this.tipo = tipo;
	}

	public void setOferta(OfertaActividad oferta) {
		this.oferta = oferta;
	}
	

	public List<TipoProyecto> getTipos() {
		return Arrays.asList(TipoProyecto.values());
	}
	
	public List<PersonaFisica> getStaff() {
		try {
			return srvStaff.getListadoStaffPersonas();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public PersonaFisica getResponsable() {
		return responsable;
	}

	public void setResponsable(PersonaFisica responsable) {
		this.responsable = responsable;
	}
	
	

}// fin de la clase
