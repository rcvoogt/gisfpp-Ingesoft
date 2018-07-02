package unpsjb.fipm.gisfpp.servicios.cursada;

import unpsjb.fipm.gisfpp.entidades.cursada.Cursada;
import unpsjb.fipm.gisfpp.entidades.cursada.Materia;
import unpsjb.fipm.gisfpp.servicios.IServicioGenerico;

public interface IServiciosCursada extends IServicioGenerico<Cursada, Integer> {

	public int actualizarOguardar(Cursada instancia) throws Exception;

	int existe(String codigoComision) throws Exception;
}
