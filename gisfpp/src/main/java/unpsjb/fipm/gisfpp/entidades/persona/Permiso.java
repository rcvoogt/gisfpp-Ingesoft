package unpsjb.fipm.gisfpp.entidades.persona;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.AssertTrue;

@Entity
@Table(name = "permisos")
public class Permiso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	private TRolUsuario rol;

	@Temporal(TemporalType.DATE)
	private Date desde;

	@Temporal(TemporalType.DATE)
	private Date hasta;

	public Permiso() {
		super();
	}

	public Permiso(TRolUsuario rol, Date desde, Date hasta) {
		super();
		this.rol = rol;
		this.desde = desde;
		this.hasta = hasta;
	}

	public Integer getId() {
		return id;
	}

	protected void setId(Integer id) {
		this.id = id;
	}

	public TRolUsuario getRol() {
		return rol;
	}

	public void setRol(TRolUsuario rol) {
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

	@AssertTrue(message = "La fecha \"Hasta\" debe ser posterior a la fecha \"Desde \".")
	private boolean isFechasValidas() {
		if (hasta.after(desde)) {
			return true;
		} else {
			return false;
		}
	}

}// Fin de la clase Permiso
