package unpsjb.fipm.gisfpp.servicios.integracion;

import unpsjb.fipm.gisfpp.integracion.entidades.CursadaAdapter;
import unpsjb.fipm.gisfpp.integracion.entidades.MateriaAdapter;
import unpsjb.fipm.gisfpp.servicios.IServicioGenerico;

public interface IServicioCursadaAdapter extends IServicioGenerico<CursadaAdapter, Integer>{

	int actualizarOguardar(CursadaAdapter instancia) throws Exception;

	/**
	 * 
	 * @param codigoComision
	 * @return id
	 * @throws Exception
	 */
	int existe(String codigoComision) throws Exception;

}
