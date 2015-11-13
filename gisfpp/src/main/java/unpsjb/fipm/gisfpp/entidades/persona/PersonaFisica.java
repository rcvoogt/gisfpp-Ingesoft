package unpsjb.fipm.gisfpp.entidades.persona;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
public class PersonaFisica extends Persona {

	@Column(length = 50, nullable = false)
	private String apellidos;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_nacimiento")
	private Date nacimiento;

	@Column(length = 15)
	private String dni;

	@Column(length = 15)
	private String cuil;

	public PersonaFisica() {
		super();
	}

	public PersonaFisica(String nombre, String web, String telefono1, String telefono2, String email1, String email2,
			String apellidos, Date nacimiento, String dni, String cuil) {
		super(nombre, web, telefono1, telefono2, email1, email2);
		this.apellidos = apellidos;
		this.nacimiento = nacimiento;
		this.dni = dni;
		this.cuil = cuil;
	}

	@NotBlank(message = "Se debe indicar un \"Apellido\" para la Persona.")
	@Length(max = 50, message = "El \"Apellido\" de la Persona no debe ser mayor a 50 caracteres.")
	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Date getNacimiento() {
		return nacimiento;
	}

	public void setNacimiento(Date nacimiento) {
		this.nacimiento = nacimiento;
	}

	@Length(max = 15, message = "El \"DNI\" no debe ser mayor a 15 caracteres")
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	@Length(max = 15, message = "El \"CUIL		\" no debe ser mayor a 15 caracteres")
	public String getCuil() {
		return cuil;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}

}// Fin de la clase Entity PersonaFísica
