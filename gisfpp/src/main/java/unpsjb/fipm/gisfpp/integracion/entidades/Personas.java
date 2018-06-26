package unpsjb.fipm.gisfpp.integracion.entidades;

import java.util.Arrays;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
/**
 * Clase bean utilizada para parsear los datos desde un archivo xml
 * @author isaia
 *
 */
@JacksonXmlRootElement(localName = "Personas")
public class Personas {
	
    @JacksonXmlProperty(localName = "Persona")
    @JacksonXmlElementWrapper(useWrapping = false)
	private Persona[] personas;

	public Personas() {
		super();
	}

	public Personas(Persona[] personas) {
		super();
		this.personas = personas;
	}

	public Persona[] getPersonas() {
		return personas;
	}

	public void setPersonas(Persona[] personas) {
		this.personas = personas;
	}

	@Override
	public String toString() {
		return "Personas [personas=" + Arrays.toString(personas) + "]";
	}
	
}
