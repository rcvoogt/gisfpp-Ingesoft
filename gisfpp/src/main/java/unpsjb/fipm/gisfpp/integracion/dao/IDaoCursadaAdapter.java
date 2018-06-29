package unpsjb.fipm.gisfpp.integracion.dao;

import unpsjb.fipm.gisfpp.dao.DaoGenerico;
import unpsjb.fipm.gisfpp.integracion.entidades.CursadaAdapter;
import unpsjb.fipm.gisfpp.integracion.entidades.MateriaAdapter;

public interface IDaoCursadaAdapter extends DaoGenerico<CursadaAdapter, Integer>{

	int actualizarOguardar(CursadaAdapter instancia) throws Exception;

	int existe(String codigoComision);

}
