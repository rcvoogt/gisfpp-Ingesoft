package unpsjb.fipm.gisfpp.integracion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import unpsjb.fipm.gisfpp.controladores.integracion.ControladorMaterias;
import unpsjb.fipm.gisfpp.entidades.xml.Materias;
import unpsjb.fipm.gisfpp.servicios.TemplateTest;
import unpsjb.fipm.gisfpp.servicios.cursada.IServiciosMateria;
import unpsjb.fipm.gisfpp.servicios.integracion.IServicioMateriaAdapter;

public class ControladorMateriasTest extends TemplateTest {
	@Autowired
	IServiciosMateria servMateria;
	@Autowired
	IServicioMateriaAdapter servMateriaAdapter;
	@Autowired
	ControladorMaterias controladorMaterias;
	Materias materias;
	
	@Override
	public void setBefore() {
		
	}
	
	@Test
	public void testGetMaterias() {
		try {
			materias = controladorMaterias.getMaterias();
			System.out.println(materias);
			assertNotNull(materias);
			assertEquals(materias.getMaterias().length, 40);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testIntegrarPersonas() {
		try {
			controladorMaterias.integrarMaterias();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	@Override
	public void setAfter() {
		
	}

}
