package unpsjb.fipm.gisfpp.entidades.persona;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPersona")
	private Integer id;

	@Column(name = "nombre", length = 50, nullable = false)
	private String nombre;

	@Column(name = "web", length = 100)
	private String web;

	@Column(length = 20)
	private String telefono1;

	@Column(length = 20)
	private String telefono2;

	@Column(length = 100)
	private String email1;

	@Column(length = 100)
	private String email2;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "personaId")
	private List<Domicilio> domicilios;

	public Persona() {
		super();
		this.domicilios = new ArrayList<Domicilio>();
	}

	public Persona(String nombre, String web, String telefono1, String telefono2, String email1, String email2) {
		super();
		this.nombre = nombre;
		this.web = web;
		this.telefono1 = telefono1;
		this.telefono2 = telefono2;
		this.email1 = email1;
		this.email2 = email2;
		this.domicilios = new ArrayList<Domicilio>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@NotBlank(message = "Debe especificar un \"Nombre\" para la Persona.")
	@Length(max = 50, message = "El \"Nombre\"  de la Persona no debe ser mayor a 50 caracteres.")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Length(max = 100, message = "El  link \"Web\"  de la Persona no debe ser mayor a 100 caracteres.")
	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	@Length(max = 20, message = "El \"N° de Telefono 1\" no debe ser mayor a 20 caracteres.")
	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	@Length(max = 20, message = "El \"N° de Telefono 2\" no debe ser mayor a 20 caracteres.")
	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	@Length(max = 100, message = "El \"E-Mail 1\" no debe ser mayor a 100 caracteres.")
	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	@Length(max = 100, message = "El \"E-Mail 2\" no debe ser mayor a 100 caracteres.")
	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	@Valid
	public List<Domicilio> getDomicilios() {
		return domicilios;
	}

	public void setDomicilios(List<Domicilio> domicilios) {
		this.domicilios = domicilios;
	}

	public void addDomicilio(Domicilio domicilio) {
		this.domicilios.add(domicilio);
	}

}// Fin de la clase Entity Persona
