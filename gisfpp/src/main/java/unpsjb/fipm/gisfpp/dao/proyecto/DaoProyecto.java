package unpsjb.fipm.gisfpp.dao.proyecto;

import java.util.List;

import unpsjb.fipm.gisfpp.dao.DaoGenerico;
import unpsjb.fipm.gisfpp.entidades.proyecto.OfertaActividad;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;

public interface DaoProyecto extends DaoGenerico<Proyecto, Integer> {

	List<OfertaActividad> getAllOfertas();
		
}
