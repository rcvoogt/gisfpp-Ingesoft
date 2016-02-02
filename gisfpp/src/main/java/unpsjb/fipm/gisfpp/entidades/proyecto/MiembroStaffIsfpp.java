package unpsjb.fipm.gisfpp.entidades.proyecto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;

@Entity
@Table(name="staff_isfpp")
public class MiembroStaffIsfpp implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="idStaffIsfpp")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name="isfppId", nullable=false, foreignKey=@ForeignKey(name="fk_isfpp_1"))
	private Isfpp isfpp;
	
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name="personaId", nullable=false, foreignKey=@ForeignKey(name="fk_persona_1"))
	private PersonaFisica miembro;
	
	@Enumerated(EnumType.STRING)
	private ERolStaffIsfpp rol;

	public MiembroStaffIsfpp() {
		super();
	}

	public MiembroStaffIsfpp(Isfpp isfpp, PersonaFisica miembro, ERolStaffIsfpp rol) {
		super();
		this.isfpp = isfpp;
		this.miembro = miembro;
		this.rol = rol;
	}

	public Integer getId() {
		return id;
	}

	protected void setId(Integer id) {
		this.id = id;
	}

	public Isfpp getIsfpp() {
		return isfpp;
	}

	public void setIsfpp(Isfpp isfpp) {
		this.isfpp = isfpp;
	}

	public PersonaFisica getMiembro() {
		return miembro;
	}

	public void setMiembro(PersonaFisica miembro) {
		this.miembro = miembro;
	}

	public ERolStaffIsfpp getRol() {
		return rol;
	}

	public void setRol(ERolStaffIsfpp rol) {
		this.rol = rol;
	}
	
		
}//fin de la clase.
