package unpsjb.fipm.gisfpp.controladores.integracion;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import unpsjb.fipm.gisfpp.entidades.cursada.Cursada;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.staff.ECargosStaffFi;
import unpsjb.fipm.gisfpp.entidades.staff.StaffFI;
import unpsjb.fipm.gisfpp.entidades.xml.CursadaXML;
import unpsjb.fipm.gisfpp.entidades.xml.Cursadas;
import unpsjb.fipm.gisfpp.integracion.entidades.CursadaAdapter;
import unpsjb.fipm.gisfpp.servicios.cursada.IServiciosCursada;
import unpsjb.fipm.gisfpp.servicios.integracion.IServicioCursadaAdapter;
import unpsjb.fipm.gisfpp.servicios.integracion.IServicioMateriaAdapter;
import unpsjb.fipm.gisfpp.servicios.integracion.IServicioPersonaAdapter;
import unpsjb.fipm.gisfpp.servicios.staff.IServiciosStaffFI;

@Component
public class ControladorCursadas {
	
	@Autowired
	private IServiciosCursada servCursada;
	@Autowired
	private IServiciosStaffFI servStaff;
	@Autowired
	private IServicioCursadaAdapter servCursadaAdapter;
	@Autowired
	private IServicioPersonaAdapter servPersonaAdapter;	
	@Autowired
	private IServicioMateriaAdapter servMateriaAdapter;	
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	RestImplementation restImplementation;
	Cursadas cursadas;
	
	
	public void integrarCursadas() throws Exception {
		cursadas = getCursadas();
		persistirCursadas(cursadas);
	}
	
	
	private void persistirCursadas(Cursadas cursadas) throws Exception {
		Cursada cursada;
		CursadaAdapter cursadaAdapter;
		int existe;
		for(CursadaXML cursadaXML: cursadas.getCursadas()) {
			cursadaAdapter = crearCursadaAdapter(cursadaXML);
			existe = servCursadaAdapter.actualizarOguardar(cursadaAdapter);
			cursada = crearCursada(cursadaXML);
			if(existe == -1) {
				servCursada.persistir(cursada);
				cursadaAdapter.setIdCursadaGisfpp(cursada.getIdCursada());
				servCursadaAdapter.editar(cursadaAdapter);
			}else {
				cursadaAdapter.setIdCursadaGisfpp(existe);
				servCursadaAdapter.editar(cursadaAdapter);
			}
		}
	}

	private CursadaAdapter crearCursadaAdapter(CursadaXML cursadaXML) {
		CursadaAdapter cursadaAdapter = new CursadaAdapter();
		cursadaAdapter.setCodComision(cursadaXML.getCodigoComision());
		//cursadaAdapter.setIdCursadaGisfpp(integer);
		return cursadaAdapter;
	}



	private Cursada crearCursada(CursadaXML cursadaXML) {
		PersonaFisica aux = null;
		StaffFI auxStaff = null;
		Cursada cursada = new Cursada();
		cursada.setNombre(cursadaXML.getNombre());
		cursada.setAnio(cursadaXML.getAnio());
		cursada.setCuatrimestre(cursadaXML.getCuatrimestre());
		//Meterle los docentes, alumnos y materia!!!
		try {
			aux = servPersonaAdapter.getPFxLegajo(cursadaXML.getPersonaLegajo());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			auxStaff = servStaff.getMiembro(aux);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(auxStaff.getRol().equals(ECargosStaffFi.ALUMNO)) {
			cursada.addAlumno(aux);
		}else {
			cursada.addDocente(aux);
		}
//		cursada.setCodigoComision(cursadaXML.getCodigoComision());
		cursada.setMateria(servMateriaAdapter.getMateriaxCodigo(cursadaXML.getMateria()));	
		return cursada;
	}



	public Cursadas getCursadas() throws JsonParseException, JsonMappingException, IOException {
		HttpEntity<String> response = restImplementation.get(Rutas.SERVICIO_CURSADA_PERSONA, "application/xml");
		return adaptarCursadas(response.getBody());
	}

	private Cursadas adaptarCursadas(String xml) throws JsonParseException, JsonMappingException, IOException {
		XmlMapper xmlMapper = new XmlMapper();
		Cursadas value = xmlMapper.readValue(xml, Cursadas.class);
		return value;
	}

	
}
