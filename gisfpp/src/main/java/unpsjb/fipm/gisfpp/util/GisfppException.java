package unpsjb.fipm.gisfpp.util;

/**
 * Excepcion provocada por la violacion de una regla de negocio de la App
 * 
 * @author Jose Devia
 *
 */

public class GisfppException extends Exception {

	private static final long serialVersionUID = 1L;

	public GisfppException(String mensaje) {
		super(mensaje);
	}

}
