package unpsjb.fipm.gisfpp.servicios.convocatoria;

import java.util.List;

import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocable;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.servicios.IServicioGenerico;

public interface IServiciosConvocable extends IServicioGenerico<Convocable, Integer> {
	
	public Convocable getInstancia(Convocable convocable) throws Exception;
	public List<PersonaFisica> getMiembros(Convocable convocable) throws Exception;
}
