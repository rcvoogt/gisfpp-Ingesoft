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
import unpsjb.fipm.gisfpp.util.GisfppException;

@Service("servSubProyecto")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServicioSubProyecto implements IServicioSubProyecto {

	private IDaoSubProyecto dao;

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public Integer persistir(SubProyecto instancia) throws Exception {
		dao.crear(instancia);
		return instancia.getId();
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void editar(SubProyecto instancia) throws Exception {
		dao.actualizar(instancia);
		
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void eliminar(SubProyecto instancia) throws Exception {
		if (dao.cantIsfppAsociadas(instancia.getId()) == 0) {
			dao.eliminar(instancia);
		} else {
			throw new GisfppException("No se puede eliminar el Sub-Proyecto, "
					+ "el mismo tiene ISFPP's asignadas.");
		}
	}

	@Override
	@Transactional(value="gisfpp", readOnly = true)
	public SubProyecto getInstancia(Integer id) throws Exception {
		return dao.recuperarxId(id);
	}

	@Override
	@Transactional(value="gisfpp", readOnly = true)
	public List<SubProyecto> getListado() throws Exception {
		return dao.recuperarTodo();
	}

	@Override
	@Transactional(value="gisfpp", readOnly = true)
	public List<SubProyecto> getSubProyectos(Proyecto proyecto) throws Exception {
		return dao.listadoSubProyectos(proyecto);
	}

	@Override
	@Transactional(value="gisfpp", readOnly = true)
	public List<SubProyecto> getOfertasActividades() throws Exception {
		return dao.listadoOfertasActividades();
	}
	
	@Override
	@Transactional(value="gisfpp", readOnly=true)
	public Proyecto getPerteneceA(Integer idSP) throws Exception {
		return dao.getPerteneceA(idSP);
	}
	
	@Autowired(required = true)
	public void setDao(IDaoSubProyecto dao) {
		this.dao = dao;
	}
	
}// fin de la clase
