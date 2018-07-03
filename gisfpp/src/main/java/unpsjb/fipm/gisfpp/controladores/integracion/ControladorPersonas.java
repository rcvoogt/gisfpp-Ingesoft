package unpsjb.fipm.gisfpp.controladores.integracion;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
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
import unpsjb.fipm.gisfpp.entidades.xml.PersonaXML;
import unpsjb.fipm.gisfpp.entidades.xml.Personas;
import unpsjb.fipm.gisfpp.integracion.entidades.PersonaAdapter;
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
	@Autowired
	RestImplementation restImplementation;
	
	public void persistirPersonas(Personas personas) throws DataAccessException, Exception {
		int existe;
		PersonaAdapter personaAdapter;
		PersonaFisica personaFisica;
		StaffFI staff;
		for (PersonaXML persona : personas.getPersonas()) {
			personaAdapter = crearPersonaAdapter(persona);
			existe = servPersonaAdapter.actualizarOguardar(personaAdapter);
			personaFisica = crearPersonaFisica(persona);
			if(existe == -1) {
				//Si la materia no existe, la creamos y actualizamos el id en el adapter
				servPersona.persistir(personaFisica);
				staff = crearStaff(persona.getRol(), personaFisica);
				servStaff.actualizarOguardar(staff);
				personaAdapter.setIdPersona(personaFisica.getId());
				servPersonaAdapter.editar(personaAdapter);
			}
			else {
				personaAdapter = servPersonaAdapter.getPAxNroInscripcion(persona.getNro_inscripcion());
				personaAdapter.setLegajo2(persona.getLegajo());
				servPersonaAdapter.editar(personaAdapter);
			}			
		}
	}
	
	
	private PersonaAdapter crearPersonaAdapter(PersonaXML persona) {
		PersonaAdapter personaAdapter = new PersonaAdapter();
		personaAdapter.setLegajo1(persona.getLegajo());
		personaAdapter.setNroInscripcion(persona.getNro_inscripcion());
		return personaAdapter;
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

	private PersonaFisica crearPersonaFisica(PersonaXML persona) {
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
	
	
	@SuppressWarnings("unused")
	private PersonaAdapter crearPersonaAdapter(PersonaXML persona , Integer idPersonaFisica) {
		PersonaAdapter personaAdapter = new PersonaAdapter();
		personaAdapter.setLegajo1(persona.getLegajo());
		personaAdapter.setIdPersona(idPersonaFisica);
		return personaAdapter;
	}
	
	private Personas adaptarPersonas(String xml) throws JsonParseException, JsonMappingException, IOException {
		XmlMapper xmlMapper = new XmlMapper();
		Personas value = xmlMapper.readValue(xml, Personas.class);
		return value;
	}

	public Personas getPersonas() throws JsonParseException, JsonMappingException, IOException {
		HttpEntity<String> response = restImplementation.get(Rutas.SERVICIO_PERSONA, "application/xml");
		return adaptarPersonas(response.getBody());
	}

	public void integrarPersonas() throws JsonParseException, JsonMappingException, IOException {
		Personas personas = getPersonas();
		try {
			persistirPersonas(personas);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unused")
	private PersonaXML adaptarPersona(String xml) throws JsonParseException, JsonMappingException, IOException {
		XmlMapper xmlMapper = new XmlMapper();
		PersonaXML value = xmlMapper.readValue(xml, PersonaXML.class);
		return value;
	}

}
