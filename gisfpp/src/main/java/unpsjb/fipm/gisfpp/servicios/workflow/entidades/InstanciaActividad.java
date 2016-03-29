package unpsjb.fipm.gisfpp.servicios.workflow.entidades;

import java.util.Date;

public class InstanciaActividad {
	
	private String idActividad;
	private String idInstanciaProceso;
	private String nombre;
	private String tipo;
	private String idTarea;
	private String asignado;
	private Date inicia;
	private Date finaliza;
	
	public InstanciaActividad() {
		super();
	}

	public InstanciaActividad( String idActividad, String idInstanciaProceso, 
				String nombre, String tipo,	String idTarea, String asignado, Date inicia, Date finaliza) {
		super();
		this.idActividad = idActividad;
		this.idInstanciaProceso = idInstanciaProceso;
		this.nombre = nombre;
		this.tipo = tipo;
		this.idTarea = idTarea;
		this.asignado = asignado;
		this.inicia = inicia;
		this.finaliza = finaliza;
	}

	public String getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(String idActividad) {
		this.idActividad = idActividad;
	}

	public String getIdInstanciaProceso() {
		return idInstanciaProceso;
	}

	public void setIdInstanciaProceso(String idInstanciaProceso) {
		this.idInstanciaProceso = idInstanciaProceso;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getIdTarea() {
		return idTarea;
	}

	public void setIdTarea(String idTarea) {
		this.idTarea = idTarea;
	}

	public String getAsignado() {
		return asignado;
	}

	public void setAsignado(String asignado) {
		this.asignado = asignado;
	}

	public Date getInicia() {
		return inicia;
	}

	public void setInicia(Date inicia) {
		this.inicia = inicia;
	}

	public Date getFinaliza() {
		return finaliza;
	}

	public void setFinaliza(Date finaliza) {
		this.finaliza = finaliza;
	}
	
	public long getDuracion(){
		if (inicia!=null && finaliza!=null) {
			return (finaliza.getTime() - inicia.getTime());
		}
		return 0;
	}
	
}
