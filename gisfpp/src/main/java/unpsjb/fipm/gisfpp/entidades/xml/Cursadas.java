package unpsjb.fipm.gisfpp.entidades.xml;

import java.util.Arrays;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Cursadas")
public class Cursadas {
	
	@JacksonXmlProperty(localName = "Cursada")
	@JacksonXmlElementWrapper(useWrapping = false)
	private CursadaXML[] cursadas;

	public Cursadas() {
		super();
	}

	public CursadaXML[] getCursadas() {
		return cursadas;
	}

	public void setCursadas(CursadaXML[] cursadas) {
		this.cursadas = cursadas;
	}

	@Override
	public String toString() {
		return "Cursadas [cursadas=" + Arrays.toString(cursadas) + "]";
	}
	
}
