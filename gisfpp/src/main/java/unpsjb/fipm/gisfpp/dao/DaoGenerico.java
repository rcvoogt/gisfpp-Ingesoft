package unpsjb.fipm.gisfpp.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

/**
 * Interface DAO Generica
 * 
 * @author Jose Devia
 * @param <T>
 * @param <K>
 */
public interface DaoGenerico<T extends Serializable, K extends Serializable> {

	// Elimina todas las instancias de la Colllection pasada como parámetro
	public void deleteAll(Collection<T> instances) throws Exception;

	// Inserta un registro en la BD de la instancia pasada como parámetro
	public K Create(T instance) throws Exception;

	// Inserta o actualiza en la BD las instancias contenidas en la Collection
	// pasada como parámetro
	public void CreateOrUpdateAll(Collection<T> instances) throws Exception;

	// Inserta o actualiza en la BD la instancia pasada como parámetro
	public void CreateOrUpdate(T instance) throws Exception;

	/*
	 * public void persist(T transientInstance) throws Exception;
	 * 
	 * public void attachDirty(T instance) throws Exception;
	 * 
	 * public void attachClean(T instance) throws Exception;
	 */

	// Elimina de la BD la instacia pasada como parámetro
	public void delete(T persistentInstance) throws Exception;

	// Devuelve todas las instancias encontradas en la BD que coincidan con la
	// instancia proporcionada como parámetro
	public List<T> findByExample(T instance) throws Exception;

	// Devuelve el resultado de la consulta proporcionada como parámetro
	// (String)
	public List<T> findByQuery(String query) throws Exception;

	public List<Map<String, Object>> findMapByQuery(String queryString) throws Exception;

	public List<T> findByCriteria(DetachedCriteria criteria) throws Exception;

	public T merge(T detachedInstance) throws Exception;

	public List<T> findAll() throws Exception;

	public T findById(K id) throws Exception;
}
