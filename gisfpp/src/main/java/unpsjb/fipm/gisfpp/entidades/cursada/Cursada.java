package unpsjb.fipm.gisfpp.entidades.cursada;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import unpsjb.fipm.gisfpp.entidades.persona.Persona;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "cursada")
public class Cursada {

	@Id
	@Column(name = "idCursada")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCursada;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "cursada_alumno",
	joinColumns = @JoinColumn(name = "idCursada", referencedColumnName = "idCursada"),
	inverseJoinColumns = @JoinColumn(name = "idAlumno", referencedColumnName = "idPersona"))
	private Collection<Persona> alumnos;	

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "cursada_docente",
	joinColumns = @JoinColumn(name = "idCursada", referencedColumnName = "idCursada"),
	inverseJoinColumns = @JoinColumn(name = "idDocente", referencedColumnName = "idPersona"))
	private Collection<Persona> docentes;	

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "idMateria")
	private Materia materia;

	@Column(name = "anio")
	private Integer anio; 
	
	@Column(name = "cuatrimestre")
	private String cuatrimestre;

	public Integer getIdCursada() {
		return idCursada;
	}

	public void setIdCursada(Integer idCursada) {
		this.idCursada = idCursada;
	}

	public Collection<Persona> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(Collection<Persona> alumnos) {
		this.alumnos = alumnos;
	}

	public Collection<Persona> getDocentes() {
		return docentes;
	}

	public void setDocentes(Collection<Persona> docentes) {
		this.docentes = docentes;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public String getCuatrimestre() {
		return cuatrimestre;
	}

	public void setCuatrimestre(String cuatrimestre) {
		this.cuatrimestre = cuatrimestre;
	}
	
}
