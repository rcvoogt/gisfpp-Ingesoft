package unpsjb.fipm.gisfpp.entidades.proyecto;

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
	private Integer num_proy;

	@Basic(optional = false)
	@Column(nullable = false, length = 50, unique = true)
	private String titulo;

	@Column(length = 500)
	private String descripcion;

	@Enumerated(EnumType.STRING)
	private TipoProyecto tipo;

	private Date fecha_inicio;

	private Date fecha_fin;

	protected Proyecto() {
		super();
	}

	public Proyecto(String titulo, String descripcion, TipoProyecto tipo, Date fecha_inicio, Date fecha_fin) {
		super();
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.tipo = tipo;
		this.fecha_inicio = fecha_inicio;
		this.fecha_fin = fecha_fin;
	}

	public Integer getNum_proy() {
		return num_proy;
	}

	protected void setNum_proy(Integer num_proyecto) {
		this.num_proy = num_proyecto;
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

}// fin del Entity Proyecto
