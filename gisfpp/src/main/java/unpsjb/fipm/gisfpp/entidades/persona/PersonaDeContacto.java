package unpsjb.fipm.gisfpp.entidades.persona;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;

@Entity
@Table(name = "persona_contacto")
public class PersonaDeContacto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne(cascade = CascadeType.ALL)
	private PersonaJuridica organizacion;

	@OneToOne(cascade = CascadeType.ALL)
	private PersonaFisica persona;

	public PersonaDeContacto() {
		super();
	}

	public PersonaDeContacto(PersonaJuridica organizacion, PersonaFisica persona) {
		super();
		this.organizacion = organizacion;
		this.persona = persona;
	}

	public Integer getId() {
		return id;
	}

	protected void setId(Integer id) {
		this.id = id;
	}

	public PersonaJuridica getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(PersonaJuridica organizacion) {
		this.organizacion = organizacion;
	}

	public PersonaFisica getPersona() {
		return persona;
	}

	public void setPersona(PersonaFisica persona) {
		this.persona = persona;
	}

	@AssertTrue(message = "Debe especificar tanto una Organizacion como una Persona para establecer la relación \"Persona de Contacto\"")
	private boolean isRelacionValida() {
		if ((organizacion != null) && (persona != null)) {
			return true;
		} else {
			return false;
		}
	}

}// fin de la clase
