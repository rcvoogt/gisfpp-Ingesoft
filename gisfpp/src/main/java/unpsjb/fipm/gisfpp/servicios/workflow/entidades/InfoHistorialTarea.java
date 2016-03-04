package unpsjb.fipm.gisfpp.servicios.workflow.entidades;

import java.util.Date;

public class InfoHistorialTarea extends InfoTarea {

	/**
	 * Fecha de inicio de ejecucion de la tarea.
	 */
	private Date fecha_inicio;
	
	/**
	 * Fecha en la que se finalizo la ejecucion de la tarea.
	 */
	private Date fecha_conluida;
	
	/**
	 * Fecha en la que se reclamo la tarea.
	 */
	private Date fecha_reclamada;
	
	/**
	 * Tiempo (en milisegundos) transcurridos desde que se inicio la tarea hasta su conclusion.
	 */
	private Long duracion_total;
	
	/**
	 * Tiempo (en milisegundos) transcurridos desde que se reclamo la tarea hasta su fecha de concluida.
	 */
	private Long duracion_atendida;

	public InfoHistorialTarea() {
		super();
	}

	public InfoHistorialTarea(String id, String nombre, String descripcion,
			Date fecha_vencimiento, int prioridad, String categoria,
			String asignado, String duenio, Date fecha_creacion, String idInstanciaProceso,
			String nombreProceso, String formulario, Date fecha_inicio, Date fecha_conluida,
			Date fecha_reclamada, Long duracion_total, Long duracion_atendida) {
		super(id, nombre, descripcion, fecha_vencimiento, prioridad, categoria,
				asignado, duenio, fecha_creacion, idInstanciaProceso, nombreProceso, formulario);
		this.fecha_inicio = fecha_inicio;
		this.fecha_conluida = fecha_conluida;
		this.fecha_reclamada = fecha_reclamada;
		this.duracion_total = duracion_total;
		this.duracion_atendida = duracion_atendida;
	}

	public Date getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public Date getFecha_conluida() {
		return fecha_conluida;
	}

	public void setFecha_conluida(Date fecha_conluida) {
		this.fecha_conluida = fecha_conluida;
	}

	public Date getFecha_reclamada() {
		return fecha_reclamada;
	}

	public void setFecha_reclamada(Date fecha_reclamada) {
		this.fecha_reclamada = fecha_reclamada;
	}

	public Long getDuracion_total() {
		return duracion_total;
	}

	public void setDuracion_total(Long duracion_total) {
		this.duracion_total = duracion_total;
	}

	public Long getDuracion_atendida() {
		return duracion_atendida;
	}

	public void setDuracion_atendida(Long duracion_atendida) {
		this.duracion_atendida = duracion_atendida;
	}
	
		
}
