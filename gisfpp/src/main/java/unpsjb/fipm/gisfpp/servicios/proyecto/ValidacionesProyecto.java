package unpsjb.fipm.gisfpp.servicios.proyecto;

import unpsjb.fipm.gisfpp.entidades.proyecto.EEstadosIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.servicios.ResultadoValidacion;

/**
 * Clase responsable de la validación de las reglas de negocio
 * aplicadas sobre las entidades Proyecto, Sub-Proyecto, Isfpp.
 * @author jldevia
 *
 */
public class ValidacionesProyecto {

	/**
	 * Método que valida si se cumplen las condiciones necesarias
	 * para poder eliminar el Proyecto pasado como parámetro.  
	 * @param arg1: Proyecto
	 * @return resultado de la validación: ResultadoValidacion.
	 */
	public static ResultadoValidacion eliminarProyecto(Proyecto arg1){
		ResultadoValidacion resultado = new ResultadoValidacion();
		boolean condicion = (arg1.getSubProyectos() == null) || (arg1.getSubProyectos().isEmpty()); 
		resultado.setValido(condicion);
		resultado.setMensaje("No se puede eliminar un Proyecto con Sub-Proyectos asignados.");
		return resultado;
	}
	
	/**
	 * Método que valida si se cumplen las condiciones necesarias
	 * para poder eliminar la Isfpp pasada como parámetro.
	 * @param arg1: Isfpp.
	 * @return Si(True)/No(False)
	 */
	public static ResultadoValidacion eliminarIsfpp(Isfpp arg1){
		ResultadoValidacion resultado = new ResultadoValidacion();
		boolean condicion = (arg1.getEstado()== EEstadosIsfpp.GENERADA); 
		resultado.setValido(condicion);
		resultado.setMensaje("Solo se puede eliminar una Isfpp en estado \"Generada\".");
		return resultado;
	}
	
}
