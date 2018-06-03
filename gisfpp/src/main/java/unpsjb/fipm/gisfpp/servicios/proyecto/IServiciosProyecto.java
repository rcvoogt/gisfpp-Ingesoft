package unpsjb.fipm.gisfpp.servicios.proyecto;

import java.util.List;

import unpsjb.fipm.gisfpp.entidades.proyecto.OfertaActividad;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.servicios.IServicioGenerico;

public interface IServiciosProyecto extends IServicioGenerico<Proyecto, Integer> {
	
	public List<OfertaActividad> getAllOfertas();

	public boolean cumpleVencimientoMeses(Proyecto proyecto, Integer venc);

}
