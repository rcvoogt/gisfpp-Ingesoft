package unpsjb.fipm.gisfpp.dao.cursada;

import unpsjb.fipm.gisfpp.dao.DaoGenerico;
import unpsjb.fipm.gisfpp.entidades.cursada.Cursada;
import unpsjb.fipm.gisfpp.entidades.cursada.Materia;

public interface IDaoCursada extends DaoGenerico<Cursada, Integer>{

	public int actualizarOguardar(Cursada instancia) throws Exception;
	public int existe(String codigoComision);

}
