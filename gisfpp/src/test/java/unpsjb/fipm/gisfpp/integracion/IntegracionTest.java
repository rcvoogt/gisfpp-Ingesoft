package unpsjb.fipm.gisfpp.integracion;

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

	
	@After
	public void setAfter() {
		
	}
}
