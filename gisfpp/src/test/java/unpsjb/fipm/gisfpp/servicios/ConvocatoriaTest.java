package unpsjb.fipm.gisfpp.servicios;

import static org.junit.Assert.*;


import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import unpsjb.fipm.gisfpp.entidades.proyecto.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosConvocatoria;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/daoContext-test.xml",
		"file:src/main/webapp/WEB-INF/app-security.xml"
}) 
public class ConvocatoriaTest {
	
	@Autowired
    private IServiciosConvocatoria servConvocatoria;
	
	private Isfpp isfppPadre;
	
	@Before
	public void setup() {
		isfppPadre = new Isfpp();
	}
	
	@Test
	public void testCrear() {
		
		Integer result = null;
		Convocatoria nuevaConvocatoria = crearNueva();
		System.out.println(servConvocatoria);
		try {
			result = servConvocatoria.persistir(nuevaConvocatoria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("No pudo persistir");
		}
		assertNotNull(result);
		
	}

	/*@Test
	public void getInstancia() {
		Integer result = null;
		
		Convocatoria nuevaConvocatoria = crearNueva();
		Convocatoria convocatoriaRecuperada = null;
		try {
			result = servConvocatoria.persistir(nuevaConvocatoria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("No pudo persistir");
		}
		try {
			servConvocatoria.getInstancia(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("No pudo obtener la instancia");
		}
		
		assertNotNull(convocatoriaRecuperada);
		assertTrue(convocatoriaRecuperada.equals(nuevaConvocatoria));
		
	}*/
	
	/*@Test
	public void testActualizar() {
		Integer result = null;
		Convocatoria nuevaConvocatoria = this.crearNueva();
		Convocatoria convocatoriaModificada = null;
		String mensajeNuevo = "Nuevo mensaje"; 
		try {
			result = servConvocatoria.persistir(nuevaConvocatoria);
		} catch (Exception e) {

			e.printStackTrace();
			fail("No pudo persistir");
		}
		assertNotNull(result);
		
		nuevaConvocatoria.setMensaje(mensajeNuevo);
		
		try {
			servConvocatoria.editar(nuevaConvocatoria);
		} catch (Exception e) {

			e.printStackTrace();
			fail("No pudo persistir");
		}
		try {
			convocatoriaModificada = servConvocatoria.getInstancia(nuevaConvocatoria.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("No pudo recuperar instancia");
		}
		assertNotNull(convocatoriaModificada);
		assertTrue(convocatoriaModificada.getMensaje().equals(mensajeNuevo));
		

	}*/

	/*@Test
	public void testEliminar() {
		Integer result = null;
		Convocatoria nuevaConvocatoria = this.crearNueva();
		Convocatoria convocatoriaRecuperada = null;
		try {
			result = servConvocatoria.persistir(nuevaConvocatoria);
		} catch (Exception e) {

			e.printStackTrace();
			fail("No pudo persistir");
		}
		try {
			servConvocatoria.eliminar(nuevaConvocatoria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("No pudo eliminar");
		}
		try {
			convocatoriaRecuperada = servConvocatoria.getInstancia(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		assertNull(convocatoriaRecuperada);
		
		
	}*/

	@Test
	public void testGetListado() {
		
		assertTrue("No implementado aun!", true );
	}

	@Test
	public void testGetIsfpp() {
		assertTrue("No implementado aun!", true );
	}
	
	private Convocatoria crearNueva() {
		Date nueva = new Date();
		Integer result = null;
		// hoy + 5 dias
		long ltime=nueva.getTime()+5*24*60*60*1000;
		Date vencimiento=new Date(ltime);
		return new Convocatoria(nueva, vencimiento, "Detalle",isfppPadre);
		
	}

}
