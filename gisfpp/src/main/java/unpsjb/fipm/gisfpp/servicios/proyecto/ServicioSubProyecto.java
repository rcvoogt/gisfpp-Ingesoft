package unpsjb.fipm.gisfpp.servicios.proyecto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.dao.proyecto.IDaoSubProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;

@Service("servSubProyecto")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServicioSubProyecto implements IServicioSubProyecto {

	private IDaoSubProyecto dao;

	@Override
	@Transactional(readOnly = false)
	public Integer persistir(SubProyecto instancia) throws Exception {
		try {
			dao.crear(instancia);
			return instancia.getId();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void editar(SubProyecto instancia) throws Exception {
		try {
			dao.actualizar(instancia);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void eliminar(SubProyecto instancia) throws Exception {
		try {
			dao.eliminar(instancia);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public SubProyecto getId(Integer id) throws Exception {
		try {
			return dao.recuperarxId(id);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<SubProyecto> getListado() throws Exception {
		try {
			return dao.recuperarTodo();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<SubProyecto> getSubProyectos(Proyecto proyecto) throws Exception {
		try {
			return dao.listadoSubProyectos(proyecto);
		} catch (Exception e) {
			throw e;
		}
	}

	@Autowired(required = true)
	public void setDao(IDaoSubProyecto dao) {
		this.dao = dao;
	}

}// fin de la clase
