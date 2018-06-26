package unpsjb.fipm.gisfpp.servicios.integracion;

import unpsjb.fipm.gisfpp.integracion.entidades.MateriaAdapter;
import unpsjb.fipm.gisfpp.servicios.IServicioGenerico;

public interface IServicioMateriaAdapter extends IServicioGenerico<MateriaAdapter, Integer>{
	public int actualizarOguardar(MateriaAdapter instancia) throws Exception;
}
