package unpsjb.fipm.gisfpp.dao.cursada;

import unpsjb.fipm.gisfpp.dao.DaoGenerico;
import unpsjb.fipm.gisfpp.entidades.cursada.Materia;

public interface IDaoMateria extends DaoGenerico<Materia, Integer>{

	public int actualizarOguardar(Materia instancia) throws Exception;
}
