package unpsjb.fipm.gisfpp.entidades.proyecto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import unpsjb.fipm.gisfpp.entidades.persona.Persona;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;

@Entity
@Table(name = "proyecto", indexes={@Index(name="uk_codigo", columnList="codigo", unique=true), 
		@Index(name="uk_num_resolucion",columnList="num_resolucion", unique=true), 
			@Index(name="uk_titulo",columnList="titulo", unique=true)})
public class Proyecto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id 
	@Column(name = "idProyecto")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "codigo", length = 20)
	private String codigo;

	@Column(name = "num_resolucion", length = 20)
	private String resolucion;

	@Column(name = "titulo", nullable = false, length = 80)
	private String titulo;

	@Column(name = "descripcion", length = 500)
	private String descripcion;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo")
	private TipoProyecto tipo;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado")
	private EstadoProyecto estado;

	@Column(name = "fecha_inicio")
	@Temporal(TemporalType.DATE)
	private Date fecha_inicio;

	@Column(name = "fecha_fin")
	@Temporal(TemporalType.DATE)
	private Date fecha_fin;

	@Column(name = "detalle")
	@Lob
	private String detalle;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, mappedBy = "perteneceA")
	private Set<SubProyecto> subProyectos;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name="demandantes", joinColumns=@JoinColumn(name="proyectoId", foreignKey=@ForeignKey(name="fk_proyecto_demandante")), 
		inverseJoinColumns=@JoinColumn(name="personaId", foreignKey=@ForeignKey(name="fk_persona_demandante")))
	private Set <Persona> demandantes; 
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="proyecto")
	private Set<MiembroStaffProyecto> staff;

	protected Proyecto() {
		tipo=TipoProyecto.INTERNO;
		estado=EstadoProyecto.GENERADO;
		fecha_inicio = new Date();
		fecha_fin = new Date();
		subProyectos = new HashSet<SubProyecto>();
		demandantes = new HashSet<Persona>();
		staff = new HashSet<MiembroStaffProyecto>();
	}

	public Proyecto(String codigo, String resolucion, String titulo, String descripcion, TipoProyecto tipo,
			Date fecha_inicio, Date fecha_fin, String detalle, Set<SubProyecto> subProyectos, 
				Set<Persona> demandantes, Set<MiembroStaffProyecto> staff) {
		this.codigo = codigo;
		this.resolucion = resolucion;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.tipo = (tipo==null)?TipoProyecto.INTERNO:tipo;
		this.estado = EstadoProyecto.GENERADO;
		this.fecha_inicio = (fecha_inicio==null)?new Date():fecha_inicio;
		this.fecha_fin = (fecha_fin==null)?new Date():fecha_fin;
		this.detalle = detalle;
		this.subProyectos =(subProyectos==null)? new HashSet<>(): subProyectos;
		this.demandantes = (demandantes==null)? new HashSet<>(): demandantes;
		this.staff = (staff==null)? new HashSet<>(): staff;
	}

	public Integer getId() {
		return id;
	}

	protected void setId(Integer id) {
		this.id = id;
	}

	@Length(max = 20, message = "El \"Codigo\" del Proyecto no debe ser mayor a 20 caracteres.")
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Length(max = 20, message = "\"El Numero de Resolucion\" del Proyecto no debe ser mayor a 20 caracteres.")
	public String getResolucion() {
		return resolucion;
	}

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}

	@NotBlank(message = " Debe especificarle un \"Titulo\" al Proyecto.")
	@Length(max = 80, message = "El \"Titulo\" del Proyecto no debe ser mayor a 80 caracteres.")
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Length(max = 500, message = "\"La Descripcion\" del Proyecto no debe ser mayor a 500 caracteres")
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

	@NotNull(message="Debe especificarle una \"fecha de inicio\" al Proyecto.")
	public Date getFecha_inicio() {
		return fecha_inicio;
	}
	
	public void setFecha_inicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	@NotNull(message="Debe especificarle una \"fecha de finalizaci�n\" al Proyecto.")
	public Date getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(Date fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public EstadoProyecto getEstado() {
		return estado;
	}

	public void setEstado(EstadoProyecto estado) {
		this.estado = estado;
	}

	public Set<SubProyecto> getSubProyectos() {
		return subProyectos;
	}

	public void setSubProyectos(Set<SubProyecto> subProyectos) {
		this.subProyectos = subProyectos;
	}
	
	public Set<Persona> getDemandantes() {
		return demandantes;
	}

	public void setDemandantes(Set<Persona> demandantes) {
		this.demandantes = demandantes;
	}
	
	public Set<MiembroStaffProyecto> getStaff() {
		return staff;
	}

	public void setStaff(Set<MiembroStaffProyecto> staff) {
		this.staff = staff;
	}

	@AssertTrue(message = "La Fecha de finalizacion del proyecto debe ser posterior a la fecha de inicio.")
	private boolean isFechaFinValida() {
		if(fecha_inicio!=null && fecha_fin!=null){
			if (fecha_fin.after(fecha_inicio)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
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
		Proyecto other = (Proyecto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public List<PersonaFisica> getResponsables(){
		List<PersonaFisica> resultado = new ArrayList<PersonaFisica>();
		for (MiembroStaffProyecto miembro : staff) {
			if (miembro.getRol() == ERolStaffProyecto.RESPONSABLE_PROYECTO){
				resultado.add(miembro.getMiembro());
			}
		}
		return resultado;
	}
	
}// Fin de la clase Entity Proyecto
