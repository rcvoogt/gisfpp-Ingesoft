package unpsjb.fipm.gisfpp.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.dao.DaoPermisos;
import unpsjb.fipm.gisfpp.entidades.Operaciones;
import unpsjb.fipm.gisfpp.entidades.Permiso;
import unpsjb.fipm.gisfpp.entidades.Roles;

@Service("servicioPermisos")
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ServiciosPermisosImp implements IServiciosPermisos {

	private DaoPermisos dao;
	
	@Override
	@Transactional(value="gisfpp")
	public Integer persistir(Permiso instancia) throws Exception {
		return dao.crear(instancia);
	}

	@Override
	@Transactional(value="gisfpp")
	public void editar(Permiso instancia) throws Exception {
		dao.actualizar(instancia);
	}

	@Override
	@Transactional(value="gisfpp")
	public void eliminar(Permiso instancia) throws Exception {
		dao.eliminar(instancia);
	}

	@Override
	@Transactional(value="gisfpp")
	public Permiso getInstancia(Integer id) throws Exception {
		return dao.recuperarxId(id);
	}

	@Override
	@Transactional(value="gisfpp")
	public List<Permiso> getListado() throws Exception {
		return dao.recuperarTodo();
	}

	@Override
	@Transactional(value="gisfpp")
	public List<Operaciones> getOperacionesxRol(Roles rol)
			throws Exception {
		return dao.getOperacionesxRol(rol);
	}

	@Override
	@Transactional(value="gisfpp")
	public List<Permiso> getPermisosxRol(Roles rol) throws Exception {
		return dao.getPermisosxRol(rol);
	}
	
	@Autowired(required=true)
	public void setDao(DaoPermisos dao) {
		this.dao = dao;
	}

}
