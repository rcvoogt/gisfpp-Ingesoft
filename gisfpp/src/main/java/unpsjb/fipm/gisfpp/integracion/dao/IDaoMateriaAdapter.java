package unpsjb.fipm.gisfpp.integracion.dao;

import org.springframework.dao.DataAccessException;

import unpsjb.fipm.gisfpp.dao.DaoGenerico;
import unpsjb.fipm.gisfpp.integracion.entidades.MateriaAdapter;

public interface IDaoMateriaAdapter extends DaoGenerico<MateriaAdapter, Integer>{

	int actualizarOguardar(MateriaAdapter instancia) throws Exception;

	int existe(String codigoMateria);

	int recuperarxNombre(String materia) throws DataAccessException;

}
