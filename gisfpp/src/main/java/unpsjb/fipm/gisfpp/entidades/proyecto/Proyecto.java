package unpsjb.fipm.gisfpp.entidades.proyecto;

import java.sql.Blob;
import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "proyecto")
public class Proyecto {

	@Id
	@Column(name = "idProyecto")
	private Integer id;

	@Column(name = "codigo", length = 20, unique = true)
	private String codigo;

	@Column(name = "num_resolucion", length = 20, unique = true)
	private String resolucion;

	@Basic(optional = false)
	@Column(name = "titulo", nullable = false, length = 50, unique = true)
	private String titulo;

	@Column(name = "descripcion", length = 500)
	private String descripcion;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo")
	private TipoProyecto tipo;

	@Column(name = "fecha_inicio")
	private Date fecha_inicio;

	@Column(name = "fecha_fin")
	private Date fecha_fin;

	@Column(name = "detalle")
	private Blob detalle;

	protected Proyecto() {
		super();
	}

	public Proyecto(String codigo, String resolucion, String titulo, String descripcion, TipoProyecto tipo,
			Date fecha_inicio, Date fecha_fin, Blob detalle) {
		super();
		this.codigo = codigo;
		this.resolucion = resolucion;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.tipo = tipo;
		this.fecha_inicio = fecha_inicio;
		this.fecha_fin = fecha_fin;
		this.detalle = detalle;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public TipoProyecto getTipo() {
		return tipo;
	}

	public void setTipo(TipoProyecto tipo) {
		this.tipo = tipo;
	}

	public Date getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public Date getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(Date fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	public Blob getDetalle() {
		return detalle;
	}

	public void setDetalle(Blob detalle) {
		this.detalle = detalle;
	}

}// fin del Entity Proyecto
