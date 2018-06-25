package unpsjb.fipm.gisfpp.servicios;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import unpsjb.fipm.gisfpp.controladores.integracion.ControladorIntegracion;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.integracion.entidades.Persona;
import unpsjb.fipm.gisfpp.integracion.entidades.PersonaAdapter;
import unpsjb.fipm.gisfpp.integracion.entidades.Personas;
import unpsjb.fipm.gisfpp.servicios.integracion.IServicioPersonaAdapter;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPF;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/daoContext-test.xml",
		"file:src/main/webapp/WEB-INF/app-security.xml" })

public class IntegracionTest {

	@Autowired
	private ControladorIntegracion integracion;
	@Autowired
	private IServicioPF servPersonaFisica;
	@Autowired
	private IServicioPersonaAdapter servPersonaAdapter;
	private Persona persona;
	private Personas personas;
	private String apellido;
	private Map<String, Object> persistidas;

	@Before
	public void setBefore() {
		apellido = "Arza";

	}

	@Test
	public void testGetPersona() {
		try {
			persona = integracion.getPersona();
			assertNotNull(persona);
			assertEquals(persona.getApellido(), apellido);
			System.out.println(persona);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetPersonas() {
		try {
			personas = integracion.getPersonas();
			assertNotNull(personas);
			assertEquals(personas.getPersonas().length, 4);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testPersistirPersonas() {
		try {
			personas = integracion.getPersonas();
			persistidas = integracion.persistirPersonas(personas);
			System.out.println(persistidas);
			assertNotNull(persistidas);
			assertEquals(persistidas.size(),8);
		} catch (Exception e) {
			e.printStackTrace();
		}
		eliminarPersonas();
	}

	public void eliminarPersonas() {
		for (Map.Entry<String, Object> persona : persistidas.entrySet()) {
			try {
				if (persona.getKey().equals("personasFisicas")) 
					servPersonaFisica.eliminar((PersonaFisica) persona.getValue());
				if(persona.getKey().equals("personasAdapter"))
					servPersonaAdapter.eliminar((PersonaAdapter) persona.getValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	@After
	public void setAfter() {
		
	}
}
