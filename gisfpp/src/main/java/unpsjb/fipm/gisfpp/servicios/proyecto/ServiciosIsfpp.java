package unpsjb.fipm.gisfpp.servicios.proyecto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.dao.proyecto.IDaoIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;

@Service("servIsfpp")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServiciosIsfpp implements IServiciosIsfpp {

	private IDaoIsfpp dao;

	@Override
	@Transactional(readOnly = false)
	public Integer persistir(Isfpp instancia) throws Exception {
		return dao.crear(instancia);
	}

	@Override
	@Transactional(readOnly = false)
	public void editar(Isfpp instancia) throws Exception {
		dao.actualizar(instancia);
	}

	@Override
	@Transactional(readOnly = false)
	public void eliminar(Isfpp instancia) throws Exception {
		dao.eliminar(instancia);
	}

	@Override
	@Transactional(readOnly = true)
	public Isfpp getInstancia(Integer id) throws Exception {
		return dao.recuperarxId(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Isfpp> getListado() throws Exception {
		return dao.recuperarTodo();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Isfpp> getIsfpps(SubProyecto sp) throws Exception {
		return dao.getIsfpps(sp);
	}

	@Autowired(required = true)
	protected void setDao(IDaoIsfpp dao) {
		this.dao = dao;
	}

}// fin de la clase
