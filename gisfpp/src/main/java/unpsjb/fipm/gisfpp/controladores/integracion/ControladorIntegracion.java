package unpsjb.fipm.gisfpp.controladores.integracion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import unpsjb.fipm.gisfpp.entidades.persona.DatoDeContacto;
import unpsjb.fipm.gisfpp.entidades.persona.Identificador;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.TDatosContacto;
import unpsjb.fipm.gisfpp.entidades.persona.TIdentificador;
import unpsjb.fipm.gisfpp.integracion.entidades.Persona;
import unpsjb.fipm.gisfpp.integracion.entidades.PersonaAdapter;
import unpsjb.fipm.gisfpp.integracion.entidades.Personas;
import unpsjb.fipm.gisfpp.servicios.integracion.IServicioPersonaAdapter;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPF;

@Component
public class ControladorIntegracion {

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	IServicioPF servPersona;
	@Autowired
	IServicioPersonaAdapter servPersonaAdapter;

	public ControladorIntegracion() {
		super();
	}

	public void init() {

	}

	final String ROOT_URI = "http://localhost:30005/";

	public void migrar() {
		try {
			Personas personas = getPersonas();
			persistirPersonas(personas);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Map<String,Object> persistirPersonas(Personas personas) {
		Map<String,Object> persistidas = new HashMap<String,Object>();
		List<PersonaAdapter> personasAdapter = new ArrayList<PersonaAdapter>();
		List<PersonaFisica> personasFisicas = new ArrayList<PersonaFisica>();
		PersonaAdapter personaAdapter;
		PersonaFisica personaFisica;
		for (Persona persona : personas.getPersonas()) {
			DatoDeContacto datoDeContacto = new DatoDeContacto(TDatosContacto.EMAIL, persona.getE_mail());			
			Identificador dni = new Identificador(TIdentificador.DNI, persona.getDni());
			personaAdapter = new PersonaAdapter();
			personaAdapter.setLegajo(persona.getLegajo());
			personasAdapter.add(personaAdapter);
			personaFisica = new PersonaFisica();
			personaFisica.setNombre(persona.getNombreCompleto());
			personaFisica.addIdentificador(dni);
			personaFisica.agregarDatoDeContacto(datoDeContacto);
			personasFisicas.add(personaFisica);
			try {
				servPersona.editar(personaFisica);
				persistidas.put("personasFisicas", personaFisica);
				personaAdapter.setIdPersona(personaFisica.getId());
				
			} catch (Exception e) {
				personaFisica.getUsuario().setNickname(persona.getNombreCompleto());
				personaFisica.getUsuario().setPassword("usuario_gisfpp");
				try {
					servPersona.persistir(personaFisica);
					personaAdapter.setIdPersona(personaFisica.getId());
					persistidas.put("personasFisicas", personaFisica);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			try {
				servPersonaAdapter.persistir(personaAdapter);
				persistidas.put("personasAdapter", personaAdapter);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return persistidas;
	}
	
	

	public Persona getPersona() throws JsonParseException, JsonMappingException, IOException {
		String x = restTemplate.getForObject(ROOT_URI + "persona", String.class);
		return adaptarPersona(x);
	}

	private Persona adaptarPersona(String xml) throws JsonParseException, JsonMappingException, IOException {
		XmlMapper xmlMapper = new XmlMapper();
		Persona value = xmlMapper.readValue(xml, Persona.class);
		return value;
	}

	private Personas adaptarPersonas(String xml) throws JsonParseException, JsonMappingException, IOException {
		XmlMapper xmlMapper = new XmlMapper();
		Personas value = xmlMapper.readValue(xml, Personas.class);
		return value;
	}

	public Personas getPersonas() throws JsonParseException, JsonMappingException, IOException {
		String x = restTemplate.getForObject(ROOT_URI + "listado/personas", String.class);
		return adaptarPersonas(x);
	}

}
