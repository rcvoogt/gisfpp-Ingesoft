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
import javax.validation.constraints.NotNull;

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
	@JoinColumn(name="isfppId", nullable=false, foreignKey=@ForeignKey(name="fk_staffIsfpp_isfpp"))
	private Isfpp isfpp;
	
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name="personaId", nullable=false, foreignKey=@ForeignKey(name="fk_staffIsfpp_persona"))
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

	@NotNull(message="Miembro Staff Isfpp: La referencia a la Isfpp es NULL.")
	public Isfpp getIsfpp() {
		return isfpp;
	}

	public void setIsfpp(Isfpp isfpp) {
		this.isfpp = isfpp;
	}

	@NotNull(message="Miembro Staff Isfpp: La referencia a la Persona es NULL.")
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((miembro == null) ? 0 : miembro.getId());
		result = prime * result + ((rol == null) ? 0 : rol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MiembroStaffIsfpp)) {
			return false;
		}
		MiembroStaffIsfpp other = (MiembroStaffIsfpp) obj;
		if (miembro == null) {
			if (other.miembro != null) {
				return false;
			}
		} else if (!miembro.getId().equals(other.getMiembro().getId())) {
			return false;
		}
		if (rol != other.rol) {
			return false;
		}
		return true;
	}

		
		
}//fin de la clase.
