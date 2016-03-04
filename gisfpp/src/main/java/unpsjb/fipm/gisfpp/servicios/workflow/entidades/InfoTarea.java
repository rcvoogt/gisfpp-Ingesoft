package unpsjb.fipm.gisfpp.servicios.workflow.entidades;

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
	private Date fecha_creacion;
	private String idformulario;
	private String idInstanciaProceso;
	private String nombreProceso;	
	
	public InfoTarea() {
	
	}

	public InfoTarea(String id, String nombre, String descripcion,
			Date fecha_vencimiento, int prioridad, String categoria,
			String asignado, String duenio, Date fecha_creacion,
			String idInstanciaProceso, String nombreProceso, String formulario) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha_vencimiento = fecha_vencimiento;
		this.prioridad = prioridad;
		this.categoria = categoria;
		this.asignado = asignado;
		this.duenio = duenio;
		this.fecha_creacion = fecha_creacion;
		this.idInstanciaProceso = idInstanciaProceso;
		this.nombreProceso = nombreProceso;
		this.idformulario = formulario;
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

	public Date getFecha_creacion() {
		return fecha_creacion;
	}

	public void setFecha_creacion(Date fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

	public String getNombreProceso() {
		return nombreProceso;
	}

	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}

	public String getIdFormulario() {
		return idformulario;
	}

	public void setIdFormulario(String idFormulario) {
		this.idformulario = idFormulario;
	}

	public String getIdInstanciaProceso() {
		return idInstanciaProceso;
	}

	public void setIdInstanciaProceso(String idInstanciaProceso) {
		this.idInstanciaProceso = idInstanciaProceso;
	}
	
				
}//fin de la clase
