package unpsjb.fipm.gisfpp.entidades.cursada;

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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name="materia")
public class Materia implements Serializable{

	@Id
	@Column(name = "idMateria")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idMateria;
	
	@Column(name = "codigoMateria")
	private String codigoMateria;
	
	@Column(name = "nombre")
	private String nombre;

	public Integer getId() {
		return idMateria;
	}

	public void setId(Integer id) {
		this.idMateria = id;
	}

	public String getCodigoMateria() {
		return codigoMateria;
	}

	public void setCodigoMateria(String codigoMateria) {
		this.codigoMateria = codigoMateria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
	
}
