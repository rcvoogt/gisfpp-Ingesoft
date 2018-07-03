package unpsjb.fipm.gisfpp.servicios.cursada;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.dao.cursada.IDaoCursada;
import unpsjb.fipm.gisfpp.dao.cursada.IDaoMateria;
import unpsjb.fipm.gisfpp.entidades.cursada.Cursada;
import unpsjb.fipm.gisfpp.entidades.cursada.Materia;

@Service("servCursada")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServiciosCursada implements IServiciosCursada {

	private IDaoCursada dao;

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public Integer persistir(Cursada instancia) throws Exception {
		return dao.crear(instancia);
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void editar(Cursada instancia) throws Exception {
		dao.actualizar(instancia);
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void eliminar(Cursada instancia) throws Exception {
		dao.eliminar(instancia);
	}

	@Override
	public Cursada getInstancia(Integer id) throws Exception {
		return dao.recuperarxId(id);
	}

	@Override
	public List<Cursada> getListado() throws Exception {
		return dao.recuperarTodo();
	}
	
	@Autowired
	public void setDao(IDaoCursada dao) {
		this.dao = dao;
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public int actualizarOguardar(Cursada instancia) throws Exception {
		instancia.setIdCursada(dao.existe(instancia.getCodigoComision()));
		if(instancia.getIdCursada()!= null) {
			dao.actualizar(instancia);
			return instancia.getIdCursada();
		}
		dao.crear(instancia);
		return instancia.getIdCursada();
	}
	
	@Override
	public int existe(String codigoComision) throws Exception {
		return dao.existe(codigoComision);
	}

}
