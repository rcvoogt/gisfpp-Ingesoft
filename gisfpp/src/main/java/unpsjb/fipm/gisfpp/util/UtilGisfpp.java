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
	
	/**
	 * Devuelve el periodo de tiempo pasado como parámetro en milisegundos, en un formato de dias, horas y minutos.
	 * @param periodo en milisegundos (Integer)
	 * @return periodo representado en días, horas y minutos como String.
	 */
	public static String formatearTiempo(Long periodo){
		int dias, horas, minutos;
		int restoDias, restoHoras;
		
		if(periodo!=null){
			dias = (int) (periodo/86400000);
			restoDias = (int) (periodo%86400000);
			
			horas = restoDias/3600000;
			restoHoras = restoDias%3600000;
			
			minutos = restoHoras/60000;
			
			return (dias + " días, " + horas + " horas, " + minutos+" minutos.");
		}
		else{
			return "";
		}
	}
	
	public static String traducirPrioridad(int prioridad){
		String resultado=null;
		switch (prioridad) {
		case 50:{
			 resultado = "Alta";
			break;
		}	
		case 25:{
			resultado ="Media";
			break;
		}
		case 1:{
			resultado ="Baja";
			break;
		}
		default:
			resultado="Indefinida";
			break;
		}
		return resultado;
	}

	
}// fin de la clase
