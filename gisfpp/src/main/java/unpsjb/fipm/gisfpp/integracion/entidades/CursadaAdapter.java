package unpsjb.fipm.gisfpp.integracion.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="cursadaAdapter", schema ="gisfpp_integracion")
public class CursadaAdapter implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCursadaAdapter")
	private Integer id;
	
	@Column(name = "idCursadaGisfpp")
	private Integer idCursadaGisfpp;
	
	@Column(name = "codComision")
	private String codComision;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdCursadaGisfpp() {
		return idCursadaGisfpp;
	}

	public void setIdCursadaGisfpp(Integer idCursadaGisfpp) {
		this.idCursadaGisfpp = idCursadaGisfpp;
	}

	public String getCodComision() {
		return codComision;
	}

	public void setCodComision(String codComision) {
		this.codComision = codComision;
	}
	
	
	
	
	
}
