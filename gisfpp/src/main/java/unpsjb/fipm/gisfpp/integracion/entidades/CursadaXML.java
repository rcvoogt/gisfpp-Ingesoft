package unpsjb.fipm.gisfpp.integracion.entidades;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class CursadaXML {

	@JacksonXmlProperty(localName = "nombre")
	private String nombre;
	
	@JacksonXmlProperty(localName = "cod_comision")
	private String codigoComision;
	
	
	
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
	
	
	
}
