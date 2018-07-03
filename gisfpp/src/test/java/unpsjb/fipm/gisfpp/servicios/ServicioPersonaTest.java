package unpsjb.fipm.gisfpp.servicios;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import unpsjb.fipm.gisfpp.entidades.persona.DatoDeContacto;
import unpsjb.fipm.gisfpp.entidades.persona.Identificador;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.TDatosContacto;
import unpsjb.fipm.gisfpp.entidades.persona.TIdentificador;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPF;

public class ServicioPersonaTest extends TemplateTest {
	@Autowired
	IServicioPF servPersona;
	PersonaFisica personaPersistida;
	Identificador identificadorPersonaPersistida;
	DatoDeContacto contactoPersonaPersistida;
	PersonaFisica personaNoPersistida;

	@Override
	public void setBefore() {
		String dni1 = "38443097";
		String dni2 = "38445198";
		personaPersistida = crearPersonaFisica(dni1,"isaiasarza@outlook.com","Persona Persistida", "20-"+dni1+"-0");
		personaNoPersistida = crearPersonaFisica(dni2,"juanarza@outlook.com","Persona No Persistida", "20-"+dni2+"-0");
		
		try {
			servPersona.persistir(personaPersistida);
			System.out.println("Peresistio Persona");
		} catch (Exception e) {
			System.err.println("Error al persistir");
		}
	}
	private PersonaFisica crearPersonaFisica(String dni, String mail, String nombre, String legajo) {
		Identificador identificadorDni = new Identificador(TIdentificador.DNI, dni);
		Identificador identificadorLegajo = new Identificador(TIdentificador.LEGAJO, legajo);
		DatoDeContacto contacto = new DatoDeContacto(TDatosContacto.EMAIL, mail);
		PersonaFisica persona = new PersonaFisica();
		persona.setNombre(nombre);
		persona.addIdentificador(identificadorDni);
		persona.agregarDatoDeContacto(contacto);
		persona.getUsuario().setNickname(nombre);
		persona.getUsuario().setPassword("1234");
		persona.addIdentificador(identificadorLegajo);
		return persona;
	}
	@Test
	public void existeTest() {
		try {
			String legajo = personaPersistida.getLegajo();
			boolean resultado = servPersona.existe(legajo);
			assertTrue(resultado);
			assertFalse(servPersona.existe(personaNoPersistida.getLegajo()));			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	@Test
	public void actualizarOguardarTest() {
		try {
			servPersona.actualizarOguardar(personaPersistida);
			servPersona.actualizarOguardar(personaNoPersistida);
			
			assertNotNull(servPersona.getInstancia(personaPersistida.getId()));
			assertNotNull(servPersona.getInstancia(personaNoPersistida.getId()));
			
			servPersona.eliminar(personaNoPersistida);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setAfter() {
		try {
			servPersona.eliminar(personaPersistida);
			System.out.println("Elimino Persona Persistida");
		} catch (Exception e) {
			System.err.println("Error al eliminar");

		}
	}

}
