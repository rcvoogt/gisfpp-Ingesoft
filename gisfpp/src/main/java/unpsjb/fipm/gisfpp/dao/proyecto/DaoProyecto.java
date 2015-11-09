package unpsjb.fipm.gisfpp.dao.proyecto;

import java.util.List;

import org.springframework.dao.DataAccessException;

import unpsjb.fipm.gisfpp.dao.DaoGenerico;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;

public interface DaoProyecto extends DaoGenerico<Proyecto, Integer> {

	public List<Proyecto> filtrarxTitulo(String filtro) throws DataAccessException;

	public List<Proyecto> filtrarxCodigo(String codigo) throws DataAccessException;

	public List<Proyecto> filtrarxResolucion(String resolucion) throws DataAccessException;

}
