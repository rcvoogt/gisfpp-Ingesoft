package unpsjb.fipm.gisfpp.servicios;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import unpsjb.fipm.gisfpp.integracion.entidades.MateriaAdapter;
import unpsjb.fipm.gisfpp.servicios.integracion.IServicioMateriaAdapter;

public class ServicioMateriaAdapterTest extends TemplateTest {
	@Autowired
	IServicioMateriaAdapter servMateriaAdapter;
	MateriaAdapter materiaPersistida;
	MateriaAdapter materiaNoPersistida;

	@Override
	public void setBefore() {
		materiaPersistida = new MateriaAdapter();
		materiaPersistida.setIdMateria(403);
		materiaPersistida.setMateria("if-002345-4");
		
		materiaNoPersistida = new MateriaAdapter();
		materiaNoPersistida.setIdMateria(40401);
		materiaNoPersistida.setMateria("if-48484884-2");
		try {
			servMateriaAdapter.persistir(materiaPersistida);
		} catch (Exception e) {
			System.err.println("Error al persistir");
		}
	}
	@Test
	public void existeTest() {
		try {
			Integer idMateria = new Integer(servMateriaAdapter.existe(materiaPersistida.getMateria()));
			Integer idMateriaNoPersistida = new Integer(servMateriaAdapter.existe(materiaNoPersistida.getMateria()));
			
			assertEquals(idMateria,materiaPersistida.getId());
			
			assertNotEquals(idMateriaNoPersistida,materiaNoPersistida.getId());
			assertEquals(idMateriaNoPersistida,new Integer(-1));
		} catch (Exception e) {}
		
	}
	
	@Test
	public void actualizarOguardarTest() {
		try {
			servMateriaAdapter.actualizarOguardar(materiaPersistida);
			servMateriaAdapter.actualizarOguardar(materiaNoPersistida);
			
			assertNotNull(servMateriaAdapter.getInstancia(materiaPersistida.getId()));
			assertNotNull(servMateriaAdapter.getInstancia(materiaNoPersistida.getId()));
			
			servMateriaAdapter.eliminar(materiaNoPersistida);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setAfter() {
		try {
			servMateriaAdapter.eliminar(materiaPersistida);
		} catch (Exception e) {
			System.err.println("Error al eliminar");

		}
	}

}
