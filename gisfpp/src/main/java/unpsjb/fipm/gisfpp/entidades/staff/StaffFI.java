package unpsjb.fipm.gisfpp.entidades.staff;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.Length;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;

@Entity
public class StaffFI implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(targetEntity = PersonaFisica.class, optional = false)
	private PersonaFisica miembro;

	@Enumerated(EnumType.STRING)
	private Rol rol;

	@Temporal(TemporalType.DATE)
	private Date desde;

	@Temporal(TemporalType.DATE)
	private Date hasta;

	@Column(length = 100)
	private String nota;

	public StaffFI() {
		super();
	}

	public StaffFI(PersonaFisica miembro, Rol rol, Date desde, Date hasta, String nota) {
		super();
		this.miembro = miembro;
		this.rol = rol;
		this.desde = desde;
		this.hasta = hasta;
		this.nota = nota;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PersonaFisica getMiembro() {
		return miembro;
	}

	public void setMiembro(PersonaFisica miembro) {
		this.miembro = miembro;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Date getDesde() {
		return desde;
	}

	public void setDesde(Date desde) {
		this.desde = desde;
	}

	public Date getHasta() {
		return hasta;
	}

	public void setHasta(Date hasta) {
		this.hasta = hasta;
	}

	@Length(max = 100, message = "El atributo \"Nota\" no debe ser mayor a 100 caracteres")
	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

}// Fin de la entidad StaffFI
