package unpsjb.fipm.gisfpp.util.excepcion;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

public class ValidacionDatosException extends Exception {

	private static final long serialVersionUID = 1L;
	private ConstraintViolationException cve;

	public ValidacionDatosException(ConstraintViolationException cve) {
		super();
		this.cve = cve;
	}

	@SuppressWarnings("rawtypes")
	public String getMensajesViolations() {
		StringBuffer acumulados = new StringBuffer("");
		int contador = 0;
		for (ConstraintViolation violation : cve.getConstraintViolations()) {
			contador = contador + 1;
			acumulados.append(contador + ") " + violation.getMessage() + "\n");
		}

		return new String(acumulados);
	}

	public ConstraintViolationException getOriginal() {
		return cve;
	}

}// Fin clase ValidacionDatosException
