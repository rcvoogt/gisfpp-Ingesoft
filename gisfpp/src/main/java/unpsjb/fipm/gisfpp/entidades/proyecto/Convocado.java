package unpsjb.fipm.gisfpp.entidades.proyecto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;

@Entity
@Table(name = "convocado")
public class Convocado implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	private ERespuestaConvocado respuesta;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "convocatoriaId", nullable = false, foreignKey=@ForeignKey(name="fk_convocatoria_convocado"))
	private Convocatoria convocatoria;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "personaId", nullable = false, foreignKey=@ForeignKey(name="fk_persona_convocado"))
	private PersonaFisica persona;

	public Convocado(Integer id, ERespuestaConvocado respuesta, Convocatoria convocatoria, PersonaFisica persona) {
		super();
		this.id = id;
		this.respuesta = respuesta;
		this.convocatoria = convocatoria;
		this.persona = persona;
	}

	public Integer getId() {
		return id;
	}

	public ERespuestaConvocado getRespuesta() {
		return respuesta;
	}

	public Convocatoria getConvocatoria() {
		return convocatoria;
	}

	public PersonaFisica getPersona() {
		return persona;
	}

	
	public void setRespuesta(ERespuestaConvocado respuesta) {
		this.respuesta = respuesta;
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
		Convocado other = (Convocado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
	
	

	
	
}// fin de la clase
