package unpsjb.fipm.gisfpp.util;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

public class UtilGisfpp {

	// Constantes utilizadas en el sistema
	public static final String MOD_NUEVO = "NUEVO";
	public static final String MOD_EDICION = "EDICION";
	public static final String MOD_VER = "VER";

	public static String getMensajeValidations(ConstraintViolationException cve) {
		StringBuffer acumulados = new StringBuffer("");
		int contador = 0;
		for (ConstraintViolation violation : cve.getConstraintViolations()) {
			contador = contador + 1;
			acumulados.append(contador + ") " + violation.getMessage() + "\n");
		}

		return new String(acumulados);
	}

}
