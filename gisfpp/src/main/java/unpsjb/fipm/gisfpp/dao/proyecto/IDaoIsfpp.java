package unpsjb.fipm.gisfpp.dao.proyecto;

import java.util.List;

import unpsjb.fipm.gisfpp.dao.DaoGenerico;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.proyecto.EEstadosIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
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
	
	/**
	 * Devuelve el SubProyecto al cual pertenece la Isfpp pasada como parámetro;
	 * @param isfpp
	 * @return SubProyecto
	 * @throws Exception
	 */
	public SubProyecto getPerteneceA(Integer idIsfpp) throws Exception;
	
	public List<MiembroStaffIsfpp> getMiembros(Isfpp isfpp) throws Exception,NullPointerException;
	
	public Proyecto getPerteneceAProyecto(Integer idIsfpp) throws Exception;
	
	public List<PersonaFisica> getPracticantes(Integer IdIsfpp) throws Exception;
	
	public int getCantidadPracticantes (Integer idIsfpp) throws Exception;
	
	public void actualizarEstado (Integer idIsfpp, EEstadosIsfpp estado) throws Exception;
	
	public List<Convocatoria> getConvocatorias(Integer idIsfpp) throws Exception;
	
		
}
