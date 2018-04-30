package unpsjb.fipm.gisfpp.servicios.proyecto;

import java.util.List;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.proyecto.Convocado;
import unpsjb.fipm.gisfpp.entidades.proyecto.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.servicios.IServicioGenerico;

public interface IServiciosConvocatoria extends IServicioGenerico<Convocatoria, Integer> {

	public Isfpp getIsfpp(Integer idConvocatoria) throws Exception;
	
	public List<Convocado> getConvocados(Integer idConvocatoria) throws Exception;
	
	public int getCantidadConvocados(Integer idConvocatoria) throws Exception;
	
}
