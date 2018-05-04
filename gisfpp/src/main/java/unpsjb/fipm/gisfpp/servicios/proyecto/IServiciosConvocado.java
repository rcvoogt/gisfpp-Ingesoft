package unpsjb.fipm.gisfpp.servicios.proyecto;

import java.util.List;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.proyecto.Convocado;
import unpsjb.fipm.gisfpp.entidades.proyecto.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.servicios.IServicioGenerico;

public interface IServiciosConvocado extends IServicioGenerico<Convocado, Integer> {

	
	public Convocado getConvocado(String usuario, Convocatoria convocatoria) throws Exception;
	
	public PersonaFisica getPersona(Integer idConvocado) throws Exception;
	
	public PersonaFisica getConvocatoria(Integer idConvocado) throws Exception;
	
	
	
}
