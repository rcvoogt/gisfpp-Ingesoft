package unpsjb.fipm.gisfpp.dao.convocatoria;

import java.util.List;

import unpsjb.fipm.gisfpp.dao.DaoGenerico;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocado;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.persona.DatoDeContacto;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.proyecto.EEstadosIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.ERespuestaConvocado;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;

public interface IDaoConvocado extends DaoGenerico<Convocado, Integer> {

	/**
	 * Dado un Sub-Proyecto pasado como argumento, se recuperan todas las
	 * instancias Isfpp's asociadas a ese Sub-Proyecto.
	 * 
	 * @param sp
	 * @return List<Isfpp> Lista de Isfpp's.
	 * @throws Exception
	 */
	public ERespuestaConvocado getRespuesta(Convocado conv) throws Exception;
	
	public PersonaFisica getPersonaFisica(Convocado conv) throws Exception;
	
	public Convocatoria getConvocatoria(Convocado conv) throws Exception;
	
	public List<DatoDeContacto> getDatosContacto(Convocado conv) throws Exception;
	
	public String getMail(Convocado conv) throws Exception;
		
}
