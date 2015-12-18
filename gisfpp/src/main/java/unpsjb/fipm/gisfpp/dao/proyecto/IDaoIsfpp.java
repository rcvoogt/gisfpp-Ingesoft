package unpsjb.fipm.gisfpp.dao.proyecto;

import java.util.List;

import unpsjb.fipm.gisfpp.dao.DaoGenerico;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;

public interface IDaoIsfpp extends DaoGenerico<Isfpp, Integer> {

	/**
	 * Dado un Sub-Proyecto pasado como argumento, se recuperan todas las
	 * instancias Isfpp's asociadas a ese Sub-Proyecto.
	 * 
	 * @param sp
	 * @return List<Isfpp> Lista de Isfpp's.
	 * @throws Exception
	 */
	public List<Isfpp> getIsfpps(SubProyecto sp) throws Exception;
}
