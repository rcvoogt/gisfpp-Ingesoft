package unpsjb.fipm.gisfpp.controladores.integracion;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import unpsjb.fipm.gisfpp.entidades.cursada.Cursada;
import unpsjb.fipm.gisfpp.entidades.cursada.Materia;
import unpsjb.fipm.gisfpp.entidades.xml.CursadaXML;
import unpsjb.fipm.gisfpp.entidades.xml.Cursadas;
import unpsjb.fipm.gisfpp.entidades.xml.MateriaXML;
import unpsjb.fipm.gisfpp.entidades.xml.Materias;
import unpsjb.fipm.gisfpp.integracion.entidades.CursadaAdapter;
import unpsjb.fipm.gisfpp.integracion.entidades.MateriaAdapter;
import unpsjb.fipm.gisfpp.servicios.cursada.IServiciosCursada;
import unpsjb.fipm.gisfpp.servicios.integracion.IServicioCursadaAdapter;

@Component
public class ControladorCursadas {
	
	@Autowired
	private IServiciosCursada servCursada;
	@Autowired
	private IServicioCursadaAdapter servCursadaAdapter;	
	@Autowired
	RestTemplate restTemplate;
	Cursadas cursadas;
	
	
	public void integrarCursadas() throws Exception {
		cursadas = getCursadas();
		persistirCursadas(cursadas);
	}
	
	
	private void persistirCursadas(Cursadas cursadas) throws Exception {
		Cursada cursada;
		CursadaAdapter cursadaAdapter;
		
		for(CursadaXML cursadaXML: cursadas.getCursadas()) {
			cursada = crearCursada(cursadaXML);
			servCursada.actualizarOguardar(cursada);
			cursadaAdapter = crearCursadaAdapter(cursadaXML,cursada.getIdCursada());
			servCursadaAdapter.actualizarOguardar(cursadaAdapter);
		}
	}

	private CursadaAdapter crearCursadaAdapter(CursadaXML cursadaXML, Integer integer) {
		CursadaAdapter cursadaAdapter = new CursadaAdapter();
		cursadaAdapter.setCodComision(cursadaXML.getCodigoComision());
		cursadaAdapter.setIdCursadaGisfpp(integer);
		return cursadaAdapter;
	}



	private Cursada crearCursada(CursadaXML cursadaXML) {
		Cursada cursada = new Cursada();
		cursada.setNombre(cursadaXML.getNombre());
		return cursada;
	}



	public Cursadas getCursadas() throws JsonParseException, JsonMappingException, IOException {
		String x = restTemplate.getForObject(Rutas.SERVICIO_CURSADA_TEST, String.class);
		return adaptarCursadas(x);
	}

	private Cursadas adaptarCursadas(String xml) throws JsonParseException, JsonMappingException, IOException {
		XmlMapper xmlMapper = new XmlMapper();
		Cursadas value = xmlMapper.readValue(xml, Cursadas.class);
		return value;
	}

	
}
