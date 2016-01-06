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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.AssertTrue;

import org.hibernate.validator.constraints.Length;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;

@Entity
@Table(name = "staff_fi")
public class StaffFI implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(targetEntity = PersonaFisica.class, optional = false)
	@JoinColumn(name = "personaId")
	private PersonaFisica miembro;

	@Enumerated(EnumType.STRING)
	private ECargosStaffFi rol;

	@Temporal(TemporalType.DATE)
	private Date desde;

	@Temporal(TemporalType.DATE)
	private Date hasta;

	@Column(length = 100)
	private String nota;

	public StaffFI() {
		super();
	}

	public StaffFI(PersonaFisica miembro, ECargosStaffFi rol, Date desde, Date hasta, String nota) {
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

	public ECargosStaffFi getRol() {
		return rol;
	}

	public void setRol(ECargosStaffFi rol) {
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
	
	@AssertTrue(message="La fecha \"Hasta\" debe ser posterior a la fecha \"Desde\".")
	private boolean isFechaValida(){
		if (hasta.after(desde)){
			return true;
		}else{
			return false;
		}
	}

}// Fin de la entidad StaffFI
