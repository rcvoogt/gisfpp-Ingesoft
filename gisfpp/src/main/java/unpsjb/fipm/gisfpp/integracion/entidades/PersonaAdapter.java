package unpsjb.fipm.gisfpp.integracion.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name="personaAdapter")

public class PersonaAdapter implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPersonaAdapter")
	private Integer id;
	
	@Column(name = "IdPersonaGisfpp")
	private Integer idPersona;
	
	@Column(name = "legajo1")
	private String legajo1;
	
	@Column(name = "legajo2")
	private String legajo2;
	
	@Column(name = "nroInscripcion", unique=true)
	private String nroInscripcion;

	public String getNroInscripcion() {
		return nroInscripcion;
	}

	public void setNroInscripcion(String nroInscripcion) {
		this.nroInscripcion = nroInscripcion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLegajo1() {
		return legajo1;
	}

	public void setLegajo1(String legajo1) {
		this.legajo1 = legajo1;
	}

	public String getLegajo2() {
		return legajo2;
	}

	public void setLegajo2(String legajo2) {
		this.legajo2 = legajo2;
	}

	public Integer getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}
	
	
}
