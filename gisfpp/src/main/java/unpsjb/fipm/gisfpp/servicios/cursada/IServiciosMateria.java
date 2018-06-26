package unpsjb.fipm.gisfpp.servicios.cursada;

import unpsjb.fipm.gisfpp.entidades.cursada.Materia;
import unpsjb.fipm.gisfpp.servicios.IServicioGenerico;

public interface IServiciosMateria extends IServicioGenerico<Materia, Integer> {
	public int actualizarOguardar(Materia instancia) throws Exception;
}
