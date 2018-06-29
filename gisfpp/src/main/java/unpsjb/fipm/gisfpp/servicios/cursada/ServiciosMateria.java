package unpsjb.fipm.gisfpp.servicios.cursada;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.dao.cursada.IDaoMateria;
import unpsjb.fipm.gisfpp.entidades.cursada.Materia;
@Service("servMateria")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServiciosMateria implements IServiciosMateria{
	
	private IDaoMateria dao;

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public Integer persistir(Materia instancia) throws Exception {
		return dao.crear(instancia);
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void editar(Materia instancia) throws Exception {
		dao.actualizar(instancia);
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void eliminar(Materia instancia) throws Exception {
		dao.eliminar(instancia);
	}

	@Override
	public Materia getInstancia(Integer id) throws Exception {
		return dao.recuperarxId(id);
	}

	@Override
	public List<Materia> getListado() throws Exception {
		return dao.recuperarTodo();
	}
	
	@Autowired
	public void setDao(IDaoMateria dao) {
		this.dao = dao;
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public int actualizarOguardar(Materia instancia) throws Exception {
		instancia.setId(dao.existe(instancia.getCodigoMateria()));
		if(instancia.getId() != null) {
			dao.actualizar(instancia);
			return instancia.getId();
		}
		dao.crear(instancia);
		return instancia.getId();
	}

	@Override
	public int existe(String codigoMateria) throws Exception {
		return dao.existe(codigoMateria);
	}

}
