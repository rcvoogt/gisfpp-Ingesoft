package unpsjb.fipm.gisfpp.servicios.integracion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.integracion.dao.IDaoMateriaAdapter;
import unpsjb.fipm.gisfpp.integracion.entidades.MateriaAdapter;
@Service("servMateriaAdapter")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServicioMateriaAdapter implements IServicioMateriaAdapter{

	IDaoMateriaAdapter dao;
	
	@Autowired
	public void setDao(IDaoMateriaAdapter dao) {
		this.dao = dao;
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public Integer persistir(MateriaAdapter instancia) throws Exception {
		return dao.crear(instancia);
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void editar(MateriaAdapter instancia) throws Exception {
		dao.actualizar(instancia);
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void eliminar(MateriaAdapter instancia) throws Exception {
		dao.eliminar(instancia);
	}

	@Override
	@Transactional
	public MateriaAdapter getInstancia(Integer id) throws Exception {
		return dao.recuperarxId(id);
	}

	@Override
	@Transactional
	public List<MateriaAdapter> getListado() throws Exception {
		return dao.recuperarTodo();
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public int actualizarOguardar(MateriaAdapter instancia) throws Exception {
		if(existe(instancia.getMateria()) == -1) {
			dao.crear(instancia);
			return -1;
		}
		dao.actualizar(instancia);
		return instancia.getIdMateria();
	}

	@Override
	@Transactional
	public int existe(String codigoMateria) throws Exception {
		return dao.existe(codigoMateria);
	}	

}
