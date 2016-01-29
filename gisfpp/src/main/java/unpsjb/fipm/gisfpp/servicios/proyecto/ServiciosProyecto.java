package unpsjb.fipm.gisfpp.servicios.proyecto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.dao.proyecto.DaoProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.util.GisfppException;

@Service("servProyecto")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServiciosProyecto implements IServiciosProyecto {

	private DaoProyecto dao;

	@Override
	@Transactional(readOnly = false)
	public Integer persistir(Proyecto instancia) throws Exception {
		try {
			dao.crear(instancia);
			return instancia.getId();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void editar(Proyecto instancia) throws Exception {
		try {
			dao.actualizar(instancia);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void eliminar(Proyecto instancia) throws Exception {
		if (instancia.getSubProyectos()==null || instancia.getSubProyectos().isEmpty()) {
			try {
				dao.eliminar(instancia);
			} catch (Exception e) {
				throw e;
			}
		} else {
			throw new GisfppException("No se puede eliminar el Proyecto,"
					+ " el mismo tiene Sub-Proyectos asignados.");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Proyecto getInstancia(Integer id) throws Exception {
		try {
			return dao.recuperarxId(id);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Proyecto> getListado() throws Exception {
		try {
			return dao.recuperarTodo();
		} catch (Exception e) {
			throw e;
		}
	}

	@Autowired(required = true)
	public void setDao(DaoProyecto dao) {
		this.dao = dao;
	}

}// fin de la clase
