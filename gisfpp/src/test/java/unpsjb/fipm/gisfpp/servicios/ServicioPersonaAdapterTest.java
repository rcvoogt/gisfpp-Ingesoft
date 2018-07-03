package unpsjb.fipm.gisfpp.servicios;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.integracion.entidades.MateriaAdapter;
import unpsjb.fipm.gisfpp.integracion.entidades.PersonaAdapter;
import unpsjb.fipm.gisfpp.servicios.integracion.IServicioMateriaAdapter;
import unpsjb.fipm.gisfpp.servicios.integracion.IServicioPersonaAdapter;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPF;

public class ServicioPersonaAdapterTest extends TemplateTest {
	@Autowired
	IServicioPersonaAdapter servPersonaAdapter;
	@Autowired
	IServicioPF servPersonaFisica;
	PersonaAdapter personaPersistida;
	PersonaAdapter personaNoPersistida;
	PersonaFisica pf;

	@Override
	public void setBefore() {
		try {
			pf = servPersonaFisica.getListado().get(0);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		personaPersistida = new PersonaAdapter();
		personaPersistida.setIdPersona(pf.getId());
		personaPersistida.setLegajo("404-000-401");
		
		personaNoPersistida = new PersonaAdapter();
		personaNoPersistida.setIdPersona(401);
		personaNoPersistida.setLegajo("404-000-406");
		try {
			servPersonaAdapter.persistir(personaPersistida);
		} catch (Exception e) {
			System.err.println("Error al persistir");
		}
	}
	@Test
	public void existeTest() {
		try {
			Integer idMateria = new Integer(servPersonaAdapter.existe(personaPersistida.getLegajo()));
			Integer idMateriaNoPersistida = new Integer(servPersonaAdapter.existe(personaNoPersistida.getLegajo()));
			
			assertEquals(idMateria,personaPersistida.getId());
			
			assertNotEquals(idMateriaNoPersistida,personaNoPersistida.getId());
			assertEquals(idMateriaNoPersistida,new Integer(-1));
		} catch (Exception e) {}
		
	}
	
	@Test
	public void actualizarOguardarTest() {
		try {
			servPersonaAdapter.actualizarOguardar(personaPersistida);
			servPersonaAdapter.actualizarOguardar(personaNoPersistida);
			
			assertNotNull(servPersonaAdapter.getInstancia(personaPersistida.getId()));
			assertNotNull(servPersonaAdapter.getInstancia(personaNoPersistida.getId()));
			
			servPersonaAdapter.eliminar(personaNoPersistida);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getxLegajoTest() {
		try {
			PersonaFisica personaFisica = servPersonaAdapter.getPFxLegajo(personaPersistida.getLegajo());
			assertEquals(personaFisica,pf);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void setAfter() {
		try {
			servPersonaAdapter.eliminar(personaPersistida);
		} catch (Exception e) {
			System.err.println("Error al eliminar");

		}
	}

}
