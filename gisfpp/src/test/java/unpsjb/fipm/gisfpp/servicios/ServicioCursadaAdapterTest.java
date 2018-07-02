package unpsjb.fipm.gisfpp.servicios;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import unpsjb.fipm.gisfpp.integracion.entidades.CursadaAdapter;
import unpsjb.fipm.gisfpp.integracion.entidades.MateriaAdapter;
import unpsjb.fipm.gisfpp.servicios.integracion.IServicioCursadaAdapter;

public class ServicioCursadaAdapterTest extends TemplateTest {
	@Autowired
	IServicioCursadaAdapter servCursadaAdapter;
	CursadaAdapter cursadaPersistida;
	CursadaAdapter cursadaNoPersistida;

	@Override
	public void setBefore() {
		cursadaPersistida = new CursadaAdapter();
		cursadaPersistida.setIdCursadaGisfpp(403);
		cursadaPersistida.setCodComision("if-002345-4");
		
		cursadaNoPersistida = new CursadaAdapter();
		cursadaNoPersistida.setIdCursadaGisfpp(40401);
		cursadaNoPersistida.setCodComision("if-48484884-2");
		try {
			servCursadaAdapter.persistir(cursadaPersistida);
		} catch (Exception e) {
			System.err.println("Error al persistir");
		}
	}
	@Test
	public void existeTest() {
		try {
			Integer idCursada = new Integer(servCursadaAdapter.existe(cursadaPersistida.getCodComision()));
			Integer idCursadaNoPersistida = new Integer(servCursadaAdapter.existe(cursadaNoPersistida.getCodComision()));
			
			assertEquals(idCursada,cursadaPersistida.getId());
			
			assertNotEquals(idCursadaNoPersistida,cursadaNoPersistida.getId());
			assertEquals(idCursadaNoPersistida,new Integer(-1));
		} catch (Exception e) {}
		
	}
	
	@Test
	public void actualizarOguardarTest() {
		try {
			cursadaPersistida.setCodComision("CHOTA");
			servCursadaAdapter.actualizarOguardar(cursadaPersistida);
			servCursadaAdapter.actualizarOguardar(cursadaNoPersistida);
			
			assertNotNull(servCursadaAdapter.getInstancia(cursadaPersistida.getId()));
			assertNotNull(servCursadaAdapter.getInstancia(cursadaNoPersistida.getId()));
			assertEquals(servCursadaAdapter.getInstancia(cursadaPersistida.getId()), "CHOTA");
			servCursadaAdapter.eliminar(cursadaNoPersistida);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setAfter() {
		try {
			servCursadaAdapter.eliminar(cursadaPersistida);
		} catch (Exception e) {
			System.err.println("Error al eliminar");

		}
	}

}
