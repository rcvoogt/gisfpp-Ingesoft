package unpsjb.fipm.gisfpp.servicios.proyecto;

import java.util.List;

import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;

/**
 * @author Jose Devia
 *
 */
public interface ServiceProyecto {

	public List<Proyecto> getProyectos(String campoBusqueda, String patronBusqueda) throws Exception;

	public List<Proyecto> getAllProyectos() throws Exception;

	public Integer crearProyecto(Proyecto proyecto) throws Exception;

	public void editarProyecto(Proyecto proyecto) throws Exception;

	public Proyecto verProyecto(Integer id) throws Exception;

	public void bajaProyecto(Proyecto proyecto) throws Exception;

}
