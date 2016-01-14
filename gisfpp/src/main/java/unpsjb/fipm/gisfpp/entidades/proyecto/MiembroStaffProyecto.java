package unpsjb.fipm.gisfpp.entidades.proyecto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	@JoinColumn(name="proyectoId")
	private Proyecto proyecto;
	
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name="personaId")
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

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

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
	
	
}
