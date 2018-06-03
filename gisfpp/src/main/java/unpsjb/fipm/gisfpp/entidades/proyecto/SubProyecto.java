package unpsjb.fipm.gisfpp.entidades.proyecto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocable;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocado;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.convocatoria.TipoConvocatoria;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.util.MiembroExistenteException;

@Entity
@Table(name = "sub_proyecto")
public class SubProyecto implements Serializable, Convocable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "proyectoId", nullable = false, foreignKey=@ForeignKey(name="fk_proyecto_subproyecto"))
	private Proyecto perteneceA;
	
	@OneToMany(fetch=FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval=true, mappedBy="sub_proyecto")
	private List<Convocatoria> convocatorias;

	@Column(length = 80, name = "titulo", nullable = false)
	private String titulo;

	@Column(length = 500)
	private String descripcion;
	
	@Lob
	private String detalle;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, mappedBy = "perteneceA")
	private List<Isfpp> instanciasIsfpp;

	public SubProyecto() {
		super();
	}

	public SubProyecto(Proyecto proyecto, String titulo, String descripcion) {
		super();
		this.perteneceA = proyecto;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.instanciasIsfpp = new ArrayList<>();
	}

	
	
	public List<Convocatoria> getConvocatorias() {
		return convocatorias;
	}

	public void setConvocatorias(List<Convocatoria> convocatorias) {
		this.convocatorias = convocatorias;
	}
	
	
	public Integer getId() {
		return Id;
	}

	protected void setId(Integer id) {
		Id = id;
	}

	public Proyecto getPerteneceA() {
		return perteneceA;
	}

	public void setPerteneceA(Proyecto proyecto) {
		this.perteneceA = proyecto;
	}

	@NotBlank(message = "Debe especificarle un \"título\" al Sub-Proyecto.")
	@Length(max = 80, message = "El \"título\" del Sub-Proyecto no debe ser mayor a 80 caracteres.")
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Length(message = "Las \"descripcion\" del Sub-Proyecto no debe superar los 500 caracteres.")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Isfpp> getInstanciasIsfpp() {
		return instanciasIsfpp;
	}

	public void setInstanciasIsfpp(List<Isfpp> instanciasIsfpp) {
		this.instanciasIsfpp = instanciasIsfpp;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Id == null) ? 0 : Id.hashCode());
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
		SubProyecto other = (SubProyecto) obj;
		if (Id == null) {
			if (other.Id != null)
				return false;
		} else if (!Id.equals(other.Id))
			return false;
		return true;
	}

	@Override
	public String getTipoConvocatoria() throws Exception {
		return TipoConvocatoria.SUBPROYECTO.toString();
	}

	@Override
	public void setConvocados(Set<Convocado> nuevosPracticantes) throws MiembroExistenteException ,Exception {
		this.perteneceA.setConvocados(nuevosPracticantes);		
	}

	@Override
	public boolean isAsignador(PersonaFisica persona) throws Exception {
		return this.perteneceA.isAsignador(persona);
	}

	@Override
	public List<PersonaFisica> getMiembros() {
		return this.perteneceA.getMiembros();
	}
	
	
}// fin de la entidad SubProyecto
