package unpsjb.fipm.gisfpp.servicios;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

import com.mysql.jdbc.AssertionFailedException;

import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocable;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocado;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.persona.DatoDeContacto;
import unpsjb.fipm.gisfpp.entidades.persona.Identificador;
import unpsjb.fipm.gisfpp.entidades.persona.Persona;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.TDatosContacto;
import unpsjb.fipm.gisfpp.entidades.persona.TIdentificador;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.entidades.proyecto.ERespuestaConvocado;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.servicios.convocatoria.IServiciosConvocatoria;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPF;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPersona;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioUsuario;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosProyecto;
import unpsjb.fipm.gisfpp.util.security.UtilSecurity;


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
	
	@Before
	@Transactional
	public void setup() {		
		try {
			isfppPadre = crearIsfpp();
			convocador = crearConvocador();
			convocatoria = crearConvocatoria();	
			convocados = crearConvocados();			
			convocatoria.agregarConvocado(personaAcepto);
			convocatoria.agregarConvocado(personaRechazo);
			servConvocatoria.persistir(convocatoria);

		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}

	@Test
	@Transactional
	public void testCrearConvocatoria() throws Exception {
		Convocatoria convocatoria = servConvocatoria.getInstancia(this.convocatoria.getId());
		assertEquals(servConvocatoria.getCantidadConvocados(convocatoria.getId()), convocados.size());
		assertEquals(convocatoria.getConvocados().size(),convocados.size());
		assertEquals(servConvocatoria.getConvocadosAceptadores(convocatoria.getId()).get(0),personaAcepto);


	}


	@Test
	public void testAsignarPersona() throws Exception {
		
		servConvocatoria.asignar(personaAcepto);
		Convocatoria convocatoria = servConvocatoria.getInstancia(this.convocatoria.getId());
		Convocable convocable;
		convocable = convocatoria.getConvocable();
		assertTrue(servConvocatoria.isAsignado(convocado.getPersona(),convocable));
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
		Convocatoria convocatoria = new Convocatoria(nueva, vencimiento, "Detalle",isfppPadre, convocador);
		//convocatoria.agregarConvocado(personaAcepto);
		//convocatoria.agregarConvocado(personaRechazo);
		return convocatoria;

	}
	@Transactional
	private List<Convocado> crearConvocados() throws Exception {
		List<Convocado> c = new ArrayList<Convocado>();
		DatoDeContacto datos = new DatoDeContacto(TDatosContacto.EMAIL, "arza.isaias@gmail.com");
		PersonaFisica p1 = servPersona.getInstancia(100);
		p1.agregarDatoDeContacto(datos);
		servPersona.editar(p1);
		PersonaFisica p2 = servPersona.getInstancia(101);
		p2.agregarDatoDeContacto(datos);
		servPersona.editar(p2);
		personaAcepto = new Convocado(convocatoria,p1);
		personaAcepto.setRespuesta(ERespuestaConvocado.ACEPTADA);
		//personaAcepto.getPersona().setUsuario(new Usuario(personaAcepto.getPersona(), "juan_rodriguez", "1234", true));
		//personaAcepto.getPersona().agregarDatoDeContacto(datos);
		
		personaRechazo = new Convocado(convocatoria,p2);
		personaRechazo.setRespuesta(ERespuestaConvocado.RECHAZADA);
		//personaRechazo.getPersona().setUsuario(new Usuario(personaRechazo.getPersona(), "luis_velazquez", "1234", true));
		//personaRechazo.getPersona().agregarDatoDeContacto(datos);
	
		c.add(personaAcepto);
		c.add(personaRechazo);
		personaAcepto.setConvocatoria(convocatoria);
		personaRechazo.setConvocatoria(convocatoria);	
		
		return c;
	}
	
	/**
	 * Se crea una isfpp por el modelo de datos actual, debería ser un Proyecto/Subproyecto
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
