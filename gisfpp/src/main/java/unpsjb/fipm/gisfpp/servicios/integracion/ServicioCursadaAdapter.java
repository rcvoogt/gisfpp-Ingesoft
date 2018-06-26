package unpsjb.fipm.gisfpp.servicios.integracion;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.integracion.dao.IDaoCursadaAdapter;
import unpsjb.fipm.gisfpp.integracion.entidades.CursadaAdapter;

@Service("servCursadaAdapter")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServicioCursadaAdapter implements IServicioCursadaAdapter{

	
	private IDaoCursadaAdapter dao;	
	
	
	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public Integer persistir(CursadaAdapter instancia) throws Exception {
		return dao.crear(instancia);
	}

	@Override
	public void editar(CursadaAdapter instancia) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)	
	public void eliminar(CursadaAdapter instancia) throws Exception {
		dao.eliminar(instancia);
	}

	@Override
	public CursadaAdapter getInstancia(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CursadaAdapter> getListado() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public IDaoCursadaAdapter getDao() {
		return dao;
	}

	public void setDao(IDaoCursadaAdapter dao) {
		this.dao = dao;
	}
	
}
