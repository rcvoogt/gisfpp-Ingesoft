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
@JacksonXmlRootElement(localName = "Materias")
public class Materias {

	@JacksonXmlProperty(localName = "Materia")
	@JacksonXmlElementWrapper(useWrapping = false)
	private MateriaXML[] materias;

	public Materias() {
		super();
	}

	public MateriaXML[] getMaterias() {
		return materias;
	}

	public void setMaterias(MateriaXML[] materias) {
		this.materias = materias;
	}

	@Override
	public String toString() {
		return "Materias [materias=" + Arrays.toString(materias) + "]";
	}

	
}
