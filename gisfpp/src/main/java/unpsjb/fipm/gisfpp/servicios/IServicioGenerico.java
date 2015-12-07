package unpsjb.fipm.gisfpp.servicios;

import java.util.List;

public interface IServicioGenerico<T, K> {

	public K persistir(T instancia) throws Exception;

	public void editar(T instancia) throws Exception;

	public void eliminar(T instancia) throws Exception;

	public T getId(K id) throws Exception;

	public List<T> getListado() throws Exception;

}
