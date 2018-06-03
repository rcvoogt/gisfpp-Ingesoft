package unpsjb.fipm.gisfpp.servicios;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocado;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.convocatoria.ERespuestaConvocado;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.servicios.convocatoria.IServiciosConvocatoria;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPF;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioUsuario;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServicioSubProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosProyecto;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/daoContext-test.xml",
		"file:src/main/webapp/WEB-INF/app-security.xml"
}) 
public class AsignarTest {
	@Autowired
	private IServicioPF servPersona;
	@Autowired
	private IServiciosProyecto servProyecto;
	@Autowired
	private IServicioSubProyecto servSubproyecto;
	@Autowired
	private IServiciosIsfpp servIsfpp;
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
	private Usuario convocador;
	private Proyecto proyecto;
	@Before
	@Transactional
	public void setup() {		
		try {
			//isfppPadre = crearIsfpp();
			proyecto = getProyecto();
			convocador = proyecto.getResponsables().get(0).getUsuario();
			convocatoria = crearConvocatoria();	
			convocados = crearConvocados();			
			convocatoria.agregarConvocado(personaAcepto);
			convocatoria.agregarConvocado(personaRechazo);
			servConvocatoria.persistir(convocatoria);

		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}

	private Proyecto getProyecto() throws Exception {
		return servProyecto.getProyectosActivos().get(0);
	}

	@Test
	public void testCrearConvocatoria() throws Exception {
		Convocatoria convocatoria = servConvocatoria.getInstancia(this.convocatoria.getId());
		assertNotNull(convocatoria);
		assertEquals(servConvocatoria.getCantidadConvocados(convocatoria.getId()), convocados.size());
		assertEquals(convocatoria.getConvocados().size(),convocados.size());
		assertEquals(servConvocatoria.getConvocadosAceptadores(convocatoria).get(0),personaAcepto);
		//assertNotNull(proyecto);

	}

	@Test
	public void testProyectosActivos() {
		try {
			List<Proyecto> proyectosActivos = servProyecto.getProyectosActivos();
			assertEquals(proyectosActivos.size(),7);
			assertTrue(proyectosActivos.contains(proyecto));
			assertTrue(proyecto.getResponsables().contains(convocador.getPersona()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	@Test
	public void testAsignarPersonaProyecto() throws Exception {
		int cantidadInicial,cantidadFinal;
		Proyecto proyecto;
		
		proyecto = (Proyecto) convocatoria.getConvocable();
		cantidadInicial = proyecto.getStaff().size();
		servConvocatoria.asignar(personaAcepto);
		
		proyecto = servProyecto.getInstancia(this.proyecto.getId());
		proyecto = (Proyecto) convocatoria.getConvocable();
		cantidadFinal = proyecto.getStaff().size();
		
		assertNotEquals(cantidadInicial, cantidadFinal);
		assertEquals(cantidadInicial + 1,cantidadFinal);
		assertTrue(servConvocatoria.isAsignado(personaAcepto.getPersona(),convocatoria.getConvocable()));
		
		servProyecto.quitarMiembroStaff(personaAcepto.getPersona(), proyecto);

		assertFalse(servConvocatoria.isAsignado(personaAcepto.getPersona(),convocatoria.getConvocable()));
		
	}
	
	@Test
	public void testAsignarPersonaSubproyecto() throws Exception{
		
//		SubProyecto subProyecto = servSubproyecto.getSubProyectos(proyecto).get(0);
//		servConvocatoria.asignar(personaAcepto);
		
	}
	
	@After
	@Transactional
	public void setAfter() {
		try {
			servConvocatoria.eliminar(convocatoria);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Transactional
	private Convocatoria crearConvocatoria() throws Exception {
		Calendar c = new GregorianCalendar();
		Date nueva = c.getTime();
		c.set(Calendar.DATE, c.get(Calendar.DATE) + 5);
		Date vencimiento = c.getTime();		
		Convocatoria convocatoria = new Convocatoria(nueva, vencimiento, "Convocatoria para proyecto: " + proyecto.getTitulo(), proyecto, convocador);		
		return convocatoria;

	}
	@Transactional
	private List<Convocado> crearConvocados() throws Exception {
		List<Convocado> c = new ArrayList<Convocado>();
		PersonaFisica p1 = servPersona.getInstancia(16);
		PersonaFisica p2 = servPersona.getInstancia(17);
		personaAcepto = new Convocado(convocatoria,p1);
		personaAcepto.setRespuesta(ERespuestaConvocado.ACEPTADA);
		
		personaRechazo = new Convocado(convocatoria,p2);
		personaRechazo.setRespuesta(ERespuestaConvocado.RECHAZADA);
		c.add(personaAcepto);
		c.add(personaRechazo);
		
		return c;
	}
	
	/**
	 * Se crea una isfpp por el modelo de datos actual, deber�a ser un Proyecto/Subproyecto
	 * Se trae una isfpp conocida, para poder utilizar una aleatoria debe corregirse el set de datos.
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	private Isfpp crearIsfpp() throws Exception {
		return servIsfpp.getInstancia(34) ;
	}
	@Transactional
	private Usuario crearConvocador() throws Exception {
		Proyecto p = servProyecto.getInstancia(23);
		return p.getResponsables().get(0).getUsuario();
	}

}
