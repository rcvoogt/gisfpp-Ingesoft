package unpsjb.fipm.gisfpp.servicios;
import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import unpsjb.fipm.gisfpp.entidades.proyecto.EstadoProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.OfertaActividad;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.TipoProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosOfertaActividades;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosProyecto;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/daoContext-test.xml",
		"file:src/main/webapp/WEB-INF/app-security.xml"
}) 
public class OfertaActividadesTest {

		
	@Autowired
    private IServiciosOfertaActividades servOfertaActividades;
	@Autowired
	private IServiciosProyecto servProyecto;
	
	private Proyecto p1;
	private Proyecto p2;
	
	private Calendar d1;
	private Calendar d2;
	
	@Before
	public void before() {
		d1 = new GregorianCalendar(2018,05,10);
		d2 = new GregorianCalendar(2019,05,10);
		
		//Aca cargamos los 17 datos que requeria el set de datos
		p1 = new Proyecto("Caso 1", "5498765468","Proyecto test 1", "Nada", TipoProyecto.EXTENSION,
			d1.getTime(),d2.getTime(), "Nada",null, null,null);
		p2 = new Proyecto("Caso 2", "3214","Proyecto test 2", "Nada2", TipoProyecto.INVESTIGACION,
				d1.getTime(),d2.getTime(), "Nada",null, null,null);
		p1.setEstado(EstadoProyecto.ACTIVO);
		p2.setEstado(EstadoProyecto.ACTIVO);
		try {
			servProyecto.persistir(p1);
			servProyecto.persistir(p2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void test() {
		List<OfertaActividad> ofertas = servOfertaActividades.getAllOfertas();
		assertEquals(2,ofertas.size());
		//assertNull(ofertas);
	}
	
	@After
	public void after() {
		//Aca borramos los 17 ofertas
		try {
			servProyecto.eliminar(p1);
			servProyecto.eliminar(p2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
