package unpsjb.fipm.gisfpp.servicios.proyecto;

import java.util.List;

import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.servicios.IServicioGenerico;

public interface IServicioSubProyecto extends IServicioGenerico<SubProyecto, Integer> {

	public List<SubProyecto> getSubProyectos(Proyecto proyecto) throws Exception;
	
	public List<SubProyecto> getOfertasActividades() throws Exception;
	
	/**
	 * Devuelve el "Proyecto" al cual pertenece el "SubProyecto" pasado como parámetro.
	 * @param instancia de SubProyecto.
	 * @return instancia de Proyecto.
	 * @throws Exception
	 */
	public Proyecto getPerteneceA(Integer idSP) throws Exception;

}
