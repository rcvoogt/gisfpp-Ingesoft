package unpsjb.fipm.gisfpp.entidades.persona;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@DiscriminatorValue("PF")
public class PersonaFisica extends Persona {

	private static final long serialVersionUID = 1L;

	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="persona")
	private Usuario usuario;

	public PersonaFisica() {
		this.nombre="";
		this.usuario = new Usuario();
	}

	public PersonaFisica(String nombre) {
		this.nombre=(nombre==null)?"":nombre;
		this.usuario = new Usuario();
	}
	
	public PersonaFisica(String nombre, Usuario usuario){
		this.nombre=(nombre==null)?"":nombre;
		this.usuario = (usuario==null)? new Usuario(): usuario;
	}

	@Override
	@NotBlank(message = "Debe indicar \"Nombre y Apellido\" para la Persona.")
	@Length(max = 80, message = "El \"Nombre y Apellido\" de la Persona no debe ser mayor a 80 caracteres.")
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
	
	//Este metodo "get" se implementa solo con fines de
	//validacion de datos (mediante Hibernate Validator), en este caso para validar la existencia
	//de al menos 1 e-mail al crear o editar una Persona Fisica y
	//tambien validar el correcto formato del mismo.
	@NotBlank(message="Debe indicar al menos un \"e-mail\" para registrar esta Persona.")
	@Email(message="Formato de \"e-mail\" incorrecto.")
	public String getEmail(){
		for(DatoDeContacto contacto: getDatosDeContacto()){
			if (contacto.getTipo().equals(TDatosContacto.EMAIL)){
				return contacto.getValor();
			}
		}
		return null;
	}
	
	

}// Fin de la clase Entity PersonaFísica
