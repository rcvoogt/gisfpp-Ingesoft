package unpsjb.fipm.gisfpp.entidades.persona;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
public abstract class Persona implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPersona")
	private Integer id;

	// Para Persona Física corresponde el Nombre y Apellidos, para Persona
	// Juridica corresponde la Razón Social
	@Column(name = "nombre", length = 80, nullable = false)
	protected String nombre;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "personaId")
	private List<Domicilio> domicilios;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "personaId")
	private List<DatoDeContacto> datosDeContacto;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "personaId")
	private List<Identificador> identificadores;

	public Persona() {
		super();
		this.domicilios = new ArrayList<Domicilio>();
		this.datosDeContacto = new ArrayList<>();
		this.identificadores = new ArrayList<>();
	}

	public Persona(String nombre) {
		super();
		this.nombre = nombre;
		this.domicilios = new ArrayList<Domicilio>();
		this.datosDeContacto = new ArrayList<>();
		this.identificadores = new ArrayList<>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public abstract String getNombre();

	public void setNombre(String nombre) {
		this.nombre = nombre;

	}

	@Valid
	public List<Domicilio> getDomicilios() {
		return domicilios;
	}

	public void setDomicilios(List<Domicilio> domicilios) {
		this.domicilios = domicilios;
	}

	@Valid
	public List<DatoDeContacto> getDatosDeContacto() {
		return datosDeContacto;
	}

	public void setDatosDeContacto(List<DatoDeContacto> datosDeContacto) {
		this.datosDeContacto = datosDeContacto;
	}

	@Valid
	public List<Identificador> getIdentificadores() {
		return identificadores;
	}

	protected void setIdentificadores(List<Identificador> identificadores) {
		this.identificadores = identificadores;
	}

	public String getValorIdentificador(TIdentificador tipo) {
		for (Identificador identificador : identificadores) {
			if (identificador.getTipo().equals(tipo)) {
				return identificador.getValor();
			}
		}
		return null;
	}

	public abstract String getDni();

	public abstract String getCuil();

	public abstract String getCuit();

	public abstract String getMatricula();

	public abstract String getLegajo();

	public void agregarIdentificador(Identificador identificador) throws ConstraintViolationException {
		for (Identificador iden : identificadores) {
			if (iden.getTipo().equals(identificador.getTipo())) {
				throw new ConstraintViolationException("Error: Tipo de identificador duplicado.", null);
			}
		}
		identificadores.add(identificador);
	}

	public void removerIdentificador(Identificador identificador) {
		identificadores.remove(identificador);
	}

}// Fin de la clase Entity Persona
