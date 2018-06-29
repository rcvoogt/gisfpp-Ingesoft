package unpsjb.fipm.gisfpp.controladores.integracion;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
import unpsjb.fipm.gisfpp.entidades.staff.ECargosStaffFi;
import unpsjb.fipm.gisfpp.entidades.staff.StaffFI;
import unpsjb.fipm.gisfpp.integracion.entidades.Persona;
import unpsjb.fipm.gisfpp.integracion.entidades.PersonaAdapter;
import unpsjb.fipm.gisfpp.integracion.entidades.Personas;
import unpsjb.fipm.gisfpp.servicios.integracion.IServicioPersonaAdapter;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPF;
import unpsjb.fipm.gisfpp.servicios.staff.IServiciosStaffFI;

@Component
public class ControladorPersonas {
	final String SERVICIO_PERSONA_URI = "http://localhost:30005/";
	
	@Autowired
	IServicioPF servPersona;
	@Autowired
	IServicioPersonaAdapter servPersonaAdapter;
	@Autowired
	IServiciosStaffFI servStaff;
	@Autowired
	RestTemplate restTemplate;
	
	public void persistirPersonas(Personas personas) {
		PersonaAdapter personaAdapter;
		PersonaFisica personaFisica;
		StaffFI staff;
		for (Persona persona : personas.getPersonas()) {
			personaFisica = crearPersonaFisica(persona);									
				try {
					servPersona.actualizarOguardar(personaFisica);
				} catch (Exception e) {
					e.printStackTrace();
				}
				staff = crearStaff(persona.getRol(),personaFisica);
				try {
					servStaff.actualizarOguardar(staff);
				} catch (DataAccessException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				personaAdapter = crearPersonaAdapter(persona, personaFisica.getId());
				try {
					servPersonaAdapter.actualizarOguardar(personaAdapter);
				} catch (DataAccessException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			
		}
	}
	
	
	private StaffFI crearStaff(String rol, PersonaFisica personaFisica) {
		Calendar calendar = Calendar.getInstance();
		Date desde = calendar.getTime();
		calendar.add(Calendar.YEAR, 1);
		Date hasta = calendar.getTime();
		StaffFI staff = new StaffFI();
		staff.setDesde(desde);
		staff.setHasta(hasta);
		staff.setMiembro(personaFisica);
		if(rol.equals("ALUMNO")) {
			staff.setRol(ECargosStaffFi.ALUMNO);
			return staff;
		}
		staff.setRol(ECargosStaffFi.PROFESOR);
		return staff;
	}

	private PersonaFisica crearPersonaFisica(Persona persona) {
		DatoDeContacto datoDeContacto = new DatoDeContacto(TDatosContacto.EMAIL, persona.getE_mail());		
		Identificador dni = new Identificador(TIdentificador.DNI, persona.getDni());
		Identificador legajo = new Identificador(TIdentificador.LEGAJO, persona.getLegajo());
		PersonaFisica personaFisica = new PersonaFisica();
		personaFisica.setNombre(persona.getNombreCompleto());
		personaFisica.addIdentificador(dni);
		personaFisica.addIdentificador(legajo);
		personaFisica.agregarDatoDeContacto(datoDeContacto);
		personaFisica.getUsuario().setNickname(persona.getNombreCompleto());
		personaFisica.getUsuario().setPassword("usuario_gisfpp");		
		personaFisica.getUsuario().setPersona(personaFisica);
		return personaFisica;
	}
	
	
	private PersonaAdapter crearPersonaAdapter(Persona persona , Integer idPersonaFisica) {
		PersonaAdapter personaAdapter = new PersonaAdapter();
		personaAdapter.setLegajo(persona.getLegajo());
		personaAdapter.setIdPersona(idPersonaFisica);
		return personaAdapter;
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
