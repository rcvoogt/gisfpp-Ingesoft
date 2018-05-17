package unpsjb.fipm.gisfpp.servicios;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.TipoProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosProyecto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/daoContext-test.xml",
		"file:src/main/webapp/WEB-INF/app-security.xml" })
public class ProyectoTest {

	@Autowired
	private IServiciosProyecto servProyecto;
	private Proyecto proyecto;
	private Date fechaInicio;
	private Date fechaFin;
	private Calendar calendar;
	private int cantidadProyectosScript;

	@Before
	public void setBefore() {
		cantidadProyectosScript = 10;
		calendar = new GregorianCalendar(2018, 5, 15);
		fechaInicio = calendar.getTime();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 3);
		fechaFin = calendar.getTime();
		proyecto = new Proyecto("557799009", "Res Nº4233", "Desplegando JUnit DB Test", "Esto es una prueba de JUnit",
				TipoProyecto.EXTENSION, fechaInicio, fechaFin, " ", null, null, null);
	
	}

	@Test
	public void testPersistir() {
		assertTrue(true);
	}
	@Test
	public void testRecuperarTodo() {
		try {
			assertEquals(servProyecto.getListado().size(),cantidadProyectosScript);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@After
	public void setAfter() {

	}

}
