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
@Table(name="staff_proyecto")
public class MiembroStaffProyecto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="idStaffProyecto")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name="proyectoId", foreignKey=@ForeignKey(name="fk_staffProyecto_proyecto"))
	private Proyecto proyecto;
	
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name="personaId", foreignKey=@ForeignKey(name="fk_staffProyecto_persona"))
	private PersonaFisica miembro;
	
	@Enumerated(EnumType.STRING)
	private ERolStaffProyecto rol;

	public MiembroStaffProyecto() {
		super();
	}

	public MiembroStaffProyecto(Proyecto proyecto, PersonaFisica miembro, ERolStaffProyecto rol) {
		super();
		this.proyecto = proyecto;
		this.miembro = miembro;
		this.rol = rol;
	}

	public Integer getId() {
		return id;
	}

	protected void setId(Integer id) {
		this.id = id;
	}

	@NotNull(message="Miembro Staff de Proyecto: La referencia a Proyecto es NULL.")
	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	@NotNull(message="Miembro Staff de Proyecto: La referencia a Persona es NULL")
	public PersonaFisica getMiembro() {
		return miembro;
	}

	public void setMiembro(PersonaFisica miembro) {
		this.miembro = miembro;
	}

	public ERolStaffProyecto getRol() {
		return rol;
	}

	public void setRol(ERolStaffProyecto rol) {
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
		if (!(obj instanceof MiembroStaffProyecto)) {
			return false;
		}
		MiembroStaffProyecto other = (MiembroStaffProyecto) obj;
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

		
		
}//fin de la clase
