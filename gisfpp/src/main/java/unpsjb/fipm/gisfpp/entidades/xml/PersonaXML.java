package unpsjb.fipm.gisfpp.entidades.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
/**
 * Clase bean utilizada para parsear los datos desde un archivo xml
 * @author isaia
 *
 */
public class PersonaXML {

    @JacksonXmlProperty(localName = "legajo")
	private String legajo;
    @JacksonXmlProperty(localName = "nombre")
	private String nombre;
    @JacksonXmlProperty(localName = "apellido")
	private String apellido;
    @JacksonXmlProperty(localName = "rol")
	private String rol;
    @JacksonXmlProperty(localName = "e_mail")
	private String e_mail;
    @JacksonXmlProperty(localName = "dni")
	private String dni;
	public String getLegajo() {
		return legajo;
	}
	public void setLegajo(String legajo) {
		this.legajo = legajo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public String getE_mail() {
		return e_mail;
	}
	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	@Override
	public String toString() {
		return "Persona [legajo=" + legajo + ", nombre=" + nombre + ", apellido=" + apellido + ", rol=" + rol
				+ ", e_mail=" + e_mail + ", dni=" + dni + "]";
	}
	public String getNombreCompleto() {
		return getNombre() + " " + getApellido();
	}
	
	
	
	
}
