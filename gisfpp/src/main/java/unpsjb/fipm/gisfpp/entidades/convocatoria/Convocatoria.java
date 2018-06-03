package unpsjb.fipm.gisfpp.entidades.convocatoria;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.AssertTrue;

import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;

@Entity
@Table(name = "convocatoria")
public class Convocatoria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_creacion")
	private Date fechaCreacion;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_vencimiento")
	private Date fechaVencimiento;

	@Lob
	private String mensaje;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "isfppId", nullable = true, foreignKey = @ForeignKey(name = "fk_isfpp_convocatoria"))
	private Isfpp isfpp;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "usuarioId", nullable = true, foreignKey = @ForeignKey(name = "fk_usuario_convocatoria"))
	private Usuario usuarioOriginante;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "subproyectoId", nullable = true, foreignKey = @ForeignKey(name = "fk_subproyecto_convocatoria"))
	private SubProyecto sub_proyecto;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "proyectoId", nullable = true, foreignKey = @ForeignKey(name = "fk_proyecto_convocatoria"))
	private Proyecto proyecto;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "convocatoria")
	private Set<Convocado> convocados;

	public Convocatoria() {
		fechaCreacion = new Date();
		fechaVencimiento = new Date();
		mensaje = "";
		convocados = new HashSet<Convocado>();
	}

	public Convocatoria(Date creacion, Date vencimiento, String detalle, Convocable convocable, Usuario usuario) {

		super();

		this.usuarioOriginante = usuario;
		this.fechaCreacion = (creacion == null) ? new Date() : creacion;
		this.fechaVencimiento = (vencimiento == null) ? new Date() : vencimiento;
		convocados = new HashSet<Convocado>();

		if (convocable instanceof Isfpp) {
			this.isfpp = (Isfpp) convocable;
		}
		if (convocable instanceof Proyecto) {
			this.proyecto = (Proyecto) convocable;
		}
		if (convocable instanceof SubProyecto) {
			this.sub_proyecto = (SubProyecto) convocable;
		}

	}

	public Integer getId() {
		return id;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Set<Convocado> getConvocados() {
		return convocados;
	}

	public void agregarConvocado(Convocado convocado) {
		if (convocado != null) {
			convocados.add(convocado);

		}
	}

	public void quitarConvocado(Convocado convocado) {
		if (convocado != null) {
			convocados.remove(convocado);
		}
	}

	public Convocable getConvocable() {
		if (this.isfpp != null)
			return isfpp;
		if (this.proyecto != null)
			return proyecto;
		if (this.sub_proyecto != null)
			return sub_proyecto;
		return null;
	}

	// public void setConvocable(Convocable convocable) {
	// this.convocable = convocable;
	// }

	public Isfpp getIsfpp() {
		return isfpp;
	}

	public Usuario getUsuarioOriginante() {
		return usuarioOriginante;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setUsuarioOriginante(Usuario usuarioOriginante) {
		this.usuarioOriginante = usuarioOriginante;
	}

	public SubProyecto getSub_proyecto() {
		return sub_proyecto;
	}

	@AssertTrue(message = "La \"fecha de vencimiento\" debe ser posterior a la \"fecha creacion\".")
	private boolean isFechaVencimientoValida() {
		if (fechaVencimiento.after(fechaCreacion)) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean isVigente(Date fechaComparacion) {
		if (fechaComparacion.before(fechaVencimiento)) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean isPorVencer() {
		Date hoy = new Date();
		// hoy - 5 dias
		long ltime = fechaVencimiento.getTime() - 5 * 24 * 60 * 60 * 1000;
		Date porVencer = new Date(ltime);
		if (hoy.before(porVencer)) {
			return false;
		} else {
			return true;
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Convocatoria other = (Convocatoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}// fin de la clase
