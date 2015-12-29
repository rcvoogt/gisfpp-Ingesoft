package unpsjb.fipm.gisfpp.entidades.proyecto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "isfpp")
public class Isfpp implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "titulo", nullable = false, length = 80)
	private String titulo;

	@Column(name = "objetivos", length = 500)
	private String objetivos;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_inicio")
	private Date inicio;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_fin")
	private Date fin;

	@Lob
	private String detalle;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "subProyectoId", nullable = false)
	private SubProyecto perteneceA;

	@Enumerated(EnumType.STRING)
	private EEstadosIsfpp estado;

	public Isfpp() {
		super();
	}

	public Isfpp(SubProyecto perteneceA, String titulo, String objetivos, Date inicio, Date fin, String detalle) {
		super();
		this.titulo = titulo;
		this.objetivos = objetivos;
		this.inicio = inicio;
		this.fin = fin;
		this.detalle = detalle;
		this.perteneceA = perteneceA;
		this.estado = EEstadosIsfpp.GENERADA;
	}

	public Integer getId() {
		return id;
	}

	protected void setId(Integer id) {
		this.id = id;
	}

	@NotBlank(message = "Debe especificarle un \"Título\" a la ISFPP.")
	@Length(max = 80, message = "El \"Título\" de la ISFPP no debe superar los 80 caracteres.")
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Length(max = 500, message = "El campo \"Objetivos\" de la ISFPP no debe superar los 500 caracteres.")
	public String getObjetivos() {
		return objetivos;
	}

	public void setObjetivos(String objetivos) {
		this.objetivos = objetivos;
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

	public SubProyecto getPerteneceA() {
		return perteneceA;
	}

	public void setPerteneceA(SubProyecto sp) {
		this.perteneceA = sp;
	}

	public EEstadosIsfpp getEstado() {
		return estado;
	}

	public void setEstado(EEstadosIsfpp estado) {
		this.estado = estado;
	}

	@AssertTrue(message = "La \"fecha fin\" debe ser posterior a la \"fecha inicio\".")
	private boolean isFechaFinValida1() {
		if (fin.after(inicio)) {
			return true;
		} else {
			return false;
		}
	}

	@AssertTrue(message = "La \"fecha de finalizacion\" de la ISFPP debe ser anterior a la fecha de finalizacion del Proyecto.")
	private boolean isFechaFinValida2() {
		if (fin.before(perteneceA.getPerteneceA().getFecha_fin())) {
			return true;
		} else {
			return false;
		}
	}

}// fin de la clase
