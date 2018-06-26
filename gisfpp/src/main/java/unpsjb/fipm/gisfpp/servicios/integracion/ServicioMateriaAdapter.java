package unpsjb.fipm.gisfpp.servicios.integracion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

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
	public Integer persistir(MateriaAdapter instancia) throws Exception {
		// TODO Auto-generated method stub
		return dao.crear(instancia);
	}

	@Override
	public void editar(MateriaAdapter instancia) throws Exception {
		dao.actualizar(instancia);
	}

	@Override
	public void eliminar(MateriaAdapter instancia) throws Exception {
		dao.eliminar(instancia);
	}

	@Override
	public MateriaAdapter getInstancia(Integer id) throws Exception {
		return dao.recuperarxId(id);
	}

	@Override
	public List<MateriaAdapter> getListado() throws Exception {
		return dao.recuperarTodo();
	}

	@Override
	public int actualizarOguardar(MateriaAdapter instancia) throws Exception {
		dao.actualizarOguardar(instancia);
		return instancia.getId();
	}	

}
