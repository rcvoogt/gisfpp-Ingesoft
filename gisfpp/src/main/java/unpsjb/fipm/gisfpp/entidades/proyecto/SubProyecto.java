package unpsjb.fipm.gisfpp.entidades.proyecto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.AssertTrue;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "sub_proyecto")
public class SubProyecto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "proyectoId", nullable = false)
	private Proyecto proyecto;

	@Column(length = 80, name = "titulo", nullable = false)
	private String titulo;

	@Column(length = 500)
	private String descripcion;

	@Temporal(TemporalType.DATE)
	private Date inicio;

	@Temporal(TemporalType.DATE)
	private Date fin;

	@Column(name = "detalle")
	@Lob
	private String detalle;

	public SubProyecto() {
		super();
	}

	public SubProyecto(Proyecto proyecto, String titulo, String descripcion, Date inicio, Date fin, String detalle) {
		super();
		this.proyecto = proyecto;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.inicio = inicio;
		this.fin = fin;
		this.detalle = detalle;
	}

	public Integer getId() {
		return Id;
	}

	protected void setId(Integer id) {
		Id = id;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	@NotBlank(message = "Debe especificarle un \"título\" al Sub-Proyecto.")
	@Length(max = 80, message = "El \"título\" del Sub-Proyecto no debe ser mayor a 80 caracteres.")
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Length(message = "La \"descripción\" del Sub-Proyecto no debe superar los 500 caracteres.")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFin() {
		return fin;
	}

	public void setFin(Date fin) {
		this.fin = fin;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	@AssertTrue(message = "La \"fecha de finalización\" debe ser posterior a la \"fecha de inicio\" del Sub-Proyecto.")
	private boolean isFechaFinValida1() {
		if (fin.after(inicio)) {
			return true;
		} else {
			return false;
		}
	}

	@AssertTrue(message = "La \"fecha de finalizacion\" del Sub-Proyecto no puede ser posterior a la fecha de finalización del Proyecto al cual pertenece.")
	private boolean isFechaFinValida2() {
		if (fin.before(proyecto.getFecha_fin())) {
			return true;
		} else {
			return false;
		}
	}

}// fin de la entidad SubProyecto
