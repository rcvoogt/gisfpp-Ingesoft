package unpsjb.fipm.gisfpp.servicios;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import unpsjb.fipm.gisfpp.entidades.persona.DatoDeContacto;
import unpsjb.fipm.gisfpp.entidades.persona.Identificador;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.TDatosContacto;
import unpsjb.fipm.gisfpp.entidades.persona.TIdentificador;
import unpsjb.fipm.gisfpp.entidades.staff.ECargosStaffFi;
import unpsjb.fipm.gisfpp.entidades.staff.StaffFI;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPF;
import unpsjb.fipm.gisfpp.servicios.staff.IServiciosStaffFI;

public class ServicioStaffTest extends TemplateTest {
	@Autowired 
	IServiciosStaffFI servStaff;
	@Autowired
	IServicioPF servPersona;
	PersonaFisica personaPersistida;
	Identificador identificadorPersonaPersistida;
	DatoDeContacto contactoPersonaPersistida;
	PersonaFisica personaNoPersistida;
	StaffFI staffPersonaPersistida;
	StaffFI staffPersonaNoPersistida;

	@Override
	public void setBefore() {
		String dni1 = "38443097";
		String dni2 = "38445198";
		
		
		personaPersistida = crearPersonaFisica(dni1,"isaiasarza@outlook.com","Persona Persistida", "20-"+dni1+"-0");
		personaNoPersistida = crearPersonaFisica(dni2,"juanarza@outlook.com","Persona No Persistida", "20-"+dni2+"-0");
		staffPersonaPersistida = crearStaff(personaPersistida);
		staffPersonaNoPersistida = crearStaff(personaPersistida);
		try {
			servPersona.persistir(personaPersistida);
			servStaff.persistir(staffPersonaPersistida);
			System.out.println("Peresistio Persona");
		} catch (Exception e) {
			System.err.println("Error al persistir");
		}
	}
	
	private StaffFI crearStaff(PersonaFisica persona) {
		Calendar calendar = Calendar.getInstance();
		Date desde = calendar.getTime();
		calendar.add(Calendar.YEAR, 1);
		Date hasta = calendar.getTime();
		StaffFI staff = new StaffFI(persona,ECargosStaffFi.ALUMNO,desde,hasta,null);		
		return staff;
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
			assertTrue(servStaff.existe(staffPersonaPersistida));		
			assertFalse(servStaff.existe(staffPersonaNoPersistida));
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	@Test
	public void actualizarOguardarTest() {
		try {
			assertNotNull(servStaff.actualizarOguardar(staffPersonaPersistida));
			assertNotNull(servStaff.actualizarOguardar(staffPersonaNoPersistida));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setAfter() {
		try {			
			servStaff.eliminar(staffPersonaPersistida);
			servPersona.eliminar(personaPersistida);
			System.out.println("Elimino Persona Persistida");
		} catch (Exception e) {
			System.err.println("Error al eliminar");

		}
	}

}
