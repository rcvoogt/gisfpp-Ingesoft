package unpsjb.fipm.gisfpp.integracion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import unpsjb.fipm.gisfpp.controladores.integracion.ControladorCursadas;
import unpsjb.fipm.gisfpp.controladores.integracion.ControladorMaterias;
import unpsjb.fipm.gisfpp.controladores.integracion.ControladorPersonas;
import unpsjb.fipm.gisfpp.entidades.xml.Cursadas;
import unpsjb.fipm.gisfpp.servicios.TemplateTest;

public class ControladorCursadasTest extends TemplateTest {
	@Autowired
	ControladorPersonas controladorPersonas;
	@Autowired
	ControladorMaterias controladorMaterias;
	@Autowired
	ControladorCursadas controladorCursadas;
	Cursadas cursadas;
	
	@Override
	public void setBefore() {
		
	}
	
	@Test
	public void testGetPersonas() {
		try {
			cursadas = controladorCursadas.getCursadas();
			System.out.println(cursadas);
			assertNotNull(cursadas);
			assertEquals(cursadas.getCursadas().length, 881);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testIntegrarPersonas() {
		
		try {
//			controladorPersonas.integrarPersonas();
//			controladorMaterias.integrarMaterias();
			controladorCursadas.integrarCursadas();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}	

	@Override
	public void setAfter() {
		// TODO Auto-generated method stub
		
	}

}
