package unpsjb.fipm.gisfpp.entidades.persona;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@DiscriminatorValue("PF")
public class PersonaFisica extends Persona {

	private static final long serialVersionUID = 1L;

	@OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
	private Usuario usuario;

	public PersonaFisica() {
		super();
	}

	public PersonaFisica(String nombre) {
		super(nombre);
	}

	@Override
	@NotBlank(message = "Debe indicar \"Nombrey Apellido\" para la Persona.")
	@Length(max = 80, message = "El \"Nombre\" de la Persona no debe ser mayor a 80 caracteres.")
	public String getNombre() {
		return nombre;
	}

	@Override
	public String getDni() {
		return getValorIdentificador(TIdentificador.DNI);
	}

	@Override
	public String getCuil() {
		return getValorIdentificador(TIdentificador.CUIL);
	}

	// Es Persona Física, no tiene CUIT
	@Override
	public String getCuit() {
		return null;
	}

	@Override
	public String getMatricula() {
		return getValorIdentificador(TIdentificador.MATRICULA);
	}

	@Override
	public String getLegajo() {
		return getValorIdentificador(TIdentificador.LEGAJO);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}// Fin de la clase Entity PersonaFísica
