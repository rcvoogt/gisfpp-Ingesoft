package unpsjb.fipm.gisfpp.servicios.proyecto;

import java.util.List;

import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.servicios.IServicioGenerico;

public interface IServiciosIsfpp extends IServicioGenerico<Isfpp, Integer> {

	/**
	 * Dado un Sub-Proyecto pasado como argumento, se recuperan todas las
	 * instancias Isfpp's asociadas a ese Sub-Proyecto.
	 * 
	 * @param sp
	 * @return List<Isfpp> Listado de Isfpp's
	 * @throws Exception
	 */
	public List<Isfpp> getIsfpps(SubProyecto sp) throws Exception;
	
	/**
	 * Devuelve el SubProyecto al cual pertenece la Isfpp pasada como parámetro;
	 * @param isfpp
	 * @return SubProyecto
	 * @throws Exception
	 */
	public SubProyecto getPerteneceA(Isfpp instancia) throws Exception; 

}
