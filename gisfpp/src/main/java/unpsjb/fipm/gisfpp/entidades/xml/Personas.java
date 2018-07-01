package unpsjb.fipm.gisfpp.entidades.xml;

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
	private PersonaXML[] personas;

	public Personas() {
		super();
	}

	public Personas(PersonaXML[] personas) {
		super();
		this.personas = personas;
	}

	public PersonaXML[] getPersonas() {
		return personas;
	}

	public void setPersonas(PersonaXML[] personas) {
		this.personas = personas;
	}

	@Override
	public String toString() {
		return "Personas [personas=" + Arrays.toString(personas) + "]";
	}
	
}
