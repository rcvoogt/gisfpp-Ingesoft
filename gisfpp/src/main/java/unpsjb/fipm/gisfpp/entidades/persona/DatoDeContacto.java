package unpsjb.fipm.gisfpp.entidades.persona;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "datos_contacto")
public class DatoDeContacto  implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	private TDatosContacto tipo;

	@Column(name = "valor", length = 100)
	private String valor;

	public DatoDeContacto() {
		super();
	}

	public DatoDeContacto(TDatosContacto tipo, String valor) {
		super();
		this.tipo = tipo;
		this.valor = valor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TDatosContacto getTipo() {
		return tipo;
	}

	public void setTipo(TDatosContacto tipo) {
		this.tipo = tipo;
	}

	@NotBlank(message="Debe especificar un \"Valor\" para Dato de Contacto.")
	@Length(max=100, message="El \"Valor\" para Dato de Contacto no puede ser mayor a 100 caracteres.")
	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}// Fin de la entidad "DatoContacto"
