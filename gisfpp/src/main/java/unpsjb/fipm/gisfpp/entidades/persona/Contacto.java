package unpsjb.fipm.gisfpp.entidades.persona;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Contacto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idContacto")
	private Integer id;

	@Column(name = "nombres", length = 50, nullable = false)
	private String nombres;

	@Column(name = "apellidos", length = 50, nullable = false)
	private String apellidos;

	@Column(name = "puesto", length = 30)
	private String puesto;

	@Column(name = "num_telef", length = 20)
	private String telefono;

	@Column(name = "email", length = 70)
	private String email;

	public Contacto() {
		super();
	}

	public Contacto(String nombres, String apellidos, String puesto, String telefono, String email) {
		super();
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.puesto = puesto;
		this.telefono = telefono;
		this.email = email;
	}

	@NotBlank(message = "Debe especifar un \"Nombre\" para el Contacto.")
	@Length(max = 50, message = "El \"Nombre\" no debe ser mayor a 50 caracteres.")
	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	@NotBlank(message = "Debe especifar un \"Apellido\" para el Contacto.")
	@Length(max = 50, message = "El \"Apellido\" no debe ser mayor a 50 caracteres.")
	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Email(message = "Formato de \"E-Mail\" inválido.")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}// Fin de la clase Entity Contacto
