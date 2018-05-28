package unpsjb.fipm.gisfpp.servicios.convocatoria;

import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocable;
import unpsjb.fipm.gisfpp.entidades.convocatoria.TipoConvocatoria;
import unpsjb.fipm.gisfpp.servicios.IServicioGenerico;

public interface IServiciosConvocable extends IServicioGenerico<Convocable, Integer> {
	
	public Convocable recuperarConvocable(Integer id, TipoConvocatoria tipoConvocable) throws Exception;
}
