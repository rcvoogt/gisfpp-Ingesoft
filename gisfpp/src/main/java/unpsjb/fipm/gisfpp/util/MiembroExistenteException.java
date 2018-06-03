package unpsjb.fipm.gisfpp.util;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;

public class MiembroExistenteException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PersonaFisica persona;

	public MiembroExistenteException(PersonaFisica persona) {
		super();
		this.persona = persona;
	}
	
	public PersonaFisica getPersona() {
		return persona;
	}
	
	@Override
	public String getMessage() {
		return "La persona " + persona.getNombre() + " ya forma parte del staff";
	}
}
