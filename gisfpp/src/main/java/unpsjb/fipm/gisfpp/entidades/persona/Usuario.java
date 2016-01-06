package unpsjb.fipm.gisfpp.entidades.persona;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "personaId", referencedColumnName = "idPersona")
	private PersonaFisica persona;

	@Column(unique = true, nullable = false, length = 50)
	private String nickname;

	@Column(nullable = false, length = 50)
	private String password;

	private Boolean activo;

	public Usuario() {
		super();
	}

	public Usuario(PersonaFisica persona, String nickname, String password, Boolean activo) {
		super();
		this.persona = persona;
		this.nickname = nickname;
		this.password = password;
		this.activo = activo;
	}

	public Integer getId() {
		return id;
	}

	protected void setId(Integer id) {
		this.id = id;
	}

	public PersonaFisica getPersona() {
		return persona;
	}

	public void setPersona(PersonaFisica persona) {
		this.persona = persona;
	}

	@NotBlank(message = "Debe especificar un \"Nombre de usuario\".")
	@Length(max = 50, message = "El \"Nombre de Usuario\" no puede ser mayor a 50 caracteres.")
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Length(max = 50, message = "La \"Contrase�a\" no puede ser mayor a 50 caracteres.")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

}// Fin de la clase Usuario
