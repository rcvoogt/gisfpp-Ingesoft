package unpsjb.fipm.gisfpp.dao.convocatoria;

import java.util.List;

import org.springframework.dao.DataAccessException;

import unpsjb.fipm.gisfpp.dao.DaoGenerico;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocado;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.proyecto.EEstadosIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;

public interface IDaoConvocatoria extends DaoGenerico<Convocatoria, Integer> {

	/**
	 * Devuelve la Isfpp al cual pertenece la convocatoria pasada como parámetro;
	 * @param Convocatoria
	 * @return Isfpp
	 * @throws Exception
	 */
	public Isfpp getIsfpp(Integer idConvocatoria) throws Exception;
	
	
	/**
	 * Devuelve la lista de convocados de la convocatoria pasada como parámetro;
	 * @param Convocatoria
	 * @return List<Convocado>
	 * @throws Exception
	 */
	public List<Convocado> getConvocados(Integer idConvocatoria) throws Exception;
	
	
	public int getCantidadConvocados (Integer idConvocatoria) throws Exception;
	
	public Convocatoria recuperarxId(Integer id) throws DataAccessException;

		
}
