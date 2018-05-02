package unpsjb.fipm.gisfpp.entidades.workflow;

import java.util.Date;

public class InfoTarea {

	private String id;
	private String nombre;
	private String descripcion;
	private Date fecha_vencimiento;
	private int prioridad;
	private String categoria;
	private String asignado;
	private String duenio;
	private String idFormulario;
	private String idInstanciaProceso;
	private String nombreProceso;
	private EstadosTarea estado;
		
	/**
	 * Fecha de inicio de ejecucion de la tarea.
	 */
	protected Date fecha_inicio;
	/**
	 * Fecha en la que se finalizo la ejecucion de la tarea.
	 */
	protected Date fecha_concluida;
	/**
	 * Fecha en la que se reclamo la tarea.
	 */
	protected Date fecha_reclamada;	
	
	public InfoTarea() {
	
	}

	public InfoTarea(String id, String nombre, String descripcion,
			String idInstanciaProceso) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.idInstanciaProceso = idInstanciaProceso;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha_vencimiento() {
		return fecha_vencimiento;
	}

	public void setFecha_vencimiento(Date fecha_vencimiento) {
		this.fecha_vencimiento = fecha_vencimiento;
	}

	public int getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(int prioridad) {
		this.prioridad = prioridad;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getAsignado() {
		return asignado;
	}

	public void setAsignado(String asignado) {
		this.asignado = asignado;
	}

	public String getDuenio() {
		return duenio;
	}

	public void setDuenio(String duenio) {
		this.duenio = duenio;
	}

	public String getNombreProceso() {
		return nombreProceso;
	}

	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}

	public String getIdInstanciaProceso() {
		return idInstanciaProceso;
	}

	public void setIdInstanciaProceso(String idInstanciaProceso) {
		this.idInstanciaProceso = idInstanciaProceso;
	}

	public String getIdFormulario() {
		return idFormulario;
	}

	public void setIdFormulario(String idformulario) {
		this.idFormulario = idformulario;
	}

	public EstadosTarea getEstado() {
		return estado;
	}

	public void setEstado(EstadosTarea estado) {
		this.estado = estado;
	}

	public Date getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public Date getFecha_concluida() {
		return fecha_concluida;
	}

	public void setFecha_concluida(Date fecha_concluida) {
		this.fecha_concluida = fecha_concluida;
	}

	public Date getFecha_reclamada() {
		return fecha_reclamada;
	}

	public void setFecha_reclamada(Date fecha_reclamada) {
		this.fecha_reclamada = fecha_reclamada;
	}
	
	public long getDuracionTotal(){
		if (fecha_inicio!=null && fecha_concluida!=null) {
			return (fecha_concluida.getTime() - fecha_inicio.getTime());
		}
		return 0;
	}
	
	public long getDuracionRealizacion(){
		if (fecha_reclamada != null && fecha_concluida != null) {
			return (fecha_concluida.getTime() - fecha_reclamada.getTime());
		}
		return 0;
	}

	
	
}//fin de la clase
