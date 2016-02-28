package unpsjb.fipm.gisfpp.util;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jose Devia
 *
 */
public class UtilGisfpp {

	// Constantes utilizadas en el sistema
	public static final String MOD_NUEVO = "NUEVO";
	public static final String MOD_EDICION = "EDICION";
	public static final String MOD_VER = "VER";
	private static Logger logger;

	public static String getMensajeValidations(ConstraintViolationException cve) {
		StringBuffer acumulados = new StringBuffer("");
		int contador = 0;
		for (ConstraintViolation violation : cve.getConstraintViolations()) {
			contador = contador + 1;
			acumulados.append(contador + ") " + violation.getMessage() + "\n");
		}

		return new String(acumulados);
	}
	public static Logger getLogger() {
		if (logger == null) {
			logger = LoggerFactory.getLogger("logger_gisfpp");
		}
		return logger;
	}

	
}// fin de la clase
