package unpsjb.fipm.gisfpp.controladores.integracion;

import java.io.IOException;

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
public class ControladorPersonas {
	final String SERVICIO_PERSONA_URI = "http://localhost:30005/";
	
	@Autowired
	IServicioPF servPersona;
	@Autowired
	IServicioPersonaAdapter servPersonaAdapter;
	@Autowired
	RestTemplate restTemplate;
	
	public void persistirPersonas(Personas personas) {
		// TODO Crear miembro en staff (alumno o profesor)
		PersonaAdapter personaAdapter;
		PersonaFisica personaFisica;
		for (Persona persona : personas.getPersonas()) {
			DatoDeContacto datoDeContacto = new DatoDeContacto(TDatosContacto.EMAIL, persona.getE_mail());			
			Identificador dni = new Identificador(TIdentificador.DNI, persona.getDni());
			personaAdapter = new PersonaAdapter();
			personaAdapter.setLegajo(persona.getLegajo());
			personaFisica = new PersonaFisica();
			personaFisica.setNombre(persona.getNombreCompleto());
			personaFisica.addIdentificador(dni);
			personaFisica.agregarDatoDeContacto(datoDeContacto);
			try {
				
				// TODO servPersonaAdapter.existe(persona.getLegajo())
				
				servPersona.editar(personaFisica);
				personaAdapter.setIdPersona(personaFisica.getId());
				
			} catch (Exception e) {
				personaFisica.getUsuario().setNickname(persona.getNombreCompleto());
				personaFisica.getUsuario().setPassword("usuario_gisfpp");
				try {
					servPersona.persistir(personaFisica);
					personaAdapter.setIdPersona(personaFisica.getId());
					} catch (Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			try {
				servPersonaAdapter.persistir(personaAdapter);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private Personas adaptarPersonas(String xml) throws JsonParseException, JsonMappingException, IOException {
		XmlMapper xmlMapper = new XmlMapper();
		Personas value = xmlMapper.readValue(xml, Personas.class);
		return value;
	}

	public Personas getPersonas() throws JsonParseException, JsonMappingException, IOException {
		String x = restTemplate.getForObject(Rutas.SERVICIO_PERSONA_TEST, String.class);
		return adaptarPersonas(x);
	}

	public void integrarPersonas() throws JsonParseException, JsonMappingException, IOException {
		Personas personas = getPersonas();
		persistirPersonas(personas);
	}
	
	
	@SuppressWarnings("unused")
	private Persona adaptarPersona(String xml) throws JsonParseException, JsonMappingException, IOException {
		XmlMapper xmlMapper = new XmlMapper();
		Persona value = xmlMapper.readValue(xml, Persona.class);
		return value;
	}

}
