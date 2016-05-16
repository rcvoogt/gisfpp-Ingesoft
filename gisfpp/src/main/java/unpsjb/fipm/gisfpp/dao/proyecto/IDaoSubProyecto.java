package unpsjb.fipm.gisfpp.dao.proyecto;

import java.util.List;

import org.springframework.dao.DataAccessException;

import unpsjb.fipm.gisfpp.dao.DaoGenerico;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;

public interface IDaoSubProyecto extends DaoGenerico<SubProyecto, Integer> {

	public List<SubProyecto> listadoSubProyectos(Proyecto proyecto) throws DataAccessException;
	
	public List<SubProyecto> listadoOfertasActividades()  throws DataAccessException;
	
	public Proyecto getPerteneceA(Integer idSP) throws DataAccessException;
	
	/**
	 * Devuelve la cantidad de Isfpp's que tiene asociadas este Sub-Proyecto.
	 * @param idSP (Id del SubProyecto) Integer
	 * @return cantidad de isfpp (long)
	 * @throws DataAccessException
	 */
	public long cantIsfppAsociadas(Integer idSP) throws DataAccessException;

}
