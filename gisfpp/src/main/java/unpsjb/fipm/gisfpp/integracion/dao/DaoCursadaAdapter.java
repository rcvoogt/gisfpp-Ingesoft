package unpsjb.fipm.gisfpp.integracion.dao;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import unpsjb.fipm.gisfpp.integracion.entidades.CursadaAdapter;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class DaoCursadaAdapter extends HibernateDaoSupport implements IDaoCursadaAdapter{

	private Logger log = UtilGisfpp.getLogger();

	@Override
	public Integer crear(CursadaAdapter instancia) throws DataAccessException {
		try {
			getHibernateTemplate().save(instancia);
			return instancia.getId();
		} catch (Exception e) {
			log.debug(this.getClass().getName(), e);
		}
		
		
		getHibernateTemplate().saveOrUpdate(instancia);
		return instancia.getId();
	}

	@Override
	public void actualizar(CursadaAdapter instancia) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminar(CursadaAdapter instancia) throws DataAccessException {
		getHibernateTemplate().delete(instancia);
	}

	@Override
	public List<CursadaAdapter> recuperarTodo() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CursadaAdapter recuperarxId(Integer id) throws DataAccessException {
		String query ="select cursada "
					+ "from CursadaAdapter cursada "
					+ "where cursada.idCursadaAdapter = ?";
		List<CursadaAdapter> result = (List<CursadaAdapter>) getHibernateTemplate().find(query, id);
		if(result == null || result.isEmpty()){
			return null;
		}
		return result.get(0);
	}

	@Override
	public int actualizarOguardar(CursadaAdapter instancia) throws Exception {
		getHibernateTemplate().saveOrUpdate(instancia);
		return instancia.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public int existe(String codigoComision) {
		String query = "select cursada " 
					 + "from CursadaAdapter as cursada " 
					 + "where materia.codComision = ?";
		List<CursadaAdapter> result;
		CursadaAdapter cursadaAux;
		try {
			result = (List<CursadaAdapter>) getHibernateTemplate().find(query, codigoComision);
			cursadaAux = result.get(0);
		} catch (Exception e) {
			return -1;
		}
		return cursadaAux.getId();
	}


}
