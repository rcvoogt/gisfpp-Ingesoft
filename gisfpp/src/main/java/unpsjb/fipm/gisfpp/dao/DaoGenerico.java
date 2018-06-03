package unpsjb.fipm.gisfpp.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.dao.DataAccessException;

import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocatoria;

/**
 * Interface DAO Generica: Interface generica con los m�todos comunes que
 * deberan implementar todos los DAO de la aplicacion
 * 
 * @author Jose Devia
 * @param <T>
 *            instancia
 * @param <K>
 *            identificador
 */
public interface DaoGenerico<T extends Serializable, K extends Serializable> {

	/**
	 * Crear un registro nuevo en la BD de la instancia (T) pasada como
	 * par�metro. Devuelve el Id (K) generado.
	 * 
	 * @param instancia
	 *            (T)
	 * @return id (K)
	 * @throws DataAccessException
	 */
	public K crear(T instancia) throws DataAccessException;

	/**
	 * Actualiza el registro en la BD de la instancia (T) pasada como par�metro.
	 * 
	 * @param instancia
	 *            (T)
	 * @throws DataAccessException
	 */
	public void actualizar(T instancia) throws DataAccessException;

	/**
	 * Elimina de la BD el registro correspondiente a la instancia (T) pasada
	 * como par�metro.
	 * 
	 * @param instancia
	 *            (T)
	 * @throws DataAccessException
	 */
	public void eliminar(T instancia) throws DataAccessException;

	/**
	 * Recupera todos los registros de la BD de el tipo de instancias T
	 * 
	 * @return Collection<T>
	 * @throws DataAccessException
	 */
	public List<T> recuperarTodo() throws DataAccessException;

	/**
	 * Recupera la instancia (T) de la BD con el id (K) pasado como par�metro
	 * 
	 * @param id
	 *            (K)
	 * @return instancia (T)
	 * @throws DataAccessException
	 */
	public T recuperarxId(K id) throws DataAccessException;
	

}
