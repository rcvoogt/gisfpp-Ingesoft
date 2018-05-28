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
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
@Table(name="persona")
public abstract class Persona implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPersona")
	private Integer id;

	// Para Persona Fisica corresponde el Nombre y Apellidos, para Persona
	// Juridica corresponde la Razon Social
	@Column(name = "nombre", length = 80, nullable = false)
	protected String nombre;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "personaId", foreignKey=@ForeignKey(name="fk_persona_domicilio"))
	private List<Domicilio> domicilios;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "personaId", foreignKey=@ForeignKey(name="fk_persona_dato_contacto"))
	protected List<DatoDeContacto> datosDeContacto;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "personaId", foreignKey=@ForeignKey(name="fk_persona_identificador"))
	private List<Identificador> identificadores;

	public Persona() {
		super();
		this.domicilios = new ArrayList<Domicilio>();
		this.datosDeContacto = new ArrayList<DatoDeContacto>();
		this.identificadores = new ArrayList<Identificador>();
	}

	public Persona(String nombre) {
		super();
		this.nombre = nombre;
		this.domicilios = new ArrayList<Domicilio>();
		this.datosDeContacto = new ArrayList<DatoDeContacto>();
		this.identificadores = new ArrayList<Identificador>();
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

	protected void setDomicilios(List<Domicilio> domicilios) {
		this.domicilios = domicilios;
	}
	
	public void addDomicilio (Domicilio domicilio){
		if(domicilio!=null){
			domicilios.add(domicilio);
		}
	}
	
	public void removerDomicilio(Domicilio domicilio){
		if(domicilio!=null){
			domicilios.remove(domicilio);
		}
	}

	@Valid
	public List<DatoDeContacto> getDatosDeContacto() {
		return datosDeContacto;
	}

	protected void setDatosDeContacto(List<DatoDeContacto> datosDeContacto) {
		this.datosDeContacto = datosDeContacto;
	}
	
	@Valid
	public List<Identificador> getIdentificadores() {
		return identificadores;
	}

	protected void setIdentificadores(List<Identificador> identificadores) {
		this.identificadores = identificadores;
	}

	public void addIdentificador(Identificador identificador) throws ConstraintViolationException {
		if (identificador!=null) {
			for (Identificador iden : identificadores) {
				if (iden.getTipo().equals(identificador.getTipo())) {
					throw new ConstraintViolationException("Error: Tipo de identificador duplicado.", null);
				}
			}
			identificadores.add(identificador);
		}
	}
	
	public void removerIdentificador(Identificador identificador) {
		if (identificador!=null) {
			identificadores.remove(identificador);
		}
	}
	
	protected String getValorIdentificador(TIdentificador tipo) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	

}// Fin de la clase Entity Persona
