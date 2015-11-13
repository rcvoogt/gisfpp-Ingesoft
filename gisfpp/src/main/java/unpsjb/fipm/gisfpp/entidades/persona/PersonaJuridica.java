package unpsjb.fipm.gisfpp.entidades.persona;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.Length;

@Entity
public class PersonaJuridica extends Persona {

	@Column(length = 15)
	private String cuit;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "personaJuridicaId")
	private List<Contacto> contactos;

	public PersonaJuridica() {
		super();
		this.contactos = new ArrayList<Contacto>();
	}

	public PersonaJuridica(String nombre, String web, String telefono1, String telefono2, String email1, String email2,
			String cuit, List<Contacto> contactos) {
		super(nombre, web, telefono1, telefono2, email1, email2);
		this.cuit = cuit;
		this.contactos = contactos;
	}

	@Length(max = 15, message = "El \"CUIT\" de la Persona Jurídica no puede ser mayor a 15 caracteres.")
	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public List<Contacto> getContactos() {
		return contactos;
	}

	public void setContactos(List<Contacto> contactos) {
		this.contactos = contactos;
	}

	public void addContacto(Contacto contacto) {
		this.contactos.add(contacto);
	}

}
