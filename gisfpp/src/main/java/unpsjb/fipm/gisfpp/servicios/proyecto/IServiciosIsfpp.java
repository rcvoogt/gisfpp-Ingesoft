package unpsjb.fipm.gisfpp.servicios.proyecto;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.proyecto.EEstadosIsfpp;
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
	
	public List<PersonaFisica> getPracticantes(Integer idIsfpp) throws Exception;
	
	public int getCantidadPracticantes(Integer idIsfpp) throws Exception;
	
	/**
	 * Establecer Isfpp a estado "Activa".
	 * @param idIsfpp
	 * @throws Exception
	 */
	public void activarIsfpp(Integer idIsfpp) throws Exception;
	
	/**
	 * Establecer Isfpp a estado "Rechazada"
	 * @param idIsfpp
	 * @throws Exception
	 */
	public void rechazarIsfpp(Integer idIsfpp) throws Exception;
	
	/**
	 * Establecer Isfpp a estado "Suspendida". Estado es temporal.
	 * @param idIsfpp
	 * @throws Exception
	 */
	public void suspenderIsfpp(Integer idIsfpp) throws Exception;
	
	/**
	 * Establecer Isfpp a estado "Cancelada". Estado definitivo.
	 * @param idIsfpp
	 * @throws Exception
	 */
	public void cancelarIsfpp(Integer idIsfpp) throws Exception;
	
	/**
	 * Establecer Isfpp a estado "Concluida"
	 * @param idIsfpp
	 * @throws Exception
	 */
	public void concluirIsfpp(Integer idIsfpp) throws Exception;
	
	public void refrescarInstancia(Isfpp instancia) throws Exception;

}
