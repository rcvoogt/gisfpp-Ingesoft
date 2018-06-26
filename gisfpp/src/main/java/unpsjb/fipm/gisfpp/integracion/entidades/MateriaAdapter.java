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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name="materiaAdapter")

public class MateriaAdapter implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idMateriaAdapter")
	private Integer id;
	
	@Column(name = "idMateriaGisfpp")
	private Integer idMateria;
	
	@Column(name = "materia")
	private String materia;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdMateria() {
		return idMateria;
	}

	public void setIdMateria(Integer idMateria) {
		this.idMateria = idMateria;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	@Override
	public String toString() {
		return "MateriaAdapter [id=" + id + ", idMateria=" + idMateria + ", materia=" + materia + "]";
	}

		
}
