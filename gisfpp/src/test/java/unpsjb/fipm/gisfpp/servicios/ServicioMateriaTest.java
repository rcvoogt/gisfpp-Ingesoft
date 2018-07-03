package unpsjb.fipm.gisfpp.servicios;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import unpsjb.fipm.gisfpp.entidades.cursada.Materia;
import unpsjb.fipm.gisfpp.servicios.cursada.IServiciosMateria;

public class ServicioMateriaTest extends TemplateTest {
	@Autowired
	IServiciosMateria servMateria;
	Materia materiaPersistida;
	Materia materiaNoPersistida;

	@Override
	public void setBefore() {
		materiaPersistida = new Materia();
		materiaPersistida.setCodigoMateria("if-002345-4");
		materiaPersistida.setNombre("Materia Persistida");
		
		materiaNoPersistida = new Materia();
		materiaNoPersistida.setCodigoMateria("if-48484884-2");
		materiaNoPersistida.setNombre("Materia No Persistida");
		try {
			servMateria.persistir(materiaPersistida);
		} catch (Exception e) {
			System.err.println("Error al persistir");
		}
	}
	@Test
	public void existeTest() {
		try {
			assertNotNull((servMateria.existe(materiaPersistida.getCodigoMateria())));
			assertNull(servMateria.existe(materiaNoPersistida.getCodigoMateria()));
			
		} catch (Exception e) {}
		
	}
	
	@Test
	public void actualizarOguardarTest() {
		try {
			servMateria.actualizarOguardar(materiaPersistida);
			servMateria.actualizarOguardar(materiaNoPersistida);
			
			assertNotNull(servMateria.getInstancia(materiaPersistida.getId()));
			assertNotNull(servMateria.getInstancia(materiaNoPersistida.getId()));
			
			servMateria.eliminar(materiaNoPersistida);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setAfter() {
		try {
			servMateria.eliminar(materiaPersistida);
		} catch (Exception e) {
			System.err.println("Error al eliminar");

		}
	}

}
