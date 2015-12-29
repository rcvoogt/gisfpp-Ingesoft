package unpsjb.fipm.gisfpp.entidades.persona;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.validation.constraints.AssertTrue;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@DiscriminatorValue("PJ")
public class PersonaJuridica extends Persona {

	private static final long serialVersionUID = 1L;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "persona_contacto", joinColumns = @JoinColumn(name = "organizacionId") , inverseJoinColumns = @JoinColumn(name = "personaId") )
	private List<PersonaFisica> contactos;

	public PersonaJuridica() {
		super();
		contactos = new ArrayList<PersonaFisica>();
	}

	public PersonaJuridica(String razonSocial) {
		super(razonSocial);
		contactos = new ArrayList<PersonaFisica>();
	}

	@Override
	@NotBlank(message = "Debe indicar la \"Razon Social\" de la Organizacion.")
	@Length(max = 80, message = "La \"Razon Social\" de la Organizacion no debe ser mayor a 80 caracteres.")
	public String getNombre() {
		return nombre;
	}

	// Es Persona Jurï¿½dica, no posee DNI
	@Override
	public String getDni() {
		return null;
	}

	// Es Persona Jurï¿½dica, no posee CUIL
	@Override
	public String getCuil() {
		return null;
	}

	@Override
	public String getCuit() {
		return getValorIdentificador(TIdentificador.CUIT);
	}

	// Es Persona Jurï¿½dica, no posee Matricula
	@Override
	public String getMatricula() {
		return null;
	}

	// Es Persona Jurï¿½dica, no posee Legajo
	@Override
	public String getLegajo() {
		return null;
	}

	@AssertTrue(message = "El N° de Identificacion permitido para una Organizacion es el CUIT delinasolamente.")
	private boolean isIdentificacionesValidas() {
		if (getIdentificadores() == null) {
			return true;
		} else if (getIdentificadores().isEmpty()) {
			return true;
		} else if (getIdentificadores().size() == 1
				&& (getIdentificadores().get(0).getTipo().equals(TIdentificador.CUIT))) {
			return true;
		} else {
			return false;
		}
	}

	public List<PersonaFisica> getContactos() {
		return contactos;
	}

	public void setContactos(List<PersonaFisica> contactos) {
		this.contactos = contactos;
	}

}// Fin de la clase PersonaJuridica
