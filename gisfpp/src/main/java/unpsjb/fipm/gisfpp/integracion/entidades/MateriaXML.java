package unpsjb.fipm.gisfpp.integracion.entidades;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
/**
 * Clase bean utilizada para parsear los datos desde un archivo xml
 * @author isaia
 *
 */
public class MateriaXML {
	@JacksonXmlProperty(localName = "nombre")
	private String nombre;
	@JacksonXmlProperty(localName = "nombre_reducido")
	private String nombreReducido;
	@JacksonXmlProperty(localName = "cod_materia")
	private String codigoMateria;
	
	public MateriaXML() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreReducido() {
		return nombreReducido;
	}

	public void setNombreReducido(String nombreReducido) {
		this.nombreReducido = nombreReducido;
	}

	public String getCodigoMateria() {
		return codigoMateria;
	}

	public void setCodigoMateria(String codigoMateria) {
		this.codigoMateria = codigoMateria;
	}

	@Override
	public String toString() {
		return "MateriaXML [nombre=" + nombre + ", nombreReducido=" + nombreReducido + ", codigoMateria="
				+ codigoMateria + "]";
	}
	
	

	
}
