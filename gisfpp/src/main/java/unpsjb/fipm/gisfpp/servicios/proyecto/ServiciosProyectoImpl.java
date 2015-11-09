package unpsjb.fipm.gisfpp.servicios.proyecto;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.dao.proyecto.DaoProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;

@Service("servProyecto")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServiciosProyectoImpl implements ServiciosProyecto, Serializable {

	private static final long serialVersionUID = 1L;
	private DaoProyecto dao;

	public ServiciosProyectoImpl() {
		super();
	}

	@Transactional(readOnly = true)
	public List<Proyecto> filtrarProyectos(String campoBusqueda, String patronBusqueda) throws Exception {
		if (campoBusqueda.equals("Código")) {
			return dao.filtrarxCodigo(patronBusqueda);
		}
		if (campoBusqueda.equals("Nº Resolucion")) {
			return dao.filtrarxResolucion(patronBusqueda);
		}
		if (campoBusqueda.equals("Título")) {
			return dao.filtrarxTitulo(patronBusqueda);
		}
		return null;
	}

	@Transactional(readOnly = true)
	public List<Proyecto> obtenerTodosProyectos() throws Exception {
		return dao.recuperarTodo();
	}

	@Transactional(readOnly = false)
	public Integer guardarProyecto(Proyecto proyecto) throws Exception {
		return dao.crear(proyecto);
	}

	@Transactional(readOnly = false)
	public void editarProyecto(Proyecto proyecto) throws Exception {
		dao.actualizar(proyecto);
	}

	public Proyecto verProyecto(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void bajaProyecto(Proyecto proyecto) throws Exception {
		// TODO Auto-generated method stub

	}

	@Autowired(required = true)
	public void setDao(DaoProyecto daoProyecto) {
		this.dao = daoProyecto;
	}

}
