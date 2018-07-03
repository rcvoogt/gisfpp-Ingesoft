package unpsjb.fipm.gisfpp.servicios;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import unpsjb.fipm.gisfpp.entidades.cursada.Cursada;
import unpsjb.fipm.gisfpp.entidades.cursada.Materia;
import unpsjb.fipm.gisfpp.servicios.cursada.IServiciosCursada;

public class ServicioCursadaTest extends TemplateTest {
	@Autowired
	IServiciosCursada servCursada;
	Cursada cursadaPersistida;
	Cursada cursadaNoPersistida;
	Materia materiaPersistida;
	Materia materiaNoPersistida;

	@Override
	public void setBefore() {
		cursadaPersistida = new Cursada();
		cursadaPersistida.setCodigoComision("if-002345-4");
		cursadaPersistida.setNombre("Cursada Persistida");
		
		materiaPersistida = new Materia();
		materiaPersistida.setCodigoMateria("if-002345-4");
		materiaPersistida.setNombre("Materia Persistida");
		
		cursadaPersistida.setMateria(materiaPersistida);
		cursadaPersistida.setAnio(2018);
		cursadaPersistida.setCuatrimestre("Segundo Cuatrimestre");
		
		
		cursadaNoPersistida = new Cursada();
		cursadaNoPersistida.setCodigoComision("if-48484884-2");
		cursadaNoPersistida.setNombre("Cursada No Persistida");
		
		materiaNoPersistida = new Materia();
		materiaNoPersistida.setCodigoMateria("if-002345-4");
		materiaNoPersistida.setNombre("Materia No Persistida");
		
		cursadaNoPersistida.setMateria(materiaNoPersistida);
		cursadaNoPersistida.setAnio(2018);
		cursadaNoPersistida.setCuatrimestre("Segundo Cuatrimestre");
		
		
		
		try {
			servCursada.persistir(cursadaPersistida);
		} catch (Exception e) {
			System.err.println("Error al persistir");
		}
	}
	@Test
	public void existeTest() {
		try {
			Integer idCursada = new Integer(servCursada.existe(cursadaPersistida.getCodigoComision()));
			Integer idCursadaNoPersistida = new Integer(servCursada.existe(cursadaNoPersistida.getCodigoComision()));
		
			assertEquals(idCursada,cursadaPersistida.getIdCursada());
			
			assertNotEquals(idCursadaNoPersistida,cursadaNoPersistida.getIdCursada());
			assertEquals(idCursadaNoPersistida,new Integer(-1));
		} catch (Exception e) {}
		
	}
	
	@Test
	public void actualizarOguardarTest() {
		try {
			cursadaPersistida.setCodigoComision("CHOTA");
			servCursada.actualizarOguardar(cursadaPersistida);
			servCursada.actualizarOguardar(cursadaNoPersistida);
			
			assertNotNull(servCursada.getInstancia(cursadaPersistida.getIdCursada()));
			assertNotNull(servCursada.getInstancia(cursadaNoPersistida.getIdCursada()));
			assertEquals(servCursada.getInstancia(cursadaPersistida.getIdCursada()), "CHOTA");
			servCursada.eliminar(cursadaNoPersistida);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setAfter() {
		try {
			servCursada.eliminar(cursadaPersistida);
		} catch (Exception e) {
			System.err.println("Error al eliminar");

		}
	}

}
