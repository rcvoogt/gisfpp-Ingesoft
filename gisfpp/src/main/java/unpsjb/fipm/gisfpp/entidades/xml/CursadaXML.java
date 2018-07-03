package unpsjb.fipm.gisfpp.entidades.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class CursadaXML {

	@JacksonXmlProperty(localName = "nombre")
	private String nombre;
	
	@JacksonXmlProperty(localName = "COMISION")
	private String codigoComision;
	
	@JacksonXmlProperty(localName = "cuatrimestre")
	private String cuatrimestre;
	
	@JacksonXmlProperty(localName = "anio")
	private Integer anio;
	
	@JacksonXmlProperty(localName = "Legajo")
	private String personaLegajo;
	
	@JacksonXmlProperty(localName = "materia")
	private String materia;
	
	public CursadaXML() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigoComision() {
		return codigoComision;
	}

	public void setCodigoComision(String codigoComision) {
		this.codigoComision = codigoComision;
	}

	public String getCuatrimestre() {
		return cuatrimestre;
	}

	public Integer getAnio() {
		return anio;
	}

	public String getPersonaLegajo() {
		return personaLegajo;
	}
	
	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	@Override
	public String toString() {
		return "CursadaXML [nombre=" + nombre + ", cuatrimestre=" + cuatrimestre + ", anio=" + anio + "]";
	}
	
	
}
