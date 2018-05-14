package unpsjb.fipm.gisfpp.servicios;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mysql.jdbc.AssertionFailedException;

import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocado;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.persona.Persona;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.entidades.proyecto.ERespuestaConvocado;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.servicios.convocatoria.IServiciosConvocatoria;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioUsuario;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/daoContext-test.xml",
		"file:src/main/webapp/WEB-INF/app-security.xml"
}) 
public class AsignarTest {
	
	@Autowired
    private IServiciosConvocatoria servConvocatoria;
	@Autowired
	private IServicioUsuario servUsuario;
	
	private Isfpp isfppPadre;
	private Convocatoria convocatoria;
	private List<Convocado> convocados;
	private Convocado personaAcepto;
	private Convocado personaRechazo;
	private Convocado convocado;
	private Usuario u;
	
	@Before
	public void setup() {		
		//isfppPadre = new Isfpp();
		convocatoria = crearNueva();
		//convocados = crearConvocados();	
	}
	
	
	@Test
	public void testCrearConvocatoria() {
		try {
			servUsuario.persistir(u);
			servConvocatoria.persistir(convocatoria);
			assertNotNull(servConvocatoria.getInstancia(convocatoria.getId()));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	@Test
	public void testAsignarPersona() {
		//assertEquals(convocados.size(),2);
	}
	@Test
	public void testGetListado() {	
		assertTrue("No implementado aun!", true );
	}

	@Test
	public void testGetIsfpp() {
		assertTrue("No implementado aun!", true );
	}
	
	@After
	public void setAfter() {
		try {
			servUsuario.eliminar(u);
			servConvocatoria.eliminar(convocatoria);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Convocatoria crearNueva() {
		u = new Usuario(new PersonaFisica("Sergio Vazquez"),"sergio_vezquez", "1234", true);
		Calendar c = new GregorianCalendar();
		Date nueva = c.getTime();
		c.set(Calendar.DATE, c.get(Calendar.DATE) + 5);
		Integer result = null;
		// hoy + 5 dias
		long ltime=nueva.getTime()+5*24*60*60*1000;
		Date vencimiento = c.getTime();
		return new Convocatoria(nueva, vencimiento, "Detalle",isfppPadre, u);

	}
	
	private List<Convocado> crearConvocados() {
		List<Convocado> c = new ArrayList<Convocado>();
		personaAcepto = new Convocado(convocatoria,new PersonaFisica("Juan Rodriguez"));
		personaAcepto.setRespuesta(ERespuestaConvocado.ACEPTADA);
		personaRechazo = new Convocado(convocatoria,new PersonaFisica("Luis Velazquez"));
		personaRechazo.setRespuesta(ERespuestaConvocado.RECHAZADA);
		c.add(personaAcepto);
		c.add(personaRechazo);
		return c;
	}

}
