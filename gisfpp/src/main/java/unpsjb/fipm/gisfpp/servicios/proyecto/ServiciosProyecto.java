package unpsjb.fipm.gisfpp.servicios.proyecto;

import java.util.List;

/**
 * @author Jose Devia
 *
 */
public interface ServiciosProyecto {

	public List<ProyectoProxy> consultarProyectos(String campoBusqueda, String patronBusqueda) throws Exception;

	public List<ProyectoProxy> obtenerTodosProyectos() throws Exception;

	public Integer guardarProyecto(ProyectoProxy proyecto) throws Exception;

	public void editarProyecto(ProyectoProxy proyecto) throws Exception;

	public ProyectoProxy verProyecto(Integer id) throws Exception;

	public void bajaProyecto(ProyectoProxy proyecto) throws Exception;

}
