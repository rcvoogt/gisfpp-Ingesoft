package unpsjb.fipm.gisfpp.entidades.persona;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="identificador")
public class Identificador implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	private TIdentificador tipo;

	@Column(length = 30)
	private String valor;

	public Identificador() {
		super();
	}

	public Identificador(TIdentificador tipo, String valor) {
		super();
		this.tipo = tipo;
		this.valor = valor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TIdentificador getTipo() {
		return tipo;
	}

	public void setTipo(TIdentificador tipo) {
		this.tipo = tipo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}// Fin de la entidad Identificador
