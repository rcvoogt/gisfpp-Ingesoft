package unpsjb.fipm.gisfpp.entidades.convocatoria;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import unpsjb.fipm.gisfpp.entidades.persona.DatoDeContacto;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.proyecto.ERespuestaConvocado;

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

	@javax.persistence.Transient
	private boolean aceptado = false;
	
	
	public Convocado(){
		
	}
	
	public Convocado(Convocatoria convocatoria, PersonaFisica persona) {
		this.convocatoria = convocatoria;
		this.persona = persona;
		this.respuesta = ERespuestaConvocado.SIN_RESPUESTA;
	}
	
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

	
	
	public void setConvocatoria(Convocatoria convocatoria) {
		this.convocatoria = convocatoria;
	}

	public void setRespuesta(ERespuestaConvocado respuesta) {
		this.respuesta = respuesta;
	}

	/*
	 * Como la entidad aun no fue persistida cuando se crea el convocado, no tiene ID, por lo tanto con el metodo standard retorna siempre el mismo valor. 
	 * Por lo tanto, para este caso se va a utilizar el hash de la persona, que debe estar persisitida en la base de datos con un id cargado
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.getPersona().getId();
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

	public boolean isAceptado() {
		return aceptado;
	}

	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	}
	
	@Override
	public String toString() {
		return this.getPersona().getNombre();
	}
	
	
	
	

	
	
}// fin de la clase
