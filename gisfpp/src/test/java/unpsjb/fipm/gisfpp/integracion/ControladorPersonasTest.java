package unpsjb.fipm.gisfpp.integracion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import unpsjb.fipm.gisfpp.controladores.integracion.ControladorPersonas;
import unpsjb.fipm.gisfpp.entidades.xml.Personas;
import unpsjb.fipm.gisfpp.servicios.TemplateTest;

public class ControladorPersonasTest extends TemplateTest {
	@Autowired
	ControladorPersonas controladorPersonas;
	Personas personas;
	
	@Override
	public void setBefore() {
		
	}
	
	@Test
	public void testGetPersonas() {
		try {
			personas = controladorPersonas.getPersonas();
			System.out.println(personas);
			assertNotNull(personas);
			assertEquals(personas.getPersonas().length, 206);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testIntegrarPersonas() {
		try {
			controladorPersonas.integrarPersonas();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	@Override
	public void setAfter() {
		// TODO Auto-generated method stub
		
	}

}
