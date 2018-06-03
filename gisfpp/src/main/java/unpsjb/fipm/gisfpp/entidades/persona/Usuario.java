package unpsjb.fipm.gisfpp.entidades.persona;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocatoria;

@Entity
@Table(name="usuario", indexes=@Index(name="uk_nickname",columnList="nickname", unique=true))
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="personaId", nullable=false, foreignKey=@ForeignKey(name="fk_persona"))
	private PersonaFisica persona;

	@Column(nullable = false, length = 50)
	private String nickname;

	@Column(nullable = false, length = 50)
	private String password;

	private Boolean activo;

	public Usuario() {
		super();
		this.nickname="";
		this.password="";
		this.activo=false;
		this.persona=null;
	}

	public Usuario(PersonaFisica persona, String nickname, String password, Boolean activo) {
		super();
		this.persona=persona;
		this.nickname = (nickname==null)?"":nickname;
		this.password = (password==null)?"":password;
		this.activo = activo;
	}
	
	public Usuario(Integer id, String nickname) {
		this.id = id;
		this.nickname = nickname;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
	@NotBlank(message="Debe especificar un \"password\".")
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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}// Fin de la clase Usuario
